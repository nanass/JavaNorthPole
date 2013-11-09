package Server;

import Util.Command;
import Util.Data;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;

import java.util.concurrent.Future;

public class BroadcastOutMain implements Command {
    Broadcaster  b;
    public BroadcastOutMain(){
        this.b = BroadcasterFactory.getDefault().lookup("/");
    }

    public void send(Data data)  {
        Future br = b.broadcast("{\"message\": " + wrapInQuotes(data) + "," +
                "\"who\":\""+data.getWho()+"\","+
                "\"type\":\"northPole\"}");
    }

    private String wrapInQuotes(Data data){
        return ("Delivery".equals(data.getType()) ? data.getMessage() : "\"" + data.getMessage()+ "\" " );
    }

    @Override
    public void execute() {

    }

    @Override
    public void execute(Data data) {
        send(data);
    }
}
