package ch.get.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import ch.get.view.RootLayoutController;

public class ServerHandler extends Thread{
		
	public ServerHandler() { this.PORT = 8000; }
	
	private final int PORT;
	private String serverIp;
	private List<PrintWriter> clientLists = new ArrayList<PrintWriter>();
	private List<String> clientIpLists = new ArrayList<String>();
	private ServerSocket serverSocket = null;
	
	public Object lock = new Object();
	
	@Override
	public void run() {	
		try {			
			serverSocket = new ServerSocket(); //�������� ����
			serverIp = InetAddress.getLocalHost().getHostAddress(); //���� IP�� �޾ƿ�
			serverSocket.bind(new InetSocketAddress(serverIp, PORT));
			RootLayoutController.rcl.printText("���� ���� �����...");
			
			while(true) {
				Socket socket = serverSocket.accept();
				new Server(socket, clientLists, clientIpLists).start();
			}
		} catch (IOException e) {
		} finally {
			if(serverSocket != null && !serverSocket.isClosed()) {
				try {
					serverSocket.close();
					RootLayoutController.rcl.printText("���� ����");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**************************************************/
	public void stopServerSocket() {
		
	}
	/**************************************************/
	
	//getter
	public List<String> getClientIpLists() {
		return clientIpLists;
	}
	public List<PrintWriter> getClientLists() {
		return clientLists;
	}
}
