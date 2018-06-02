package RectangleDetectionHeadless;


public class ObjRecognition  extends Thread {

    boolean firsttime = true;
    
    @Override
    public void run(){
        while(true){
        if(this.firsttime){
            firsttime = false;
            initialize();
        }
        }
    }
    
    public static void initialize() {

         System.load("/home/pi/opencv-3.3.1/build/lib/libopencv_java331.so");
        //System.load("C:/Users/luca_/Documents/NetBeansProjects/PrenTutorialOpenCV/lib/opencv_java331.dll");


            ObjRecognitionController controller = new ObjRecognitionController();
            
            controller.startCamera();
    }
}
