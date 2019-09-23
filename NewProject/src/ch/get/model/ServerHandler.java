package ch.get.model;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

import ch.get.view.RootLayoutController;

public class ServerHandler extends Thread{
		
	public ServerHandler() { this.PORT = 8000; }
	
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
			
			serverSocket = new ServerSocket(); //서버소켓 생성
			serverIp = InetAddress.getLocalHost().getHostAddress();
			serverSocket.bind(new InetSocketAddress(serverIp, PORT));
			RootLayoutController.rcl.printText("서버 접속 대기중...");
			
			while(true) {
				Socket socket = serverSocket.accept();
				Server server = new Server(socket, lock);
				server.start();
				userThreadLists.put(server.getClientID(), server); //소켓 생성
			}
		} catch (UnknownHostException e) {
		} catch (Exception e) {
		} finally {
			if(serverSocket != null && !serverSocket.isClosed()) {
				try {
					serverSocket.close();
					RootLayoutController.rcl.printText("소켓 없음 서버 종료");
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
						RootLayoutController.rcl.printText("소켓 종료 중...");
					}
				}
				
				RootLayoutController.rcl.printText("서버 종료");
			} catch (Exception e) {
			} finally {
				try {
					if(serverSocket != null) { serverSocket.close(); }
				} catch (IOException e) {
				}
				
			}
		}
	}
	/**************************************************/
	
	//getter
	public HashMap<String, Server> getUserThreadLists() {
		return userThreadLists;
	}
}
