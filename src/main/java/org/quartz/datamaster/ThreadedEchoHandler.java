package org.quartz.datamaster;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by magneto on 17-3-13.
 */
public class ThreadedEchoHandler implements Runnable {
    private Socket incoming;
    public ThreadedEchoHandler(Socket incoming) {
        this.incoming = incoming;
    }

    @Override
    public void run() {

        try {
            InputStream inStream = incoming.getInputStream();
            OutputStream outStream = incoming.getOutputStream();

            try (Scanner in = new Scanner(inStream)) {
                PrintWriter out = new PrintWriter(outStream, true);

                out.println("Hello! Enter BYE to exit.");

                boolean done = false;

                while (!done && in.hasNextLine()) {
                    String line = in.nextLine();
                    System.out.println(line);
                    out.println(String.format("Echo: %s", line));
                    if (line.trim().equals("BYE")) done = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
