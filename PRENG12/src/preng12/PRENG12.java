/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preng12;

import gpio.GPIOCommunication;
import gpio.UltraSchallSensor;
import org.opencv.core.Mat;

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
    public static void main(String[] args) throws InterruptedException {
        initialize();
    }

    
    public static void initialize() throws InterruptedException{
        //Socket
        
        
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
    
    public synchronized static void runloop() throws InterruptedException{
        
        
        
        while(true){
            Thread.sleep(1000);
            
            
         //   socket.sendPosition(ap.getX(),ap.getY());
          //  socket.sendLogs("Position aktualisiert");
            
            if(ds.getR()){
                gpioc.stopPinHigh();
            //    socket.sendLogs("Position Erkannt");
              //  socket.sendLogs("Melde Freedomboard Stoppen");
            }
            
                
            
            
        }
    }
    
    
    public synchronized static void objectRecognized(){
        
    }
    
    public synchronized static void sendMatLogic(Mat bild){
        //socket.sendMat(Mat bild);
    }
    
    
    public synchronized static void updateDistanz(double Distanz){
        
    }
    
    public synchronized static void lastPinHigh(){
        
    }
}
