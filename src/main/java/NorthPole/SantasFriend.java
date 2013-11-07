package NorthPole;

import java.util.Random;
import java.util.concurrent.SynchronousQueue;

public class SantasFriend extends NorthPoleConcurrentObject {
    private Random randomGenerator = new Random();
    private final WaitingRoom waitingRoomRef;
	public final String type;
    SynchronousQueue<Integer> wait = new SynchronousQueue<Integer>();

    SantasFriend (String name, WaitingRoom waitingRoomRef, String type){
        super(name);
        this.waitingRoomRef = waitingRoomRef;
	    this.type = type;
    }

    public void withSanta() throws InterruptedException{
         wait.take();
    }

    public void doneWithSanta() throws InterruptedException{
        wait.put(1);
    }

    @Override
	public void run(){
        try{
            Thread.sleep(randomGenerator.nextInt(100) * 200);
        }
        catch (Exception e) {log("Interrupted exception");}
        while(true){
            try{
                Thread.sleep(randomGenerator.nextInt(100) * 200);
                log("Working");
                if (waitingRoomRef.receiveVisitor(this).equals("RoomFull")){
                    log("Waiting room is full");
                    Thread.sleep(randomGenerator.nextInt(100) * 200);
                }
                else{
                    log ("In the waiting room");
                    withSanta();
                    log("Going to work");
                    Thread.sleep(randomGenerator.nextInt(100) * 200);
                }
            }
            catch (InterruptedException e) {log("Interrupted exception");}
        }
    }
}