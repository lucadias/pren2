package ultraschallsensor;

import com.pi4j.io.gpio.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GPIOCommunication {
    //GPIO Pins

    private static GpioPinDigitalOutput sensorStartPin;
    private static GpioPinDigitalOutput sensorStopPin;

    private static GpioPinDigitalInput sensorLastPin;

    final static GpioController gpio = GpioFactory.getInstance();

    public static void main(String[] args) throws InterruptedException {
        // System.out.println("Hallo");
        // System.out.println("hallo3");
        sensorStartPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_28); // Trigger pin as OUTPUT
        sensorStopPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_25); // Trigger pin as OUTPUT
        // System.out.println("hallo4");
        sensorLastPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_24, PinPullResistance.PULL_DOWN); // Echo pin as INPUT
        String s = null;
        //GPIO 20 start
        // System.out.println("test");

        sensorStartPin.low();
        sensorStopPin.low();

        System.out.println("pins low");
        Thread.sleep(5000);
        sensorStartPin.high();

        Thread.sleep(10000);
        
        sensorStopPin.high();
//        sensorStartPin.low();

        while (sensorLastPin.isLow()) {
               Thread.sleep(200);
            System.out.println("chegge if last aufgenommen");
        }

        System.out.println("Last aufgenommen");
        sensorStartPin.low();
        Thread.sleep(15000);

        sensorStopPin.high();
        System.out.println("STOP PIN HIGH KOLLEGE");
        Thread.sleep(10000);

        
        System.out.println("set pins to low again - end sequence");
        sensorStartPin.low();
        sensorStopPin.low();
    }

    public void run() throws InterruptedException {

    }
}
