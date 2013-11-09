package NorthPole;

import Util.Data;
import Util.OutputService;

public class NorthPoleConcurrentObject extends Thread{

    String name;
    private final OutputService outPrint;

    NorthPoleConcurrentObject(String name){
        outPrint = new OutputService("5565");
        this.name = name;
    }
    @Override
    public void run(){}
    public void log(String message){
        System.out.println(name + ": "  + message);
        outPrint.send(new Data(name, message));
    }
    public void log(String n, String message){
        System.out.println(n + ": "  + message);
        outPrint.send(new Data(n, message));
    }
}
