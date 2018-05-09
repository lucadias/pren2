package piserver;
import java.net.*;
import java.io.*;

public class PiServerProtocol {
  
    public String processInput(String theInput) {
        String theOutput = null;

        
        if("startButtonPressed".equals(theInput)){
            System.out.println("StartButton Press erkannt");
            return "StartButton Press erkannt";
        }
        
        return theOutput;
    }
}