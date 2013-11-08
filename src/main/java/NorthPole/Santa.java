package NorthPole;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Santa extends NorthPoleConcurrentObject{
    private WaitingRoom waitingRoom;
	private WaitingRoom stable;
	private Boolean isSleeping;
    private final SnackRoom sr;
	private final ExecutorService es = Executors.newSingleThreadExecutor();

    public Santa(SnackRoom sr){
        super("Santa");
        this.sr = sr;
    }
    public void findWaitingRoom(WaitingRoom waitingRoom){
        this.waitingRoom = waitingRoom;
    }

	public void findStable(WaitingRoom stable){
		this.stable = stable;
	}

    public void sleep() throws InterruptedException{
        log("Sleeping");
	    isSleeping = true;
    }

    public Boolean wake() throws InterruptedException, BrokenBarrierException{
	    if (stable.isWaiting && isSleeping){
		    deliverToys(stable.group);
	        return true;
	    }
		if (waitingRoom.isWaiting && isSleeping){
		    consultOnRD(waitingRoom.group);
	        return true;
		}
	    return false;
    }

    public void deliverToys(Group reindeer) throws InterruptedException, BrokenBarrierException{
	    CyclicBarrier work = new CyclicBarrier(2);
	    isSleeping = false;
        log("Harnessing reindeer");
	    es.submit(new UnitOfWork("Getting Hitched", reindeer, work));
	    es.submit(new UnitOfWork("Delivering Toys", reindeer, work));
        Server.NorthPole.InjectWishList.wishList.deliverGifts();
	    es.submit(new UnitOfWork("Getting unhitched", reindeer, work));
	    es.submit(new UnitOfWork("Release", reindeer, work));
	    work.await();
        log("Unharnessing reindeer");
	    stable.releaseGroup();
	    sleep();
    }

    public void consultOnRD(Group elves) throws InterruptedException, BrokenBarrierException{
        sr.isWithElves();
        CyclicBarrier work = new CyclicBarrier(2);
        isSleeping = false;
        log("Welcoming elves");
        if(sr.isWaiting()) eatCookies();
        es.submit(new UnitOfWork("Entering Santa's house", elves, work));
        if(sr.isWaiting()) eatCookies();
        log("Consulting with elves");
        es.submit(new UnitOfWork("Consulting with Santa", elves, work));
        if(sr.isWaiting()) eatCookies();
        log("Dismissing Elves");
        es.submit(new UnitOfWork("Leaving santa's", elves, work));
        if(sr.isWaiting()) eatCookies();
        es.submit(new UnitOfWork("Release", elves, work));
        if(sr.isWaiting()) eatCookies();
        work.await();
        waitingRoom.releaseGroup();
        sr.doneMeeting();
        sleep();
    }
    private void eatCookies(){
        try {
            log("Eating Cookies");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sr.doneEating(1);
    }

    @Override
    public void run(){
            try{
                sleep();
            }
            catch (InterruptedException e) {
                System.out.println("Interrupted exception at Santa");
            }
    }

    class UnitOfWork implements Runnable{
	    CyclicBarrier work;
        final String name;
		final Group visitors;
        public UnitOfWork(String name, Group visitors, CyclicBarrier work){
	        this.work = work;
            this.name = name;
	        this.visitors = visitors;
        }
	    public void run(){
		    try
		    {
			    Thread.sleep(2000);
			    if (name.equals("Release"))
				    visitors.releaseGroup(work);
			    else
				    visitors.groupActivity(this, work);
		    } catch (InterruptedException e) {
                System.out.println("Interrupted exception at UnitOfWork");}
		    catch (BrokenBarrierException e) {
                System.out.println("Barrier exception at UnitOfWork"); }
	    }
    }
}