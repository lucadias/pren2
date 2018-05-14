/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preng12;

import RectangleDetection.ObjRecognition;
import gpio.GPIOCommunication;
import gpio.UltraSchallSensor;

import java.io.IOException;
import org.opencv.core.Mat;
import piserver.PiServer;
import desktopapp.clientsocket.PiServerClient;
import piserver.EchoServer;
import piserver.PiServerProtocol;

/**
 *
 * @author luca_
 */
public class PRENG12 {

    /**
     * @param args the command line arguments
     */
    static GPIOCommunication gpioc;
    static Thread thread;

    static Thread thread2;
    public static String pushtolist;
    public static PiServer psinstance = PiServer.getInstance();

    // public static EchoServer echoserver = EchoServer.getInstance();
    public static PiServerProtocol psp = PiServerProtocol.getInstance();
    public static ActualPosition ap = ActualPosition.getInstance();
    public static DetectionStatus dp = DetectionStatus.getInstance();
    public static boolean startTrue = false;

    public static void main(String[] args) throws InterruptedException, IOException {
        initialize();
    }

    public static void initialize() throws InterruptedException, IOException {
        //Socket
        psinstance = PiServer.getInstance();

//        echoserver = EchoServer.getInstance();
        thread = new Thread(psinstance);
        thread.start();

        //GPIOCommunication
        // GPIOCommunication gpioc = new GPIOCommunication();
        //RectangleDetection9
        //DetectionStatus
        dp = DetectionStatus.getInstance();

        //ActualPosition
        ap = ActualPosition.getInstance();

        psp = PiServerProtocol.getInstance();

        //  ObjRecognition
        //   ObjRecognition obc = new ObjRecognition();
        //  obc.initialize();
        //UltraSchallSensor
        UltraSchallSensor uss = new UltraSchallSensor();
        thread2 = new Thread(uss);
        thread2.start();
        runloop();

    }

    public synchronized static void runloop() throws InterruptedException {

        int xp = 0;
        int yp = 0;
        while (true) {

            //  ap.updateX(xp+=1);
            //ap.updateY(yp+=1);
            System.out.println("Server" + ap.getX());
            System.out.println("Wait 10 Seconds and then send Position");
            Thread.sleep(3000);

            System.out.println("Send:" + ap.getX() + " " + ap.getY());

            psp.sendPosition(ap.getX(), ap.getY());
            ap.updateToSend(true);
            //  socket.sendLogs("Position aktualisiert");
            //    if(ds.getR()){
            //      gpioc.stopPinHigh();
            //    socket.sendLogs("Position Erkannt");
            //  socket.sendLogs("Melde Freedomboard Stoppen");
            // }
        }
        // System.out.println("start false");
    }

    public synchronized static void objectRecognized() {

    }

    public synchronized static void sendMatLogic(Mat bild) {
        //socket.sendMat(Mat bild);
    }

    public synchronized static void updateDistanz(double Distanz) {

    }

    public synchronized static void lastPinHigh() {

    }
}
