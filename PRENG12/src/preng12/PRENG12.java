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

    static Thread gpiocthread;
    public static String pushtolist;
    public static PiServer psinstance = PiServer.getInstance();

    // public static EchoServer echoserver = EchoServer.getInstance();
    public static PiServerProtocol psp = PiServerProtocol.getInstance();
    public static ActualPosition ap = ActualPosition.getInstance();
    public static DetectionStatus dp = DetectionStatus.getInstance();
    public static boolean bool_startSignalErhalten = false;
    public static boolean lastaufgenommen = false;

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
        gpioc = GPIOCommunication.getInstance();
        gpiocthread = new Thread(gpioc);
        gpiocthread.start();
        //RectangleDetection9
        //DetectionStatus
        dp = DetectionStatus.getInstance();

        //ActualPosition
        ap = ActualPosition.getInstance();

        psp = PiServerProtocol.getInstance();

        lastaufgenommen = false;
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

            //überprüfe ob PiServerProtocol das Startsignal erhalten hat
            if (bool_startSignalErhalten) {
                //Setze den Startpin auf Hoch
                gpioc.startPinHigh();

            }
            dp.updateR(false);
            while (bool_startSignalErhalten) {

                //Sende die Positionsdaten and den NotebookClient
                psp.sendPosition(ap.getX(), ap.getY());

                //Überorüfe ob die Last aufgenommen wurde
                //   System.out.println(lastaufgenommen);
                while (dp.getR()) {
                    gpioc.stopPinHigh();
                    System.out.println("important test");
                    dp.updateR(false);

                }
                while (lastaufgenommen) {
                    System.out.println(lastaufgenommen);

                    //Sende
                    ap.updateY(yp += 1);
                    psp.sendPosition(ap.getX(), ap.getY());
                    Thread.sleep(100);

                    //Überprüfe ob RectangleDetection 
                    while (dp.getR()) {

                        gpioc.stopPinHigh();

                    }

                }

            }

        }
    }

    public synchronized static void objectRecognized() {

    }

    public synchronized static void sendMatLogic(Mat bild) {
        //socket.sendMat(Mat bild);
    }

    public synchronized static void updateDistanz(double Distanz) {

    }

    public synchronized static void lastPinHigh() {
        lastaufgenommen = true;
    }
}
