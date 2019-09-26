package ch.get.model;

/*
 * protocol reference - Protocol
 * 
 */

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
	
	private Server serverInst = null;
	private Object lock = new Object();
	
	@Override
	public void run() {	
		try {			
			serverSocket = new ServerSocket(); //서버소켓 생성
			serverIp = InetAddress.getLocalHost().getHostAddress(); //서버 IP를 받아옴
			serverSocket.bind(new InetSocketAddress(serverIp, PORT));
			RootLayoutController.rcl.printText("서버 접속 대기중...");
			
			while(true) {
				Socket socket = serverSocket.accept();
				serverInst = new Server(socket, clientLists, clientIpLists);
				serverInst.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			interrupt();
		}
	}
	
	/**************************************************/
	public boolean stopServer() { //컨트롤러 핸들링
		boolean op = false;
		
		try {
			if(serverInst != null) {
				serverInst.procQuitFromServer();
				op = true;
			} else {
				closeServerSocket(getServerSocket());
				op = false;
			}		
		} catch (Exception e) {
			e.printStackTrace();
			op = false;
		} 
		
		return op;
	}
	
	private void closeServerSocket(ServerSocket serverSocket) { //서버소켓 close
		try {
			if((serverSocket != null) || (!serverSocket.isClosed())) {
				
				serverSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//getter
	public ServerSocket getServerSocket() {
		return serverSocket;
	}
	public List<String> getClientIpLists() {
		return clientIpLists;
	}
	public List<PrintWriter> getClientLists() {
		return clientLists;
	}
}
