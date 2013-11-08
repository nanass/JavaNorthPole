package Server;

import Util.Data;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.concurrent.Future;

public class BroadcastOutMain {
    Broadcaster  b;
    public BroadcastOutMain(){
        this.b = BroadcasterFactory.getDefault().lookup("/");
    }

    private final ObjectMapper mapper = new ObjectMapper();

    public void send(Data data)  {
        Future br = b.broadcast("{\"message\": " + wrapInQuotes(data) + "," +
                "\"who\":\""+data.getWho()+"\","+
                "\"type\":\"northPole\"}");
    }
    private String wrapInQuotes(Data data){
        return ("Delivery".equals(data.getType()) ? data.getMessage() : "\"" + data.getMessage()+ "\" " );
    }
}
