package Server;

import Util.Data;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.codehaus.jackson.map.ObjectMapper;
import java.io.IOException;

public class BroadcastOutMain {
    Broadcaster  b;
    public BroadcastOutMain(){
        this.b = BroadcasterFactory.getDefault().lookup("/");
    }

    private final ObjectMapper mapper = new ObjectMapper();

    public void send(Data data)  {
        try {
            b.broadcast(
                    mapper.writeValueAsString(
                            mapper.readValue(
                                    "{\"message\":\"" + data.getMessage() +
                                      "\",\"who\":\"" + data.getWho() +
                                 "\"}",
                                    Data.class)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
