/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopapp.clientsocket;

import static desktopapp.clientsocket.PiServerClient.towrite;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

class ClientUserInputReader extends Thread {

    Socket serverSocket;
    public static String towrite;

    public ClientUserInputReader(Socket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void run() {
        try {
            
            PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
            
            String fromServer;

            while (true) {

                if (towrite == null) {
                    while (towrite == null) {
                        //System.out.println(towrite);
                        Thread.sleep(10);
                    }
                    System.out.println(towrite);
                    out.println(towrite);   
                        towrite = null;
                }
                //  towrite = null;
                /*fromUser = stdIn.readLine();

                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                }*/
                Thread.sleep(10);
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host ");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to ");
            System.exit(1);
        } catch (InterruptedException ex) {
            Logger.getLogger(ClientUserInputReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
