package NorthPole;

import Server.NettoServer;
import WishList.WishList;

public class NorthPole
{
     public static void main (String[] args){
        NettoServer ns = new NettoServer();
        WishList wishList = new WishList();
        Server.NorthPole.InjectWishList.wishList = wishList;
        final SnackRoom sr = new SnackRoom();
	    final Santa santa = new Santa(sr);
        final MrsClaus mrsClaus = new MrsClaus(sr);
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
        (new Thread(mrsClaus)).start();
     }
}