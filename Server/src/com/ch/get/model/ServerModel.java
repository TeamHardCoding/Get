package com.ch.get.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;

import javafx.scene.control.TextArea;

public class ServerModel implements Runnable{
	
	private TextArea textArea;
	private ServerSocket serverSocket;
	private LocalTime time;
	private BufferedReader br;
	private BufferedWriter bw;
	private Object lock = new Object();
	private boolean ready = true;
	
	//public ServerModel() {}
	
	public ServerModel(TextArea textArea) {
		this.textArea = textArea;
	}

	
	public void closeServer() {
		try {
			synchronized (lock) {
				serverSocket.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void run() {
		
		try {
			serverSocket = new ServerSocket(8000);
			textArea.appendText("서버 세팅중...\n");		
			Thread.sleep(500);
			textArea.appendText("클라이언트 접속 대기중...\n");
			
			while(ready) {
				Socket socket = serverSocket.accept();
				
				synchronized (lock) {
					br = new BufferedReader(
							new InputStreamReader(
									socket.getInputStream()));
					
					bw = new BufferedWriter(
							new OutputStreamWriter(
									socket.getOutputStream()));
					
					String msg = br.readLine();
					InetAddress name = socket.getInetAddress();
					textArea.appendText(name.getHostAddress()+" : "+name.getHostName()+"\n");
					
					time = LocalTime.now();
					msg = "Hi "+name.getHostName()+" "+time.toString();
					
					bw.write(msg);
					bw.newLine();
					bw.flush();
	//				
					socket.close();
				}
			}
		} catch (Exception e) {
			textArea.appendText("서버 종료\n");
		}
	}
	
	public void setTextArea(TextArea textArea) {
		this.textArea = textArea;
	}
}
