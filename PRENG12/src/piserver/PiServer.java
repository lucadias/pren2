/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package piserver;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author luca_
 */
public class PiServer {
    
        public static void main(String[] args) throws Exception {

        // create socket
        int port = 4444;
        ServerSocket serverSocket = new ServerSocket(port);
        System.err.println("Started server on port " + port);

        // repeatedly wait for connections, and process
        while (true) {

            // a "blocking" call which waits until a connection is requested
            Socket clientSocket = serverSocket.accept();
            System.err.println("Accepted connection from client");

            // open up IO streams
            /*InputStream  in  = new InputStream(clientSocket);
            OutputStream out = new OutputStream(clientSocket);

            // waits for data and reads it in until connection dies
            // readLine() blocks until the server receives a new line from client
            String s;
            while ((s = in.readLine()) != null) {
                out.println(s);
            }

            // close IO streams, then socket
            System.err.println("Closing connection with client");
            out.close();
            in.close();
            clientSocket.close();
        }
*/
        }
        }
    
}
