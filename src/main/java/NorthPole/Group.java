package NorthPole;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;

public class Group extends NorthPoleConcurrentObject{

    private LinkedBlockingQueue<SantasFriend> group;
    int size;
	final public String type;
    private ArrayList<String> names = new ArrayList<String>();

    Group(int i, String type){
        super(type);
        group = new LinkedBlockingQueue<SantasFriend>(i);
        size = i;
	    this.type = type;
    }

    public void add(SantasFriend friend) throws InterruptedException{
         names.add(friend.name);
         group.put(friend);
    }

    public int getSize(){
        return group.size();
    }

    public void releaseGroup(CyclicBarrier work) throws InterruptedException, BrokenBarrierException {
	    Iterator<SantasFriend> groupList = group.iterator();
	    names.clear();
        while(groupList.hasNext()){
	        groupList.next().doneWithSanta();
        }
	    group.clear();
	    work.await();
    }

    public void groupActivity (Santa.UnitOfWork unitOfWork, CyclicBarrier work) throws InterruptedException, BrokenBarrierException {
        Thread.sleep(2000);
        for (String name : names){
            log(name, unitOfWork.name);
        }
    }
}