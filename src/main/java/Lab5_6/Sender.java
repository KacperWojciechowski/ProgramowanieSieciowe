package Lab5_6;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Sender extends Thread {
    private final MulticastSocket socket;
    private final List<String> msgList;
    private final Semaphore sendSem;
    private final InetAddress group;
    public Sender(MulticastSocket socket, List<String> msgList, Semaphore sendSem, InetAddress group)
    {
        this.socket = socket;
        this.msgList = msgList;
        this.sendSem = sendSem;
        this.group = group;
    }

    @Override
    public void run()
    {
        while(true)
        {
            //System.out.println("Sender");
            try {
                sendSem.acquire();
                if(!msgList.isEmpty())
                {
                    //System.out.println("Sender 2");
                    String message = msgList.get(0);
                    msgList.remove(0);
                    DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), group, 6789);
                    socket.setTimeToLive(10);
                    socket.send(packet);
                }
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
            sendSem.release();
        }
    }
}