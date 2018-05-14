/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopapp.clientsocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ThreadedClient extends Thread {

    public static String towrite;
    private static ThreadedClient instance = null;

    protected ThreadedClient() {
        // Exists only to defeat instantiation.
    }

    private static final Object INSTANCE_LOCK = new Object();

    public static ThreadedClient getInstance() {
        synchronized (INSTANCE_LOCK) {
            if (instance == null) {
                instance = new ThreadedClient();
            }
        }
        return instance;
    }

    @Override
    public void run() {
        String hostName = "192.168.1.101";

        try {
            Socket serverSocket = new Socket(hostName, 4444);
            ClientServerOutputReader csor = new ClientServerOutputReader(serverSocket);
            csor.start();
            ClientUserInputReader cuir = new ClientUserInputReader(serverSocket);
            cuir.start();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to "
                    + hostName);
            System.exit(1);
        }
    }

    public void startButtonPressed() {
        towrite = "startButtonPressed";
        // System.out.println(towrite);
    }

}
