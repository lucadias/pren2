/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopapp;

import java.awt.BorderLayout;
import static java.awt.Color.GRAY;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author luca_
 */
//TODO
//Create Socket
//Connect to Server
//Collect and show Data
public class ClientFrame extends JFrame {


    public static void main(String[] args) throws IOException {

        ClientFrame frame = new ClientFrame();
    }

    public ClientFrame() throws IOException {

        JFrame frame = new JFrame("HelloWorldSwing");
        JPanel panel = new JPanel();
        
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLayout(new BorderLayout(5,5));
        
        
        
        JLabel label = new JLabel("Hello World");
        
        frame.getContentPane().add(panel);
        panel.setBackground(GRAY);
        panel.add(label);

        frame.setSize(1280,720);
        frame.setVisible(true);
    }

    
}
