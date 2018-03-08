/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopapp;

import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author luca_
 */

//TODO
//Create Socket
//Connect to Server
//Collect and show Data

public class ClientFrame extends JFrame{
    private final JPanel contentPane;

    public ClientFrame() throws IOException{
        
        ClientSocket cs = new ClientSocket();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1280, 720);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5,5,5,5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        
        
     
}    
   

}
