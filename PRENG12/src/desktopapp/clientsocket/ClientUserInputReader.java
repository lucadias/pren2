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

class ClientUserInputReader extends Thread {
    Socket serverSocket;
    public ClientUserInputReader(Socket serverSocket){
        this.serverSocket = serverSocket;
    }
    public void run(){
        BufferedReader stdIn = new BufferedReader(
                 new InputStreamReader(System.in));
        PrintWriter out;
        try {
            out = new PrintWriter(serverSocket.getOutputStream(), true);
            String userInput;

            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println("userinput" + userInput);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}