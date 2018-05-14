/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopapp;

import javafx.beans.property.StringProperty;


/**
 *
 * @author luca_
 */
public class LabelValue{

    StringProperty value;

    public LabelValue() {
//asdf
    }
    private static LabelValue psinstance = null;
    private static final Object INSTANCE_LOCK = new Object();

    public static LabelValue getInstance() {
        synchronized (INSTANCE_LOCK) {
            if (psinstance == null) {
                psinstance = new LabelValue();
            }
        }
        return psinstance;
    }
    

    public void updatevalue(String value){
        this.value.set(value);
    }
    
    public StringProperty getvalue(){
        return value;
    }
   
}
