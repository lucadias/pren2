/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package piserver;

import java.net.*;
import java.io.*;
import preng12.ActualPosition;

public class EchoServer extends Thread {

    public static PiServerProtocol pspinstance = PiServerProtocol.getInstance();
    public static ActualPosition ap = ActualPosition.getInstance();

    private static int portNumber;

    public EchoServer() {
//asdf
    }
    private static EchoServer psinstance = null;
    private static final Object INSTANCE_LOCK = new Object();

    public static EchoServer getInstance() {
        synchronized (INSTANCE_LOCK) {
            if (psinstance == null) {
                psinstance = new EchoServer();
            }
        }
        return psinstance;
    }

    public void EchoServer() {

    }

    public static void startServer(String[] args) throws IOException {

        pspinstance = PiServerProtocol.getInstance();

        ap = ActualPosition.getInstance();

        int portNumber = 4444;

        try (
                ServerSocket serverSocket = new ServerSocket(4444);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {

            String inputLine = null;
            /*while ((inputLine = in.readLine()) != null) {
                out.println("From Server:" + inputLine);
                Thread.sleep(2000);
                out.println("Server status on");
            }*/
            while (true) {
                if (ap.getToSend()) {
                    out.println("Position X: " + ap.getX() + "Position Y: " + ap.getY());
                    ap.updateToSend(false);

                }
                
                if (in.ready()) {
                    inputLine += in.readLine();
                    System.out.println(inputLine);
                    out.println(pspinstance.processInput(inputLine));
                }
                Thread.sleep(100);
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
}
