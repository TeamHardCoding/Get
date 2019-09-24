/*
 * Author ch.Get
 */
package ch.get.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

import ch.get.view.RootLayoutController;

public class Server extends Thread {
	
	private Socket socket;
	private List<PrintWriter> listWriters;
	private String request;
	private String name;
	
	public Server(Socket socket, List<PrintWriter> temp) {
		this.socket = socket;
		listWriters = temp;
	}
	
	@Override
	public void run() {
		request = null;
		
		try {
			BufferedReader br = 
					new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			
			while(true) {
				request = br.readLine();
				
				if(request == null) {
					printText("Ŭ���̾�Ʈ ���� ����.");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		} catch (Exception e) {
			
		}
	}
	
	private void procQuit(PrintWriter pw) {
		removeWriter(pw); //��Ʈ�� ����
		
		String data = this.name+"���� �����߽��ϴ�.";
		broadCaster(data);
	}
	
	private void broadCaster(String temp) {
		synchronized (listWriters) {
			for (PrintWriter printWriter : listWriters) {
				printWriter.print(temp);
				printWriter.flush();
			}
		}
	}
	
	private void removeWriter(PrintWriter pw) {
		synchronized (listWriters) {
			listWriters.remove(pw);
		}
	}
	
	private void printText(String msg) {
		RootLayoutController.rcl.printText(msg);
	}
}
