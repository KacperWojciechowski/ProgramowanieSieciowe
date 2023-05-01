package Lab5_6;

public class Refresher extends Thread{

    String nick;

    Refresher(String nick)
    {
        this.nick = nick;
    }
    @Override
    public void run()
    {
        while(true) {
            Client.send("[" + nick + "]{Refresh}()");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
