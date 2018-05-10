package piserver;

import java.net.*;
import java.io.*;

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
                        new InputStreamReader(
                                socket.getInputStream()));) {
            String inputLine, outputLine;

            outputLine = pspinstance.processInput(null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                outputLine = pspinstance.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("Bye")) {
                    break;
                }
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
