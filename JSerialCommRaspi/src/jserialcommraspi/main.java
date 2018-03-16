/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jserialcommraspi;

/**
 *
 * @author luca_
 */
public class main {

    public static void main(String[] args) {
        JSerialCommRaspi comm = new JSerialCommRaspi();
        char code = (char) 'B';

        comm.write(code);

    }

}
