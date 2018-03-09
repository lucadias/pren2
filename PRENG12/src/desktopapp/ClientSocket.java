/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author luca_
 */
public class ClientSocket {

    static PrintWriter out;


    public ClientSocket() throws IOException {
        final String host = "localhost";
        final int portNumber = 4444;
        System.out.println("Creating socket to '" + host + "' on port " + portNumber);

        while (true) {

            Socket socket = new Socket(host, portNumber);
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
        }
    }
}
