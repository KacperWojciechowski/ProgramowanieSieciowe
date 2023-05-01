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
        Client.send("[" + nick + "]{Refresh}()");
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
