package piserver;

import java.net.*;
import java.io.*;
import preng12.DetectionStatus;
import static preng12.PRENG12.dp;

public class PiServerProtocol {

    public static String PiServerProtocol;
    private static PiServerProtocol instance = null;
    public static boolean lastaufgenommen = false;

    public static DetectionStatus dp = DetectionStatus.getInstance();

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
        dp = DetectionStatus.getInstance();

        //System.out.println(theInput);
        if ("startButtonPressed".equals(theInput)) {
            System.out.println("StartButton Press erkannt");
            preng12.PRENG12.bool_startSignalErhalten = true;
            return "StartButton Press erkannt";

        }
        if ("stopButtonPressed".equals(theInput)) {
            System.out.println("StopButton Press erkannt");
            dp.updateR(true);
            return "StopButton Press erkannt";
        }
        if ("".equals(theInput)) {
            return "PosX: " + posX + ", PosY: " + posY;
        }
        if (sendpos) {
            sendpos = false;
            // System.out.println("Sendposition processed input");
            return "PosX: " + posX + ", PosY: " + posY;
        }
        if (lastaufgenommen) {
            lastaufgenommen = false;
            return "Last aufgenommen!";

        }
        return theOutput;
    }

    public void sendPosition(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        sendpos = true;
    }

}
