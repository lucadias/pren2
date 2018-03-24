/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopapp;

import javax.swing.JList;
import javax.swing.JPanel;

/**
 *
 * @author luca_
 */
public class DebugLogPanel extends JPanel {

    JList LogListe;
    
    public DebugLogPanel() {
    
    LogListe = new JList();
    
    this.add(LogListe);
    
    }
    
    
    
}
