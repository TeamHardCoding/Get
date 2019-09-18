package ch.get.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server extends Thread {
	
	private Socket socket;
	private String clientID;
	
	//request stream
	private BufferedReader br;
	private PrintWriter pw;
	
	public Server(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			br = new BufferedReader(
						new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			
			pw = new PrintWriter(
						new BufferedWriter(
								new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8)));
			
			while(true) {
				String request = br.readLine();
				String clientIP = socket.getRemoteSocketAddress().toString();
				
				if(request == null) {
					ServerHandler.getInst().printText(clientIP+" Å¬¶óÀÌ¾ðÆ® Á¢¼Ó ²÷±è");
					break;
				} 
			}
		} catch (IOException e) {
		} catch (Exception e) {
		}
	}
	
	public String getClientID() {
		return clientID;
	}
}
