/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serial;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author luca_
 */
public class SerialInterfaceHandler implements Observer {
      private final String PORT_NAME = "/dev/ttyS80";
    private final int SERIAL_TIMEOUT = 2000;
    private final int BAUD_RATE = 9600;
    
    private static SerialInterfaceHandler theInstance;
    private InputStream serialInputStream;
    private OutputStream serialOutputStream;
    private final ControlData controlData;

    /**
     * Private Constructor (Singleton-Pattern).
     */
    private SerialInterfaceHandler() {
        controlData = ControlData.getInstance();        
        controlData.addObserver(this);
    }
    
    /**
     * Singleton-Pattern.
     *
     * @return The Instance.
     */
    public static SerialInterfaceHandler getInstance() {
        if (theInstance == null) {
            theInstance = new SerialInterfaceHandler();        
        }
        return theInstance;
    }
    
    /**
     * Baut die Verbindung für die serielle Schnittstelle auf.
     */
    public void setup(){
        try {
            connect(PORT_NAME);
            System.out.println("SerialInterfaceHandler erstellt!");
        } catch (Exception e) {
            System.out.println("Fehler beim oeffnen des Serial Port: " + e.toString());
        }
    }

    /**
     * Connect to the Serial Port.
     *
     * @param portName The Serial Port.
     * @throws Exception
     */
    private void connect(String portName) throws Exception {
        System.out.println("Serielle Verbindung aufbauen...");
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if (portIdentifier.isCurrentlyOwned()) {
            throw new Exception("Port ist in Verwendung.");
        } else {
            CommPort commPort = portIdentifier.open(this.getClass().getName(), SERIAL_TIMEOUT);

            if (commPort instanceof SerialPort) {
                // Serial settings: 9600/8-N-1
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(
                        BAUD_RATE,
                        SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE);

                serialInputStream = serialPort.getInputStream();
                serialOutputStream = serialPort.getOutputStream();
            } else {
                throw new Exception("Falscher Port! Nur Serial Ports!");
            }
        }
    }

    /**
     * Write to Serial Port.
     */
    public void writeToSerial() {
        byte[] data = controlData.getSignal();
        
        try {
            for (int i = 0; i < data.length; i++) {
                serialOutputStream.write(data[i]);
            }
        } catch (Exception e) {
            System.out.println("Error while writing to serial device: " + e.toString());
        }
    }

    /**
     * Read the input from Serial Port.
     * 
     * @param valuesToRead Number of Integer-Values to read from Serial Interface.
     * @return The Integer Array of the Values read from Serial Port.
     */
    public byte[] readFromSerial(int valuesToRead) {
        byte[] fromSerial = new byte[valuesToRead];
        try {
            for (int i = 0; i < fromSerial.length; i++) {
                fromSerial[i] = (byte)serialInputStream.read();
            }
        } catch (Exception e) {
            System.out.println("Error while reading from serial device: " + e.toString());
        }
        return fromSerial;
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if(o == controlData){
            writeToSerial();
        }
    }

}
