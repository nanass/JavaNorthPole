package WishList;

import Util.Command;
import Util.Data;
import Util.OutputService;

import java.util.concurrent.CopyOnWriteArrayList;

public final class WishList implements Command {

    private static CopyOnWriteArrayList<Data> wishList = new CopyOnWriteArrayList<Data>();
    OutputService out;
    public WishList(OutputService out){
        this.out = out;
    }


    public void deliverGifts(){
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
        out.send(data);
    }

    public static void addToWishList(Data msg){
        if(msg.getType().equals("wishlist")){
            wishList.add(msg);
            System.out.println(msg.getMessage());
        }
    }

    @Override
    public void execute() {}

    @Override
    public void execute(Data data) {
        if(data.getMessage().equals("Deliver"))
            deliverGifts();
        else
            addToWishList(data);
    }
}
