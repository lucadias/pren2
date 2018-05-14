/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopapp.clientsocket;

import desktopapp.ClientFrame;
import desktopapp.LabelValue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class ClientServerOutputReader extends Thread {

    Socket serverSocket;
    String outputFromServer;
    public static LabelValue lv = LabelValue.getInstance().getInstance();

    public ClientServerOutputReader(Socket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(serverSocket.getInputStream()));

            while (true) {
                outputFromServer = "";

                if (in.ready()) {
                    //This part is printing the output to console
                    //Instead it should be appending the output to some file
                    //or some swing element. Because this output may overlap
                    //the user input from console
                    outputFromServer = in.readLine();
                    System.out.println("test");
                    System.out.println(outputFromServer);
                    
                    lv.updatevalue(outputFromServer);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
