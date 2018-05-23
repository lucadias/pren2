package piserver;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import preng12.DetectionStatus;

public class PiServerThread extends Thread {

    private Socket socket = null;
    public static PiServerProtocol pspinstance = PiServerProtocol.getInstance();
    
    public PiServerThread(Socket socket) {
        super("PiServerThread");
        this.socket = socket;
    }

    @Override
    public void run() {

        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));) {
            String inputLine, outputLine;

            while (true) {
                inputLine = "";
                //while ((inputLine = in.readLine()) != null) {
                if(in.ready()){
                    inputLine = in.readLine();
                    System.out.println("signal erkannt, wird analisiert");
                   
                }
                outputLine = pspinstance.processInput(inputLine);
                out.println(outputLine);
//                if (outputLine.equals("Bye")) {
                //                  break;
                //            }
                Thread.sleep(100);
            }
//            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException ex) {
            Logger.getLogger(
                    PiServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
