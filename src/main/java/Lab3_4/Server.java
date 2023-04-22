package Lab3_4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server {

    private static final int port = 2011;

    private static final List<Integer> database = new ArrayList<Integer>();

    private static String processQuery(String inQuery) throws RuntimeException {
        int getPos = inQuery.indexOf("get");
        int setPos = inQuery.indexOf("set");

        System.out.println(inQuery);

        Pattern keyPattern = Pattern.compile("[0-9]+");
        Matcher matcher = keyPattern.matcher(inQuery);
        boolean keyWasFound = matcher.find();

        System.out.println(keyWasFound ? "key Found" : "key Not Found");

        if (getPos != -1 && keyWasFound) {
            int foundKey = Integer.parseInt(matcher.group(0));
            return "key_out-{" + foundKey + " : " + processGet(foundKey) + "};";
        }
        if (setPos != -1 && keyWasFound) {
            Pattern passwordPattern = Pattern.compile("pass_password");
            Matcher passwordMatcher = passwordPattern.matcher(inQuery);
            if (passwordMatcher.find()) {
                int foundKey = Integer.parseInt(matcher.group(0));
                return "key_out-{" + foundKey + " : " + processSet(foundKey) + "};";
            }
        }
        throw new RuntimeException("Malformed 'in' query");
    }

    private static String processGet(int key) {
        return database.contains(key) ? "yes" : "no";
    }

    private static String processSet(int key) {
        if (database.contains(key)) {
            return "error";
        } else {
            database.add(key);
            return "yes";
        }
    }

    public static void main(String[] args) {
        while (true) {
            try (
                    ServerSocket serverSocket = new ServerSocket(port);
                    Socket clientSocket = serverSocket.accept();
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ) {
                while (true) {
                    String inputLine, outputLine;
                    inputLine = in.readLine();
                    System.out.println(inputLine);
                    if (inputLine != null) {
                        int inSpecifierPos = inputLine.indexOf("in");
                        int inSpecifierLength = inputLine.indexOf('-') - inSpecifierPos + 1;
                        if (inSpecifierPos == -1 || inSpecifierLength < 0) {
                            throw new RuntimeException("Invalid query");
                        } else {
                            outputLine = processQuery(inputLine.substring(inSpecifierPos + inSpecifierLength));
                            out.println(outputLine);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Exception occured. Dropping connection");
            }
        }
    }
}