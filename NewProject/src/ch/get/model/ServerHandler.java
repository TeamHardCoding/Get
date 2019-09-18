package ch.get.model;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

import javafx.scene.control.TextArea;

public class ServerHandler extends Thread{
		
	private ServerHandler() { this.PORT = 8000; }
	private static class Singleton {
			private static final ServerHandler inst = 
					new ServerHandler(); 
	}
	
	private final int PORT;
	private String serverIp;
	private TextArea textArea;
	private HashMap<String, Server> userThreadLists;
	private ServerSocket serverSocket = null;
	
	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(); //서버소켓 생성
			serverIp = InetAddress.getLocalHost().getHostAddress();
			serverSocket.bind(new InetSocketAddress(serverIp, PORT));
			printText("클라이언트 응답 대기중...");
			
			while(true) {
				Socket socket = serverSocket.accept();
				Server server = new Server(socket);
				userThreadLists.put(server.getClientID(), server); //소켓 생성
			}
		} catch (UnknownHostException e) {
		} catch (Exception e) {
			System.out.println("dd");
		} finally {
			if(serverSocket != null && !serverSocket.isClosed()) {
				try {
					serverSocket.close();
					printText("서버 종료");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**************************************************/
	//ETC
	public void printText(String msg) {
		textArea.appendText(msg+"\n");
	}
	/**************************************************/
	
	//getter
	public HashMap<String, Server> getUserThreadLists() {
		return userThreadLists;
	}
	public static ServerHandler getInst() {
		return Singleton.inst;
	}
	public TextArea getTextArea() {
		return textArea;
	}
	
	//setter
	public void setTextArea(TextArea textArea) {
		this.textArea = textArea;
	}
}
