/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preng12;

import gpio.GPIOCommunication;
import gpio.UltraSchallSensor;
import java.io.IOException;
import org.opencv.core.Mat;
import piserver.PiServer;

/**
 *
 * @author luca_
 */
public class PRENG12 {

    /**
     * @param args the command line arguments
     */
    static ActualPosition ap;
    static DetectionStatus ds;
    static GPIOCommunication gpioc;
    static Thread thread;
    public static String pushtolist;
    public static PiServer psinstance = PiServer.getInstance();

    public static void main(String[] args) throws InterruptedException, IOException {
        initialize();
    }

    public static void initialize() throws InterruptedException, IOException {
        //Socket
        psinstance = PiServer.getInstance();
        thread = new Thread(psinstance);
        thread.start();

        //GPIOCommunication
        GPIOCommunication gpioc = new GPIOCommunication();

        //RectangleDetection
        //DetectionStatus
        DetectionStatus ds = new DetectionStatus().getInstance();

        //ActualPosition
        ActualPosition ap = new ActualPosition().getInstance();

        //UltraSchallSensor
        UltraSchallSensor uss = new UltraSchallSensor();
        uss.run();

    }

    public synchronized static void runloop() throws InterruptedException {

        while (true) {
            Thread.sleep(1000);

            //   socket.sendPosition(ap.getX(),ap.getY());
            //  socket.sendLogs("Position aktualisiert");
            //    if(ds.getR()){
            //      gpioc.stopPinHigh();
            //    socket.sendLogs("Position Erkannt");
            //  socket.sendLogs("Melde Freedomboard Stoppen");
            // }
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

    }
}
