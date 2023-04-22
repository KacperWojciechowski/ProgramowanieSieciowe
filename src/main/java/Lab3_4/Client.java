package Lab3_4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    private static final int port = 2011;
    public static void main(String[] args)
    {
        String hostname = "127.0.0.1";

        while(true)
        {
            try (
                    Socket clientSocket = new Socket(hostname, port);
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
            ) {
                System.out.println("Connected");
                Scanner scanner = new Scanner(System.in);
                while(true)
                {
                    String userIn = scanner.nextLine();
                    out.println(userIn);
                    String response = in.readLine();

                    if (response != null)
                    {
                        System.out.println(response);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Disconnected");
            }
        }

    }
}
