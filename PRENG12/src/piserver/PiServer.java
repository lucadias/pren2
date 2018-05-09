package piserver;

import java.net.*;
import java.io.*;

public class PiServer extends Thread {

    public PiServer() {
//asdf
    }
    private static PiServer psinstance = null;
    private static final Object INSTANCE_LOCK = new Object();

    public static PiServer getInstance() {
        synchronized (INSTANCE_LOCK) {
            if (psinstance == null) {
                psinstance = new PiServer();
            }
        }
        return psinstance;
    }

    @Override
    public void run() {

        int portNumber = 4444;
        boolean listening = true;
        System.out.println("Run called");
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("Server initialized.");
            while (listening) {
                new PiServerThread(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}
