/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package picameraprocessingpren;

import java.awt.EventQueue;

/**
 *
 * @author luca_
 */
public class PiCameraProcessingPREN {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
          EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    MyFrame frame = new MyFrame();
                    MyFrame frame2 = new MyFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
}
