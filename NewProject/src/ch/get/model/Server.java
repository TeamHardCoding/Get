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
			addStream(br);
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
					
					if(request.equalsIgnoreCase("quit")) {
						RootLayoutController.rcl.printText(clientIP+" Ŭ���̾�Ʈ ���� ����");
					} else {
						RootLayoutController.rcl.printText(request+"");
					}
				}
			} catch (IOException e) {
			} catch (Exception e) {
			}
		}
	}
	
	/**************************************************/
	private void addStream(Object temp) {
		
		synchronized (inStreamLists) {
			inStreamLists.add(temp);
		}
	}
	
	public void closeStream() {
		PrintWriter pw;
		
		try {
			pw = new PrintWriter(
					new BufferedWriter(
							new OutputStreamWriter(
									socket.getOutputStream(),
									StandardCharsets.UTF_8)), 
									true);
			
			pw.write("quit"); //������ �ݱ��� Ŭ���̾�Ʈ�� ���� �޽��� ����
			
			inStreamLists.clear(); //��Ʈ���� ����
			socket.close(); //������ ����
			this.interrupt(); //���� ���� ���ͷ�Ʈ ��
		} catch (Exception e) {
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
