package NorthPole;

public class NorthPoleConcurrentObject extends Thread{

    String name;
    NorthPoleConcurrentObject(String name){
        this.name = name;
    }
    @Override
    public void run(){}
    public void log(String message){
        System.out.println(name + ": "  + message);
    }
    public void log(String n, String message){
        System.out.println(n + ": "  + message);
    }
}
