/*
 * Author ch.Get
 */
package ch.get.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import ch.get.view.RootLayoutController;

public class Server extends Thread {
	
	private Socket socket;
	private String clientID;
	private Object lock;
	
	//request stream
	private BufferedReader br;
	private PrintWriter pw;
	private List<Object> inStreamLists;
	
	public Server(Socket socket, Object lock) {
		this.socket = socket;
		this.lock = lock;
		inStreamLists = new ArrayList<Object>();
		
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
			
			addStream(br);
			addStream(pw);
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
						RootLayoutController.rcl.printText(request+" 11");
					}
				}
			} catch (IOException e) {
			} catch (Exception e) {
				RootLayoutController.rcl.printText("클라이언트 접속 끊음.");
			}
		}
	}
	
	/**************************************************/
	private void addStream(Object temp) {
		
		synchronized (inStreamLists) {
			inStreamLists.add(temp);
		}
	}
	
	private void removeStream(Object temp) {
		
		synchronized (inStreamLists) {
			inStreamLists.remove(temp);
		}
	}
	
	public void closeStream() {
		try {
			pw.print("Exit");
			pw.flush();
			removeStream(br);
			removeStream(pw);
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
