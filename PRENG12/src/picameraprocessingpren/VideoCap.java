package picameraprocessingpren;

import java.awt.image.BufferedImage;
import org.opencv.core.Core;
import org.opencv.videoio.VideoCapture;

public class VideoCap {
    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    VideoCapture cap;
    MatFilter matfilter1 = new MatFilter();
    
    

    VideoCap(){
        cap = new VideoCapture();
        cap.open(0);
        
        
    } 
 
    BufferedImage getOneFrame() {
        return matfilter1.getMat(cap);
        
    }
}