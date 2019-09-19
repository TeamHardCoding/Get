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
					userThreadLists = new HashMap<String, Server>();
					userThreadLists.clear();
					
					serverSocket = new ServerSocket(); //서버소켓 생성
					serverIp = InetAddress.getLocalHost().getHostAddress();
					serverSocket.bind(new InetSocketAddress(serverIp, PORT));
					RootLayoutController.rcl.printText("서버 접속 대기중...");
					
					while(true) {
						Socket socket = serverSocket.accept();
						Server server = new Server(socket, lock);
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
		};
		
		serverInstance.start();
	}
	
	/**************************************************/
	public void stopServerSocket() {
		synchronized (lock) {
			try {
					if(userThreadLists.size() > 0) { // Server 인스턴스 마다 접근해서 먼저 스트림을 닫아줌
						for(String key : userThreadLists.keySet()) {
							Server server = userThreadLists.get(key);
							server.closeStream();
							RootLayoutController.rcl.printText("소켓 종료 중...");
						}
					} else {
						RootLayoutController.rcl.printText("종료할 클라이언트가 없습니다. 서버 소켓만 종료 합니다.");
					}
				serverSocket.close(); // 동기화 블럭내에서 처리후 다되면 서버 소켓 닫음
				serverInstance.interrupt(); //서버 인터럽트 핸들러 자체는 싱글톤으로 살려둠
				serverInstance = null; //쓰레드가 터미네이터 되었기 때문에 null 값을 넣어줌
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
