/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jserialcommraspi;

import com.fazecast.jSerialComm.SerialPort;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author luca_
 */
public class JSerialCommRaspi {

    /**
     * ttyACM0
     *
     * @param args the command line arguments
     */

    public JSerialCommRaspi() {

        // TODO code application logic here
        //SerialPort comPort = SerialPort.getCommPorts()[0];


        /*
        comPort.closePort();
        try {
            while (true) {
                while (comPort.bytesAvailable() == 0) {
                    Thread.sleep(20);
                }

                byte[] readBuffer = new byte[comPort.bytesAvailable()];

                int numRead = comPort.readBytes(readBuffer, readBuffer.length);
                System.out.println("Read " + numRead + " bytes.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
         */
    }

    public void write(char code) {
        String portDescriptor = "ttyACM0";
        SerialPort comPort = SerialPort.getCommPort(portDescriptor);
        comPort.openPort();
        OutputStream out = comPort.getOutputStream();
        try {
            out.write(code);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        comPort.closePort();
    }

    public char[] read() {
        String portDescriptor = "ttyACM0";
        SerialPort comPort = SerialPort.getCommPort(portDescriptor);
        comPort.openPort();
        char[] code = new char[40];

        InputStream in = comPort.getInputStream();
        try {
            for (int j = 0; j < 20; ++j) {
                System.out.print((char) in.read());
                code[j] = (char) in.read();

            }
            in.close();
            comPort.closePort();
            return code;
        } catch (Exception e) {
            e.printStackTrace();
        }
        comPort.closePort();
        return code;

    }
}
