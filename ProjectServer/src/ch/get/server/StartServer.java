package ch.get.server;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.HashMap;

import ch.get.model.ShowAlertType;
import javafx.scene.control.Alert.AlertType;

public class StartServer {

	private String serverName = "MyServer";
	private HashMap<String, PrintWriter> users;
	
	private String serverIP;
	private int serverPORT = 8000;
	
	private ServerSocket serverSocket;
c

	public StartServer() {
		serverSocket = new ServerSocket();
		serverSocket.bind(new InetSocketAddress(, serverPORT));
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