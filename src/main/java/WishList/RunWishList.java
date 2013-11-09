package WishList;

import Util.InputService;
import Util.OutputService;import WishList.WishList;import java.lang.String;import java.lang.Thread;

public class RunWishList {
    public static void main(String[] args){

        OutputService out = new OutputService("5564");
        WishList wishList = new WishList(out);
        new Thread(new InputService(wishList, "5563")).start();
        new Thread(new InputService(wishList, "5566")).start();

    }
}
