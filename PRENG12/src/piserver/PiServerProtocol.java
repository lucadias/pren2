package piserver;

import java.net.*;
import java.io.*;

public class PiServerProtocol {

    public static String PiServerProtocol;
    private static PiServerProtocol instance = null;

    protected PiServerProtocol() {
        // Exists only to defeat instantiation.
    }

    private static final Object INSTANCE_LOCK = new Object();

    public static PiServerProtocol getInstance() {
        synchronized (INSTANCE_LOCK) {
            if (instance == null) {
                instance = new PiServerProtocol();
            }
        }
        return instance;
    }
    private int posX;
    private int posY;
    private boolean sendpos = false;

    public String processInput(String theInput) {
        String theOutput = null;

        if (sendpos) {
            sendpos = false;
           // System.out.println("Sendposition processed input");
            return "PosX: " + posX + ", PosY: " + posY;
        }
        if ("startButtonPressed".equals(theInput)) {
            System.out.println("StartButton Press erkannt");
            return "StartButton Press erkannt";
        }
        if ("position".equals(theInput)) {
            return "PosX: " + posX + ", PosY: " + posY;
        }
        return theOutput;
    }

    public void sendPosition(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        sendpos = true;
    }

}
