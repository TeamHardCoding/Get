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
	
	@Override
	public void run() {	
		try {			
			serverSocket = new ServerSocket(); //�������� ����
			serverIp = InetAddress.getLocalHost().getHostAddress(); //���� IP�� �޾ƿ�
			serverSocket.bind(new InetSocketAddress(serverIp, PORT));
			RootLayoutController.rcl.printText("���� ���� �����...");
			
			while(true) {
				Socket socket = serverSocket.accept();
				serverInst = new Server(socket, clientLists, clientIpLists);
				serverInst.start();
			}
		} catch (Exception e) {
		} finally {
			closeServerSocket(getServerSocket());
		}
	}
	
	/**************************************************/
	public boolean stopServer() { //��Ʈ�ѷ� �ڵ鸵
		try {
			if(serverInst != null) {
				serverInst.procQuitFromServer();
				return true;
			} else {
				return false;
			}		
		} catch (Exception e) {
			return false;
		} finally {
			closeServerSocket(getServerSocket());
		}

	}
	
	private void closeServerSocket(ServerSocket serverSocket) { //�������� close
		
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
