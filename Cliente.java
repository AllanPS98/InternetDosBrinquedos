/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author allan
 */
public class Cliente {
    
    public static void main(String[] args) throws IOException {
        Socket cliente = new Socket("127.0.0.1", 12345);
        ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(cliente.getInputStream());
        ThreadClient thread = new ThreadClient(cliente,out, in);
        Thread t = new Thread(thread);
        t.start();
    }
}
