package piserver;
import java.net.*;
import java.io.*;

public class PiServer {
    public static void main(String[] args) throws IOException {



        int portNumber = 4444;
        boolean listening = true;
        
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) { 
            while (listening) {
	            new PiServerThread(serverSocket.accept()).start();
	        }
	    } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}