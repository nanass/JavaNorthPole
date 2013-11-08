package NorthPole;

import Server.BroadcastOutMain;
import Util.Data;

public class NorthPoleConcurrentObject extends Thread{

    String name;
    BroadcastOutMain broadcast;
    NorthPoleConcurrentObject(String name){
        this.name = name;
        this.broadcast = new BroadcastOutMain();
    }
    @Override
    public void run(){}
    public void log(String message){
        System.out.println(name + ": "  + message);
        broadcast.send(new Data(name, message));
    }
    public void log(String n, String message){
        System.out.println(n + ": "  + message);
        broadcast.send(new Data(n, message));
    }
}
