package com.bp.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import com.bp.messageQueue.HandleCode;



/**
 * 
 * @author current_bp
 * @time 20160427
 *
 */
public class ServerThread extends Thread {

	Socket socket = null;

	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		OutputStream os = null;
		PrintWriter pw = null;
		StringBuffer sb = new StringBuffer();
		String result = "";

		try {
			is = socket.getInputStream();
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);

			String info = null;

			while ((info = br.readLine()) != null) {
				sb.append(info+"\n");
				System.out.println("sb:"+sb);
				result = getResult(sb.toString());

				os = socket.getOutputStream();
				pw = new PrintWriter(os);
				pw.write(""+result);
			}
			socket.shutdownInput();
			

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != pw)
					pw.close();
				if (null != os)
					os.close();
				if (null != br)
					br.close();
				if (null != isr)
					isr.close();
				if (null != is)
					is.close();
				if (null != socket)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 通过反射机制获得结果
	 * @param info:类名称：方法名称：参数
	 * @return
	 */
	private String getResult(String info) {
		HandleCode hc = new HandleCode();
		return hc.invokeMethod(info);
	}
}
