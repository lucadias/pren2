package gpio;

import com.pi4j.io.gpio.*;
import preng12.ActualPosition;
import static preng12.PRENG12.ap;
import static preng12.PRENG12.psp;

public class UltraSchallSensor extends Thread {

    //GPIO Pins
    private static UltraSchallSensor instance = null;
    private static GpioPinDigitalOutput sensorTriggerPin;
    private static GpioPinDigitalInput sensorEchoPin;

    final static GpioController gpio = GpioFactory.getInstance();
    public static ActualPosition ap = ActualPosition.getInstance();

    public static UltraSchallSensor getInstance() {
        if (instance == null) {
            instance = new UltraSchallSensor();
        }
        return instance;
    }

    @Override
    public void run() {
        sensorTriggerPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00); // Trigger pin as OUTPUT
        sensorEchoPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN); // Echo pin as INPUT
        System.out.println("UltraSchallSensor initialized");

        while (true) {
            try {
                Thread.sleep(50);
                sensorTriggerPin.high(); // Make trigger pin HIGH
                Thread.sleep((long) 0.01);// Delay for 10 microseconds
                sensorTriggerPin.low(); //Make trigger pin LOW

                while (sensorEchoPin.isLow()) { //Wait until the ECHO pin gets HIGH

                }
                long startTime = System.nanoTime(); // Store the surrent time to calculate ECHO pin HIGH time.
                while (sensorEchoPin.isHigh()) { //Wait until the ECHO pin gets LOW

                }
                long endTime = System.nanoTime(); // Store the echo pin HIGH end time to calculate ECHO pin HIGH time.

                double ussdistanz = ((((endTime - startTime) / 1e3) / 2) / 29.1);
                //  System.out.println(ussdistanz);

                while (true) {
                    if (preng12.PRENG12.lastaufgenommen) {

                        for (double i = 0; i < 60; i++) {
                            ap.updateY((int) (i * 0.75));
                            ap.updateToSend(true);
                            psp.sendPosition(ap.getX(), ap.getY());
                            Thread.sleep(100);
                        }
                        while (preng12.PRENG12.lastaufgenommen) {
                            while (!preng12.DetectionStatus.recognized) {

                                ap.updateY((int) (42 + (ap.getX() * 0.17)));
                                ap.updateX(ap.getX() + 4);
                                ap.updateToSend(true);
                                psp.sendPosition(ap.getX(), ap.getY());
                                Thread.sleep(200);

                            }
                            while (preng12.DetectionStatus.recognized) {
                                Thread.sleep(preng12.ActualPosition.getX()/2);
                                Thread.sleep(100);
                                int zwert = ap.getY();
                                while (true) {
                                    for (int i = 0; i < zwert; i++) {
                                        ap.updateY(ap.getY() - 1);
                                        ap.updateToSend(true);
                                        psp.sendPosition(ap.getX(), ap.getY());
                                        Thread.sleep(80);
                                    }
                                    while (true) {
                                        Thread.sleep(123);
                                    }
                                }
                            }
                        }
                    }
                }
                //   if(ussdistanz < ap.x+30 && ussdistanz > ap.x-30){

                // }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
