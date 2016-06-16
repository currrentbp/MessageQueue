package com.bp.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * 
 * @author current_bp
 * @time 20160427
 *
 */
public class Server {

	public static void mainFun(){
		System.out.println("=======start server.....");
		try {
			ServerSocket serverSocket = new ServerSocket(9999);
			Socket socket = null;
			int count = 0;
			while(true){
				socket = serverSocket.accept();
				ServerThread serverThread = new ServerThread(socket);
				serverThread.setPriority(4);
				serverThread.start();
				
				count++;
				InetAddress address = socket.getInetAddress();
			}
			
			
			
//			serverSocket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
		}
	}
	
	public static void main(String[] args) {
		Server s = new Server();
		s.mainFun();
	}
}
