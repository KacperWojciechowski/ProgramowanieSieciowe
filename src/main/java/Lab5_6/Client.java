package Lab5_6;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
    private static final List<String> inMessages = new ArrayList<>();
    private static final List<String> outMessages = new ArrayList<>();

    private static final Semaphore recvSem = new Semaphore(1);
    private static final Semaphore sendSem = new Semaphore(1);
    private static GUI gui;
    private static String nick;

    public static void send(String msg) {
        try {
            //System.out.println("Message added");
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
        gui = new GUI();
        Receiver receiver = new Receiver(socket, inMessages, recvSem, () -> {
            try {
                recvSem.acquire();
                String message = inMessages.get(0);
                inMessages.remove(0);
                recvSem.release();
                System.out.println(message);
                Pattern pattern = Pattern.compile("\\[(?<nick>[A-Za-z0-9]+)\\]:?\\{(?<command>[A-Za-z]+)\\}?\\((?<message>[A-Za-z0-9_ ]*)\\)");
                Matcher matcher = pattern.matcher(message);
                System.out.println("Inside receiver callback");
                if (matcher.find())
                {
                    String nick = matcher.group("nick");
                    String command = matcher.group("command");
                    String msg = matcher.group("message");
                    System.out.println(nick + " " + command + " " + msg);
                    process(nick, command, msg);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Sender sender = new Sender(socket, outMessages, sendSem, group);
        receiver.start();
        sender.start();
        nick = gui.start();
        gui.keepAlive();
    }

    private static void process(String sender, String command, String msg) {
        if (command.equalsIgnoreCase("Alive") && msg.equalsIgnoreCase(nick))
        {
            gui.process(sender, msg, Target.ONLINELIST, Operation.APPEND);
        }
        else if (command.equalsIgnoreCase("Refresh") && msg.equalsIgnoreCase(nick))
        {
            gui.process(sender, msg, Target.ONLINELIST, Operation.CLEAR);
        }
        else if (command.equalsIgnoreCase("Refresh"))
        {
            Client.send("[" + nick + "]{Alive}(" + sender + ")");
        }
        else if (command.equalsIgnoreCase("Message"))
        {
            System.out.println("Nick: " + sender + " Message: " + msg);
            gui.process(sender, msg, Target.CHAT, Operation.APPEND);
        }
    }
}
