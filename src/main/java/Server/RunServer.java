package Server;

import Util.InputService;
import Util.OutputService;
import java.lang.String;
import java.lang.Thread;

public class RunServer {

    public static void main(String[] args){
        NettoServer ns = new NettoServer();

        OutputService out = new OutputService("5563");
        BroadcastOutMain broadcast = new BroadcastOutMain();
        NorthPole.SendToOutput.out = out;
        new Thread(new InputService(broadcast, "5564")).start();
        new Thread(new InputService(broadcast, "5565")).start();
    }
}
