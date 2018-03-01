/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopapp;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author luca_
 */
public class ClientSocket {

    Socket MyClient;

    public ClientSocket() {

        try {
            MyClient = new Socket("Machine name", 1337);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
