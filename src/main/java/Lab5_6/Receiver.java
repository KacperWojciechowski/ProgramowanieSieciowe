package Lab5_6;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Receiver extends Thread {
    private final List<String> msgList;
    private final MulticastSocket socket;
    private final Semaphore recvSem;
    private final GUICallback guiCallback;

    public Receiver(MulticastSocket socket, List<String> msgList, Semaphore recvSem, GUICallback guiCallback)
    {
        this.msgList = msgList;
        this.socket = socket;
        this.recvSem = recvSem;
        this.guiCallback = guiCallback;
    }

    @Override
    public void run()
    {
        byte[] buffer = new byte[1000];
        while(true)
        {
            DatagramPacket recv = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(recv);
                recvSem.acquire();
                msgList.add(new String(recv.getData(), 0, recv.getLength()));
                recvSem.release();
                guiCallback.signal();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}