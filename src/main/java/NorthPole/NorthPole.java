package NorthPole;

public class NorthPole
{
     public static void main (String[] args){
	    final Santa santa = new Santa();
        final WaitingRoom waitingRoom = new WaitingRoom(santa, 3, "Elf");
	    final WaitingRoom stable = new WaitingRoom(santa, 9, "Reindeer");
        final String[] reindeerNames = { "Dasher","Dancer", "Prancer", "Vixen",
             "Comet","Cupid","Donder","Blitzen","Ruldolph" };
        final String[] elfNames = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
        santa.findWaitingRoom(waitingRoom);
	    santa.findStable(stable);
	    (new Thread(santa)).start();
        for (String name : reindeerNames){
            (new Thread(new SantasFriend(name, stable, "Reindeer"))).start();
        }
        for (String name : elfNames){
            (new Thread(new SantasFriend("Elf" + name, waitingRoom, "Elf"))).start();
        }
     }
}