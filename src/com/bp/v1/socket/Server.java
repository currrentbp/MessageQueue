package com.bp.v1.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * @author current_bp
 * @time 20160427
 */
public class Server {

    private static final int NTHREADS = 100;
    private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);

    public static void mainFun() {
        System.out.println("=======start server.....");
        try {
            ServerSocket serverSocket = new ServerSocket(9999);

            final int count = 0;
            while (true) {
                final Socket socket = serverSocket.accept();
                Runnable task = new Runnable() {
                    @Override
                    public void run() {
                        handleRequest(socket);
                    }
                };
                exec.execute(task);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    private static void handleRequest(Socket socket) {
        try {
            String message = null;
            //读入消息
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            message = bufferedReader.readLine();
            System.out.println("message:" + message);

            //写出消息
            OutputStream outputStream = socket.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            bufferedWriter.write("" + message+ " thread:"+Thread.currentThread().getId()+"\n");

            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("====finally====");
                socket.shutdownOutput();
//                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Server s = new Server();
        s.mainFun();
    }
}
