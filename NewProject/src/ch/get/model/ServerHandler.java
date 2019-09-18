package ch.get.model;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Set;

import ch.get.view.RootLayoutController;
import javafx.scene.control.TextArea;

public class ServerHandler extends Thread{
		
	private ServerHandler() { this.PORT = 8000; }
	private static class Singleton {
			private static final ServerHandler inst = 
					new ServerHandler(); 
	}
	
	private final int PORT;
	private String serverIp;
	private HashMap<String, Server> userThreadLists;
	private ServerSocket serverSocket = null;
	
	public Object lock = new Object();
	
	@Override
	public void run() {
		try {
			userThreadLists = new HashMap<String, Server>();
			userThreadLists.clear();
			
			serverSocket = new ServerSocket(); //�������� ����
			serverIp = InetAddress.getLocalHost().getHostAddress();
			serverSocket.bind(new InetSocketAddress(serverIp, PORT));
			RootLayoutController.rcl.printText("���� ���� �����...");
			
			while(true) {
				Socket socket = serverSocket.accept();
				Server server = new Server(socket, lock);
				userThreadLists.put(server.getClientID(), server); //���� ����
			}
		} catch (UnknownHostException e) {
		} catch (Exception e) {
		} finally {
			if(serverSocket != null && !serverSocket.isClosed()) {
				try {
					serverSocket.close();
					RootLayoutController.rcl.printText("���� ���� ���� ����");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**************************************************/
	public void stopServerSocket() {
		synchronized (lock) {
			try {
					if(userThreadLists.size() > 0) {
						for(String key : userThreadLists.keySet()) {
							Server server = userThreadLists.get(key);
							server.closeStream();
							RootLayoutController.rcl.printText("���� ���� ��...");
					}
				}
				serverSocket.close();
				super.interrupt();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**************************************************/
	
	//getter
	public HashMap<String, Server> getUserThreadLists() {
		return userThreadLists;
	}
	public static ServerHandler getInst() {
		return Singleton.inst;
	}
}
