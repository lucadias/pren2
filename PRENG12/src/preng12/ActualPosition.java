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
public final class ActualPosition {

    private static ActualPosition instance = null;

    private static Object mutex = new Object();

    public static int y;
    public static int x;
    
    public static boolean toSend;

    public ActualPosition() {
        y = 0;
        x = 0;
        toSend = false;
    }

    public static ActualPosition getInstance() {
        ActualPosition result = instance;
        if (result == null) {
                result = instance;
                if (result == null) {
                    instance = result = new ActualPosition();
                
            }
        }
        return result;
    }

    
    public void updateX(int x) {
        this.x = x;
    }

    public void updateY(int y) {
        this.y = y;
    }

    public static int getX() {
        return x;
    }

    public static int getY() {
        return y;
    }
    
    public static boolean getToSend(){
        return toSend;
    }
    
    public void updateToSend(boolean toSend){
        this.toSend = toSend;
    }
}
