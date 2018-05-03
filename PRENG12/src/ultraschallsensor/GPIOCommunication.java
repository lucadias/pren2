package ultraschallsensor;

import com.pi4j.io.gpio.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GPIOCommunication {

    //GPIO Pins
    //GPIO Pins
    private static GPIOCommunication instance = null;

    private static GpioPinDigitalOutput sensorStartPin;
    private static GpioPinDigitalOutput sensorStopPin;

    private static GpioPinDigitalInput sensorLastPin;

    final static GpioController gpio = GpioFactory.getInstance();

    public static GPIOCommunication getInstance() {
        if (instance == null) {
            instance = new GPIOCommunication();
        }
        return instance;
    }

    public void initialize() throws InterruptedException {
        sensorStartPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_28); // Trigger pin as OUTPUT
        sensorStopPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_25); // Trigger pin as OUTPUT
        sensorLastPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_24, PinPullResistance.PULL_DOWN); // Echo pin as INPUT

        sensorStartPin.low();
        sensorStopPin.low();

    }

    public void startPinHigh() {
        sensorStartPin.high();

    }

    public void stopPinHigh() {
        sensorStopPin.high();
    }

    public void checkForLastPin() throws InterruptedException {
        while (sensorLastPin.isLow()) {
            Thread.sleep(200);
            preng12.PRENG12.lastPinHigh();
        }
    }

    public void run() throws InterruptedException {
        checkForLastPin();
    }
}
