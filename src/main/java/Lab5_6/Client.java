package Lab5_6;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Client {
    private static final List<String> inMessages = new ArrayList<>();
    private static final List<String> outMessages = new ArrayList<>();

    private static final Semaphore recvSem = new Semaphore(1);
    private static final Semaphore sendSem = new Semaphore(1);

    public static void send(String msg) {
        try {
            sendSem.acquire();
            outMessages.add(msg);
            sendSem.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        InetAddress group = InetAddress.getByName("228.5.6.7");
        MulticastSocket socket = new MulticastSocket(6789);
        socket.joinGroup(group);
        Receiver receiver = new Receiver(socket, inMessages, recvSem, () -> {
            try {
                recvSem.acquire();
                System.out.println(inMessages.get(0));
                inMessages.remove(0);
                recvSem.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Sender sender = new Sender(socket, outMessages, sendSem, group);
        receiver.start();
        sender.start();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj swoj nick: ");
        String nick = scanner.nextLine();
        while(true)
        {
            System.out.println("Podaj wiadomosc: ");
            String message;
            message=scanner.nextLine();
            send(nick + "|" + message);
        }
    }
}
