/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 *
 * @author Sergio
 */
public class P2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        InputStream in;
        PrintWriter err;
        PrintWriter out;
        
        Process process = Runtime.getRuntime().exec("recursos/ws3270.exe 155.210.152.51 -port 101");
        in = process.getInputStream();
        //err = new PrintWriter(new OutputStreamWriter(process.getErrorStream()));
        out = new PrintWriter(new OutputStreamWriter(process.getOutputStream()));
        out.print("ascii");
        System.out.println(in.toString());
        out.print("disconnect");
        out.print("quit");
        process.destroy();
        
                
    }
    
}
