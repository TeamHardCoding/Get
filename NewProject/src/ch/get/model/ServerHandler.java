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
			serverSocket = new ServerSocket(); //서버소켓 생성
			serverIp = InetAddress.getLocalHost().getHostAddress(); //서버 IP를 받아옴
			serverSocket.bind(new InetSocketAddress(serverIp, PORT));
			RootLayoutController.rcl.printText("서버 접속 대기중...");
			
			while(true) {
				Socket socket = serverSocket.accept();
				new Server(socket, clientLists, clientIpLists).start();
			}
		} catch (IOException e) {
		} finally {
			if(serverSocket != null && !serverSocket.isClosed()) {
				try {
					serverSocket.close();
					RootLayoutController.rcl.printText("서버 종료");
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
