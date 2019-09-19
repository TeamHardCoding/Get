package ch.get.model;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

import ch.get.view.RootLayoutController;

public class ServerHandler {
		
	private ServerHandler() { this.PORT = 8000; }
	private static class Singleton {
			private static final ServerHandler inst = 
					new ServerHandler(); 
	}
	
	private final int PORT;
	private String serverIp;
	private HashMap<String, Server> userThreadLists;
	private ServerSocket serverSocket = null;
	private Thread serverInstance = null;
	
	public Object lock = new Object();
	
	public void initServer() {
		serverInstance = new Thread() {		
			@Override
			public void run() {
				try {
					userThreadLists = new HashMap<String, Server>(); //Ŭ���̾�Ʈ ���� ��� ���� ����
					userThreadLists.clear(); //�⺻ �� ����
					
					serverSocket = new ServerSocket(); //�������� ����
					serverIp = InetAddress.getLocalHost().getHostAddress(); //���� �������� PC�� IP �޾ƿ�
					serverSocket.bind(new InetSocketAddress(serverIp, PORT)); //���� IP�� PORT ���ε�
					RootLayoutController.rcl.printText("���� ���� �����..."); //�޽��� ���
					
					while(true) {
						Socket socket = serverSocket.accept(); // Ŭ���̾�Ʈ�� ���� �Ҷ����� ��ٸ�
						Server server = new Server(socket, lock); // Ŭ���̾�Ʈ�� ���� �ϸ� ���� �� �����ؼ� �������κ� �����忡 �ѱ�
						userThreadLists.put(server.getClientID(), server); //���� ����
					}
				} catch (UnknownHostException e) {
				} catch (Exception e) {
				} finally {
					if(serverSocket != null && !serverSocket.isClosed()) { //�����ϳ� ������ ����ٸ� ���� ���� ����
						try {
							serverSocket.close(); // �������� ����
							RootLayoutController.rcl.printText("���� ���� ���� ����");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		};
		
		serverInstance.start();
	}
	
	/**************************************************/
	public void stopServerSocket() {
		synchronized (lock) {
			try {
					if(userThreadLists.size() > 0) { // Server �ν��Ͻ� ���� �����ؼ� ���� ��Ʈ���� �ݾ���
						for(String key : userThreadLists.keySet()) {
							Server server = userThreadLists.get(key);
							server.closeStream();
							RootLayoutController.rcl.printText("���� ���� ��...");
						}
					} else {
						RootLayoutController.rcl.printText("������ Ŭ���̾�Ʈ�� �����ϴ�. ���� ���ϸ� ���� �մϴ�.");
					}
				serverSocket.close(); // ����ȭ �������� ó���� �ٵǸ� ���� ���� ����
				serverInstance.interrupt(); //���� ���ͷ�Ʈ �ڵ鷯 ��ü�� �̱������� �����
				serverInstance = null; //�����尡 �͹̳����� �Ǿ��� ������ null ���� �־���
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**************************************************/
	
	//getter
	public Thread getServerInstance() {
		return serverInstance;
	}
	public HashMap<String, Server> getUserThreadLists() {
		return userThreadLists;
	}
	public static ServerHandler getInst() {
		return Singleton.inst;
	}
}
