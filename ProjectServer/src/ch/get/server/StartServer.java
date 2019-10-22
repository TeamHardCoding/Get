package ch.get.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.HashMap;

public class StartServer {

	private String serverName = "MyServer";
	private HashMap<String, PrintWriter> users;
	
	private String serverIP;
	private int serverPORT = 8000;
	
	private ServerSocket serverSocket;

	public StartServer() {
		
		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress(serverIP, serverPORT));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public StartServer(String serverName) {
		this();
		this.serverName = serverName;
	}

	// Server init
	public void initServer() {
		
	}
	
	public String getServerIP() {
		String ip = null;
		
		try {
			ip = InetAddress.getLocalHost().toString();
		} catch (NumberFormatException | UnknownHostException e) {
			e.printStackTrace();
		}
		
		return ip;
	}
}

class Server extends Thread {

	public Server() {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}