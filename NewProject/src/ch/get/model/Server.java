/*
 * Author ch.Get
 */
package ch.get.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import ch.get.view.RootLayoutController;

public class Server extends Thread {
	
	private Socket socket;
	private String clientID;
	private Object lock;
	
	//request stream
	private BufferedReader br;
	private PrintWriter pw;
	
	public Server(Socket socket, Object lock) {
		this.socket = socket;
		this.lock = lock;
		
		try {
			br = new BufferedReader(
					new InputStreamReader(
							socket.getInputStream(), 
							StandardCharsets.UTF_8));
		
			pw = new PrintWriter(
						new BufferedWriter(
								new OutputStreamWriter(
										socket.getOutputStream(), 
										StandardCharsets.UTF_8)), true);
		} catch (IOException e) {
		} catch (Exception e) {
		}
	}
	
	@Override
	public void run() {
		
		synchronized (lock) {
			try {
				while(true) {
					String request = br.readLine();
					String clientIP = socket.getRemoteSocketAddress().toString();
					
					if(request == null) {
						RootLayoutController.rcl.printText(clientIP+" 클라이언트 접속 끊김");
						break;
					} else {
						RootLayoutController.rcl.printText(request);
						pw.append("-> 서버 에서 보낸 메시지 입니다. 성공적인 접속.");
					}
				}
			} catch (IOException e) {
			} catch (Exception e) {
			}
		}
	}
	
	/**************************************************/
	//ETC
	public void closeStream() {
		try {
			br.close();
			pw.flush();
			pw.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**************************************************/
	
	/*
	 * getter/setter
	 */
	public String getClientID() {
		return clientID;
	}
}
