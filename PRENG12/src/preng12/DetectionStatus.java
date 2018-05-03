/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preng12;

/**
 *
 * @author luca_
 */
public final class DetectionStatus {

    private static DetectionStatus instance = null;

    private static Object mutex = new Object();

    public static boolean recognized;

    public DetectionStatus() {
        recognized = false;
    }

    public static DetectionStatus getInstance() {
        DetectionStatus result = instance;
        if (result == null) {
            result = instance;
            if (result == null) {
                instance = result = new DetectionStatus();

            }
        }
        return result;
    }

    public void updateR(boolean r) {
        this.recognized = r;
    }

  
    public static boolean getR() {
        return recognized;
    }

}
