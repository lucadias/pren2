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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luca_
 */
public class ClientSocket implements Runnable {

    static PrintWriter out;
    final String host = "localhost";
    final int portNumber = 4444;

    public ClientSocket() throws IOException {
        System.out.println("Creating socket to '" + host + "' on port " + portNumber);

    }

    @Override
    public void run() {
        Socket socket = null;
        while (true) {
            try {
                socket = new Socket(host, portNumber);

                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                System.out.println("server says:" + br.readLine());

                BufferedReader userInputBR = new BufferedReader(new InputStreamReader(System.in));
                String userInput = userInputBR.readLine();

                out.println(userInput);

                System.out.println("server says:" + br.readLine());

                if ("exit".equalsIgnoreCase(userInput)) { 
                   socket.close();
                    break;
                }
            } catch (IOException ex) {
                Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void write(String text) {

        out.println(text);
    }
}
