package RectangleDetectionHeadless;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.opencv.core.Rect;

public class ObjRecognitionController {

    public static int i = 1;

    // a timer for acquiring the video stream
    private ScheduledExecutorService timer;
    // the OpenCV object that performs the video capture
    private VideoCapture capture = new VideoCapture(0);

    // a flag to change the button behavior
    private boolean cameraActive;

    // property for object binding
    public void startCamera() {
        // bind a text property with the string containing the current range of
        // HSV values for object detection

        // set a fixed width for all the image to show and preserve image ratio
        System.out.println("camerastart");

        if (!this.cameraActive) {
            // start the video capture
            this.capture.open(0);

            // is the video stream available?
            if (this.capture.isOpened()) {
                this.cameraActive = true;

                // grab a frame every 33 ms (30 frames/sec)
                Runnable frameGrabber = new Runnable() {

                    @Override
                    public void run() {

                        // effectively grab and process a single frame
                        Mat frame = grabFrame();
                        // convert and show the frame

                        Image imageToShow = Utils.matToBufferedImage(frame);
                        //saveImage(imageToShow);
                        //TODO : Send Image over Sockets
//            updateImageView(originalFrame, imageToShow);
                    }
                };

                this.timer = Executors.newSingleThreadScheduledExecutor();
                this.timer.scheduleAtFixedRate(frameGrabber, 0, 50, TimeUnit.MILLISECONDS);

            } else {
                // log the error
                System.err.println("Failed to open the camera connection...");
            }
        } else {
            // the camera is not active at this point
            this.cameraActive = false;

            // stop the timer
            this.stopAcquisition();
        }
    }

    /**
     * Get a frame from the opened video stream (if any)
     */
    private Mat grabFrame() {

        Mat frame = new Mat();
        Mat originalframe = new Mat();

        // check if the capture is open
        if (this.capture.isOpened()) {
            try {
                // read the current frame
                this.capture.read(originalframe);

                Rect rectCrop = new Rect(120, 0, 400, 300);

                Imgproc.resize(new Mat(originalframe, rectCrop), frame, new Size(160, 120));

                // if the frame is not empty, process it
                if (!frame.empty()) {
                    // init
                    Mat blurredImage = new Mat();
                    Mat hsvImage = new Mat();
                    Mat mask = new Mat();
                    Mat morphOutput = new Mat();

                    // remove some noise
                    Imgproc.blur(frame, blurredImage, new Size(7, 7));

                    // convert the frame to HSV
                    Imgproc.cvtColor(blurredImage, hsvImage, Imgproc.COLOR_BGR2HSV);

                    // get thresholding values from the UI
                    // remember: H ranges 0-180, S and V range 0-255
                    //         Scalar minValues = new Scalar(this.hueStart.getValue(), this.saturationStart.getValue(),
                    //               this.valueStart.getValue());
                    //     Scalar maxValues = new Scalar(this.hueStop.getValue(), this.saturationStop.getValue(),
                    //           this.valueStop.getValue());
                    // show the current selected HSV range
                    //  String valuesToPrint = "Hue range: " + minValues.val[0] + "-" + maxValues.val[0]
                    //        + "\tSaturation range: " + minValues.val[1] + "-" + maxValues.val[1] + "\tValue range: "
                    //      + minValues.val[2] + "-" + maxValues.val[2];
                    //Utils.onFXThread(this.hsvValuesProp, valuesToPrint);
                    // threshold HSV image to select tennis balls
                    Core.inRange(hsvImage, new Scalar(0, 0, 0, 0), new Scalar(0, 0, 0, 0), mask);
                    // show the partial output
//                    this.updateImageView(this.maskImage, Utils.mat2Image(mask));
                    // morphological operators
                    // dilate with large element, erode with small ones
                    Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(24, 24));
                    Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(12, 12));

                    //      System.out.println(mask.channels());
                    Imgproc.erode(mask, morphOutput, erodeElement);
                    Imgproc.erode(morphOutput, morphOutput, erodeElement);

                    Imgproc.dilate(morphOutput, morphOutput, dilateElement);
                    Imgproc.dilate(morphOutput, morphOutput, dilateElement);
                    // show the partial output
                    //                  this.updateImageView(this.morphImage, Utils.mat2Image(morphOutput));
                    //frame = this.findAndDrawBalls(morphOutput, frame);

                    Rectangle rect = new Rectangle();
                    rect.findRectangle(frame);

                }

            } catch (Exception e) {
                // log the (full) error
                System.err.print("Exception during the image elaboration...");
                e.printStackTrace();
            }
        }

        return frame;
    }

    private void saveImage(BufferedImage bframe) {
        System.out.println("hi");

        i++;
        File outputfile = new File("image" + i + ".jpg");
        try {
            ImageIO.write(bframe, "jpg", outputfile);
        } catch (IOException ex) {
            System.out.println("fail");
            Logger.getLogger(ObjRecognitionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Stop the acquisition from the camera and release all the resources
     */
    private void stopAcquisition() {
        if (this.timer != null && !this.timer.isShutdown()) {
            try {
                // stop the timer
                this.timer.shutdown();
                this.timer.awaitTermination(100, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                // log any exception
                System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
            }
        }

        if (this.capture.isOpened()) {
            // release the camera
            this.capture.release();
        }
    }

    //private void updateImageView(ImageView view, Image image)
    //{
    //   Utils.onFXThread(view.imageProperty(), image);
    //}
    /**
     * On application close, stop the acquisition from the camera
     */
    protected void setClosed() {
        this.stopAcquisition();
    }

}
