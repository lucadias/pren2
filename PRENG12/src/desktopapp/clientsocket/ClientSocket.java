/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopapp.clientsocket;

import desktopapp.ClientFrame;
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
    String response;
    String userInput;
    Socket socket = null;
    BufferedReader br;

    public ClientSocket() throws IOException {
        System.out.println("Creating socket to '" + host + "' on port " + portNumber);

    }

    @Override
    public void run() {
        while (true) {
            read();
            socketwrite();
        }
    }

    public void socketwrite() {
        try {
            BufferedReader userInputBR = new BufferedReader(new InputStreamReader(System.in));
            if (userInput == null) {
                userInput = userInputBR.readLine();
            }
            out.println(userInput);

            response = br.readLine();
            System.out.println("server says:" + response);

            userInput = null;
        } catch (IOException ex) {

        }

    }

    public void read() {
        try {
            socket = new Socket(host, portNumber);

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("server says:" + br.readLine());
        } catch (IOException ex) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void write(String text) {
        System.out.println(text);
        userInput = text;
        read();
    }

}
