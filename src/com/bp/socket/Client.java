package com.bp.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * @author current_bp
 * @createtime 20160427
 */
public class Client {

    private static Socket socket = null;
    private OutputStream os = null;
    private PrintWriter pw = null;
    private InputStream is = null;
    private static int count = 1;

    public static Socket getSocketInstance() throws UnknownHostException, IOException {
        if (null != socket) {
            return socket;
        } else {
            return socket = new Socket("localhost", 9999);
        }
    }

    public String useFunction(String clazzAndPath, Object[] args) {
        return null;
    }

    public String useFunction() {
        String result = null;
        try {
            Socket socket = getSocketInstance();

            os = socket.getOutputStream();
            pw = new PrintWriter(os);

            pw.write("CountNum:addCount:20160428," + getCount() + "\n");
            pw.flush();
//			socket.shutdownOutput();

            is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String info = null;

            while ((info = br.readLine()) != null) {
                System.out.println("result:" + info);
                result = info;
            }


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.shutdownOutput();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private synchronized static int getCount(){
        return count++;
    }

    public void closeResource() throws IOException {
        is.close();
        pw.close();
        os.close();
        socket.close();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 500; i++) {
            new Thread(new Runnable() {
                public void run() {
                    Client c = new Client();
                    c.useFunction();

                }
            }).start();


        }


    }
}
