/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopapp;

import java.awt.BorderLayout;
import static java.awt.Color.GRAY;
import java.awt.GridLayout;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
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

        //ClientSocket socket = new ClientSocket();
       // ClientFrame frame = new ClientFrame();
        UIDesignTest frame2 = new UIDesignTest();
        frame2.setVisible(true);
    }

    JFrame mainframe;
            
    public ClientFrame() throws IOException {

        mainframe = new JFrame();
        
        JList debuglist = new JList();
        JButton startbutton = new JButton();
        
        
     /*   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLayout(new BorderLayout(5, 5));

        JLabel label = new JLabel("Hello World");

        frame.getContentPane().add(panel);
        panel.setBackground(GRAY);
        panel.add(label);
        GridLayout experimentLayout = new GridLayout(0, 3);
        

        panel.setLayout(experimentLayout);

        panel.add(new JButton("Button 1"));
        panel.add(new JButton("Button 2"));
        panel.add(new JButton("Button 3"));
        panel.add(new JButton("Long-Named Button 4"));
        panel.add(new JButton("5"));
        frame.setSize(1280, 720);
        frame.setVisible(true);3/
    }

}
