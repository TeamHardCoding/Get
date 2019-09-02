package ch.get.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.scene.control.TextArea;

public class Client implements Runnable {
	
	private TextArea textArea;
	private String localHost;
	public Client() {  }
	
	public Client(TextArea textArea) {
		
		//스트림 생성
		this.textArea = textArea;
		
		try {
			localHost = Inet4Address.getLocalHost().toString();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
	
		try {
			Socket socket = new Socket("localhost", 8000);
			BufferedWriter bw = new BufferedWriter(
					new OutputStreamWriter(
							socket.getOutputStream()));
			
			BufferedReader br = new BufferedReader(
					new InputStreamReader(
							socket.getInputStream()));
			
			bw.write(localHost);
			bw.newLine();
			bw.flush();
			
			String msg = br.readLine();
			textArea.appendText(msg+"\n");
			
			socket.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			textArea.appendText("Client Interrupted\n");
		}
		
	}
}
