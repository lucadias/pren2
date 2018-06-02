package gpio;

import com.pi4j.io.gpio.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import piserver.PiServerProtocol;

public class GPIOCommunication extends Thread {

    //GPIO Pins
    //GPIO Pins
    private static GPIOCommunication instance = null;

    private static GpioPinDigitalOutput sensorStartPin;
    int i;
    private static GpioPinDigitalOutput sensorStopPin;
    private static GpioPinDigitalInput sensorLastPin;

    final static GpioController gpio = GpioFactory.getInstance();
    public static PiServerProtocol pspinstance = PiServerProtocol.getInstance();

    public static GPIOCommunication getInstance() {
        if (instance == null) {
            instance = new GPIOCommunication();
        }
        return instance;
    }

    public void initialize() throws InterruptedException {
        sensorStartPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_28);  //OUTPUT: Startsignal
        sensorStopPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_27);  //OUTPUT: Zielplatform erkannt
        sensorLastPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_24, PinPullResistance.PULL_DOWN); //INPUT: Lauf aufgenommen

        sensorStartPin.low();
        sensorStopPin.low();

        int i = 0;
    }

    public void startPinHigh() {
        sensorStartPin.high();

        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            Logger.getLogger(GPIOCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
        startPinLow();
    }

    public static void startPinLow() {
        sensorStartPin.low();
    }

    public static void stopPinHigh() {
        sensorStopPin.high();
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            Logger.getLogger(GPIOCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
        startPinLow();
        sensorStopPin.low();
    }

    public void checkForLastPin() throws InterruptedException {

        while (sensorLastPin.isLow()) {
            System.out.println("Low:" + sensorLastPin.isLow() + (i += 1));

            Thread.sleep(2000);
            if (sensorLastPin.isHigh()) {
                System.out.println("High:" + sensorLastPin.isHigh());
                System.out.println("sensorlastpin high");
                //preng12.PRENG12.lastPinHigh();
                preng12.PRENG12.lastaufgenommen = true;

            }
        }

    }

    @Override
    public void run() {
        try {
            initialize();
            while (true) {
                checkForLastPin();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(GPIOCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
