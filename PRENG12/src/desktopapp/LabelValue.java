/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopapp;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author luca_
 */
public class LabelValue implements ObservableValue<String> {

    String value;

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
        this.value = value;
    }
    @Override
    public void addListener(ChangeListener<? super String> listener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeListener(ChangeListener<? super String> listener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void addListener(InvalidationListener listener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
