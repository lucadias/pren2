package desktopapp.clientsocket;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PiServerClient extends Thread {

    public static String towrite;
    private static PiServerClient instance = null;

    protected PiServerClient() {
        // Exists only to defeat instantiation.
    }

    private static final Object INSTANCE_LOCK = new Object();

    public static PiServerClient getInstance() {
        synchronized (INSTANCE_LOCK) {
            if (instance == null) {
                instance = new PiServerClient();
            }
        }
        return instance;
    }

    public static void initialize() throws IOException, InterruptedException {

        String hostName = "localhost";
        int portNumber = 4444;

        try (
                Socket psSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(psSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(psSocket.getInputStream()));) {
            BufferedReader stdIn
                    = new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;

            while ((fromServer = in.readLine()) != null) {
                System.out.println("Server: " + fromServer);
                if (fromServer.equals("Bye.")) {
                    break;
                }
                while (towrite == null) {
                //System.out.println(towrite);
                Thread.sleep(10);
                }
                out.println("startButtonPressed");

                
                towrite = null;
                /*fromUser = stdIn.readLine();

                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                }*/
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to "
                    + hostName);
            System.exit(1);
        }
    }

    @Override
    public void run() {
        System.out.println("Run called");
        try {
            initialize();
        } catch (IOException ex) {
            Logger.getLogger(PiServerClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(PiServerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void startButtonPressed() {
        towrite = "startButtonPressed";
        System.out.println(towrite);
    }
}
