package RectangleDetectionHeadless;

import static RectangleDetectionHeadless.Rectangle.dp;
import gpio.GPIOCommunication;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.List;
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
import org.opencv.core.CvType;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;

public class ObjRecognitionController {

    public static int i = 1;

    private VideoCapture capture = new VideoCapture(0);

    private boolean cameraActive;

    public void startCamera() {

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
                        while (true) {
                            // effectively grab and process a single frame
                            Mat frame = grabFrame();
                            // convert and show the frame
                        }

//                        Image imageToShow = Utils.matToBufferedImage(frame);
                        //saveImage(imageToShow);
                        //TODO : Send Image over Sockets
//            updateImageView(originalFrame, imageToShow);
                    }
                };

                Thread frammeGrabberThread = new Thread(frameGrabber);
                frameGrabber.run();
                Thread frammeGrabberThread2 = new Thread(frameGrabber);
                frameGrabber.run();
                Thread frammeGrabberThread3 = new Thread(frameGrabber);
                frameGrabber.run();

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
                //       this.starttime = System.nanoTime();
                //   System.out.println("findrectangle");

                this.capture.read(originalframe);

                Rect rectCrop = new Rect(120, 0, 400, 300);

                Imgproc.resize(new Mat(originalframe, rectCrop), frame, new Size(320, 240));

                // if the frame is not empty, process it
                if (!frame.empty()) {
                    // init

                    findRectangle(frame);

                }

            } catch (Exception e) {
                // log the (full) error
                System.err.print("Exception during the image elaboration...");
                e.printStackTrace();
            }
        }

        return frame;
    }

    /**
     * Stop the acquisition from the camera and release all the resources
     */
    private void stopAcquisition() {

    }

    protected void setClosed() {
        this.stopAcquisition();
    }

    public void findRectangle(Mat src) throws Exception {

        Mat blurred = src.clone();

        Imgproc.medianBlur(src, blurred, 9);
        Mat gray0 = new Mat(blurred.size(), CvType.CV_8U), gray = new Mat();

        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();

        List<Mat> blurredChannel = new ArrayList<Mat>();
        blurredChannel.add(blurred);
        List<Mat> gray0Channel = new ArrayList<Mat>();
        gray0Channel.add(gray0);

        MatOfPoint2f approxCurve;
        List<Point> maxCurves = null;

        double maxArea = 40;
        int maxId = -1;

        for (int c = 0; c < 3; c++) {
            int ch[] = {c, 0};
            Core.mixChannels(blurredChannel, gray0Channel, new MatOfInt(ch));

            int thresholdLevel = 1;
            for (int t = 0; t < thresholdLevel; t++) {
                if (t == 0) {
                    Imgproc.Canny(gray0, gray, 10, 20, 3, true); // true ?
                    Imgproc.dilate(gray, gray, new Mat(), new Point(-1, -1), 1); // 1
                    // ?
                } else {
                    Imgproc.adaptiveThreshold(gray0, gray, thresholdLevel,
                            Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
                            Imgproc.THRESH_BINARY,
                            (src.width() + src.height()) / 200, t);
                }

                Imgproc.findContours(gray, contours, new Mat(),
                        Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

                for (MatOfPoint contour : contours) {
                    MatOfPoint2f temp = new MatOfPoint2f(contour.toArray());

                    double area = Imgproc.contourArea(contour);
                    approxCurve = new MatOfPoint2f();
                    Imgproc.approxPolyDP(temp, approxCurve,
                            Imgproc.arcLength(temp, true) * 0.02, true);

                    if (approxCurve.total() == 4 && area >= maxArea) {
                        double maxCosine = 0;
                        //System.out.println("Found Rect at position: ");
                        List<Point> curves = approxCurve.toList();
                        for (int j = 2; j < 5; j++) {

                            double cosine = Math.abs(angle(curves.get(j % 4),
                                    curves.get(j - 2), curves.get(j - 1)));
                            maxCosine = Math.max(maxCosine, cosine);
                        }

                        if (maxCosine < 0.3) {
                            maxCurves = curves;
                            maxArea = area;
                            maxId = contours.indexOf(contour);
                        }
                    }

                }
            }
        }

        if (maxId >= 0) {

            Point p1 = maxCurves.get(0);
            Point p2 = maxCurves.get(1);
            Point p3 = maxCurves.get(2);
            Point p4 = maxCurves.get(3);

            Point middle = new Point();

            double diffX = Math.abs(p1.x - p2.x);
            double diffY = Math.abs(p1.y - p2.y);

            if (diffX > diffY) {
                if (p1.x > p2.x) {
                    middle.x = p2.x + diffX / 2;
                } else {
                    middle.x = p1.x + diffX / 2;
                }
                if (p1.y > p4.y) {
                    middle.y = p4.y + Math.abs(p1.y - p4.y) / 2;
                } else {
                    middle.y = p1.y + Math.abs(p1.y - p4.y) / 2;
                }
            } else {
                if (p1.x > p4.x) {
                    middle.x = p4.x + Math.abs(p1.x - p4.x) / 2;
                } else {
                    middle.x = p1.x + Math.abs(p1.x - p4.x) / 2;
                }
                if (p1.y > p2.y) {
                    middle.y = p2.y + diffY / 2;
                } else {
                    middle.y = p1.y + diffY / 2;
                }
            }
            double lengthx = sqrt((((p2.x - p1.x) * (p2.x - p1.x)) + ((p2.y - p1.y) * (p2.y - p1.y))));
            double lengthy = sqrt((((p3.x - p4.x) * (p3.x - p4.x)) + ((p3.y - p4.y) * (p3.y - p4.y))));

            if (lengthx > lengthy - 8 && lengthx < lengthy + 8) {
                if (lengthx > 60 && lengthy > 60 && lengthx < 300 && lengthy < 220) {

                    if (middle.x >= 100) {

                        if (middle.x <= 220) {

                            //  Imgproc.drawContours(src, contours, maxId, new Scalar(0, 255, 0, .8), 1);
                            System.out.println("erkannt");
                            Thread.sleep(preng12.ActualPosition.getX()/2);
                            preng12.DetectionStatus.recognized = true;
//dp.updateR(true);
                            GPIOCommunication.stopPinHigh();
                            // DetectionStatus.recognized = true;
                            //System.out.println(DetectionStatus.recognized);

                            //           Imgcodecs.imwrite("bidl.jpg", src);
                        }
                        //      Imgproc.putText(src, middle.toString(), new Point(middle.x + 5, middle.y + 5), 0, 2, new Scalar(0, 0, 255, .8));

                    }
                }
            }
        }

        //      System.out.println(((double) System.nanoTime() - this.starttime) / 1000000000);
    }

    public double angle(Point p1, Point p2, Point p0) {
        double dx1 = p1.x - p0.x;
        double dy1 = p1.y - p0.y;
        double dx2 = p2.x - p0.x;
        double dy2 = p2.y - p0.y;
        return (dx1 * dx2 + dy1 * dy2)
                / Math.sqrt((dx1 * dx1 + dy1 * dy1) * (dx2 * dx2 + dy2 * dy2)
                        + 1e-10);
    }

}
