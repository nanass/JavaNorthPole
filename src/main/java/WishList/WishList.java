package WishList;

import Server.BroadcastOutMain;
import Util.Data;

import java.util.concurrent.CopyOnWriteArrayList;

public final class WishList {

    private static CopyOnWriteArrayList<Data> wishList = new CopyOnWriteArrayList<Data>();
    private static BroadcastOutMain broadcast = new BroadcastOutMain();
    public static void deliverGifts(){
        String output = "[";
        System.out.println("Gifts for: ");
        for(Data d : wishList){
            output += "\"" + d.getAuthor() + " : " + d.getMessage() + "\",";
        }
        wishList.clear();
        output = output.substring(0,output.length() - 1) + "]";
        System.out.println(output);
        Data data = new Data("all", output);
        data.setType("Delivery");
        broadcast.send(data);
    }

    public static void addToWishList(Data msg){
        if(msg.getType().equals("wishlist")){
            wishList.add(msg);
            System.out.println(msg.getMessage());
        }
    }
}
