package NorthPole;

public class MrsClaus extends NorthPoleConcurrentObject{

    public MrsClaus(SnackRoom sr){
        super("MrsClaus");
        this.sr = sr;
    }

    final private SnackRoom sr;
    final private String name = "MrsClaus";

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(5000);
                log("Going to Santa's");
                Thread.sleep(10000);
                log("Waiting with cookies");
                sr.waitWithCookies();

            } catch (java.lang.InterruptedException e) {
                e.printStackTrace();
            }
            log("Done with Cookies");
        }
    }
}
