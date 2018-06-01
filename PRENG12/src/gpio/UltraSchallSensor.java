package gpio;

import com.pi4j.io.gpio.*;
import preng12.ActualPosition;

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
                Thread.sleep(200);
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
              
              
                ap.updateX((int) ussdistanz);
                ap.updateToSend(true);

                Thread.sleep(100);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
