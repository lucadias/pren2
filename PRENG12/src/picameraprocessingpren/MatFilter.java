/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package picameraprocessingpren;

import java.awt.image.BufferedImage;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.resize;
import org.opencv.videoio.VideoCapture;

/**
 *
 * @author luca_
 */
public class MatFilter {

    Mat2Image mat2Img = new Mat2Image();

    Mat matfilter1 = new Mat();
    Mat source = new Mat();

    public MatFilter() {

    }

    public void filter1() {

        //matfilter1 = source.clone();

        Imgproc.cvtColor(source, matfilter1, Imgproc.COLORMAP_AUTUMN);

    }

    public BufferedImage getMat(VideoCapture cap) {

        cap.retrieve(source);

        Size size = new Size(1326,1000);
        
        filter1();
        resize(source, matfilter1, size);//resize image
        

        // filter1();
        // filter1(cap);
        return mat2Img.getImage(matfilter1);
    }
}
