package gpio;

import com.pi4j.io.gpio.*;

public class UltraSchallSensor {

    //GPIO Pins
    private static UltraSchallSensor instance = null;
    private static GpioPinDigitalOutput sensorTriggerPin;
    private static GpioPinDigitalInput sensorEchoPin;

    final static GpioController gpio = GpioFactory.getInstance();

    public static UltraSchallSensor getInstance() {
        if (instance == null) {
            instance = new UltraSchallSensor();
        }
        return instance;
    }

    public void run() throws InterruptedException {
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

                preng12.PRENG12.updateDistanz(ussdistanz);

                
                Thread.sleep(100);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
