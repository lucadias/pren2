package RectangleDetectionHeadless;

import static java.lang.Math.sqrt;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;
import org.opencv.imgcodecs.Imgcodecs;
import preng12.DetectionStatus;

public class Rectangle {

    boolean notwritten = true;
//                preng12.PRENG12.objectRecognized();
    //              preng12.PRENG12.sendMatLogic(bild);
    public int i = 0;
    public static DetectionStatus dp = DetectionStatus.getInstance();

    public Mat findRectangle(Mat src) throws Exception {

        Long starttime = System.nanoTime();
        //   System.out.println("findrectangle");
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
                            preng12.DetectionStatus.recognized = true;
                            dp.updateR(true);
                            // DetectionStatus.recognized = true;
                            //System.out.println(DetectionStatus.recognized);
                            if (notwritten) {
                                notwritten = false;
                                Imgcodecs.imwrite("bidl.jpg", src);
                            }

                        }
                        //      Imgproc.putText(src, middle.toString(), new Point(middle.x + 5, middle.y + 5), 0, 2, new Scalar(0, 0, 255, .8));

                    }
                }
            }
        }

        System.out.println(((double) System.nanoTime() - starttime) / 1000000000);

        return src;
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
