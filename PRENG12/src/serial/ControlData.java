package serial;

import java.nio.ByteBuffer;
import java.util.Observable;

/**
 * @author luca_
 */
public class ControlData extends Observable {

    private static ControlData theInstance;

    private final int defaultDistance = 0x00;

    private boolean systemBereit;
    private boolean startSignal;
    private int distanzFahren;
    private boolean lastAufnahme;
    private boolean fahren;
    private boolean zielPlatformErkannt;
    private boolean fahrenBisSchluss;

    public ControlData() {
        systemBereit = false;
        startSignal = false;
        distanzFahren = 123;
        lastAufnahme = false;
        fahren = false;
        zielPlatformErkannt = false;
        fahrenBisSchluss = false;
    }

    public static ControlData getInstance() {
        if (theInstance == null) {
            theInstance = new ControlData();
        }
        return theInstance;
    }
    
    /**
     * Löst den Greifmechanismus aus.
     */
    public void greifen() {
        greifen = true;
        
        setChanged();
        notifyObservers();
        
        greifen = false;
        
        setChanged();
        notifyObservers();
    }

    /**
     * Löst den Entsorgungsmechanismus aus.
     */
    public void entsorgen() {
        entsorgen = true;
        
        setChanged();
        notifyObservers();
        
        entsorgen = false;
        
        setChanged();
        notifyObservers();
    }
    
    /**
     * Setzt das Entsorgen Flag zurück
     */
    public void resetEntsorgen() {
        entsorgen = false;
        
        setChanged();
        notifyObservers();
    }

    /**
     * Setzt die Motorgeschwindigkeit rechts
     *
     * @param speed in logischen Schritten von 0-128
     */
    public void setMotorSpeedRight(int speed) {
        motorSpeedRight = speed;
        
        setChanged();
        notifyObservers();
    }

    /**
     * Setzt die Motorgeschwindigkeit links
     *
     * @param speed in logischen Schritten von 0-128
     */
    public void setMotorSpeedLeft(int speed) {
        motorSpeedLeft = speed;
        
        setChanged();
        notifyObservers();
    }

    /**
     * Generiert die byte-Repräsentation aufgrund des aktuellen Zustand dieses
     * Objektes.
     *
     * @return Byte Array des aktuellen Zustandes.
     */
    public byte[] getSignal() {
        byte arbeit = getArbeitByte();
        byte motorRechts = getMotorRechtsByte();
        byte motorLinks = getMotorLinksByte();

        return new byte[]{motorLinks, motorRechts, arbeit};
    }

    private byte getArbeitByte() {
        int greifenCode = 0x01;
        int entsorgenCode = 0x02;

        int arbeit = 0x00;
        if (greifen) {
            arbeit = arbeit | greifenCode;
        }
        if (entsorgen) {
            arbeit = arbeit | entsorgenCode;
        }

        byte[] bytes = ByteBuffer.allocate(4).putInt(arbeit).array();
        return bytes[3];
    }

    private byte getMotorRechtsByte() {
        byte[] bytes = ByteBuffer.allocate(4).putInt(motorSpeedRight).array();
        byte signedByte = bytes[3];
        byte unsignedByte = (byte) (signedByte & 0xFF);
        return unsignedByte;
    }

    private byte getMotorLinksByte() {
        byte[] bytes = ByteBuffer.allocate(4).putInt(motorSpeedLeft).array();
        byte signedByte = bytes[3];
        byte unsignedByte = (byte) (signedByte & 0xFF);
        return unsignedByte;
    }
}
