package ultraschallsensor;

import com.pi4j.io.gpio.*;

public class main {
    //GPIO Pins

    private static GpioPinDigitalOutput sensorTriggerPin;
    private static GpioPinDigitalOutput sensorStartPin;
    private static GpioPinDigitalOutput sensorStopPin;
    private static GpioPinDigitalOutput sensorMittePin;

    private static GpioPinDigitalInput sensorEchoPin;
    private static GpioPinDigitalInput sensorLastPin;

    final static GpioController gpio = GpioFactory.getInstance();

    public static void main(String[] args) throws InterruptedException {
        new main().run();
    }

    public void run() throws InterruptedException {
        sensorTriggerPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00); // Trigger pin as OUTPUT
        sensorEchoPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN); // Echo pin as INPUT

        sensorStartPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_20); // Trigger pin as OUTPUT
        sensorStopPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_19); // Trigger pin as OUTPUT


        sensorLastPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_06, PinPullResistance.PULL_DOWN); // Echo pin as INPUT

        //GPIO 20 start
        
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

                System.out.println("Distance :" + ((((endTime - startTime) / 1e3) / 2) / 29.1) + " cm"); //Printing out the distance in cm  
                Thread.sleep(100);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
