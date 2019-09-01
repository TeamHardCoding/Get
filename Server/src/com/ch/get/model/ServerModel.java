package com.ch.get.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

import javafx.scene.control.TextArea;

public class ServerModel {

	private final UUID SERVER_ID;
	
	private TextArea textArea;
	private ServerSocket serverSocket;
	private BufferedReader br;
	private Thread sevThread;
	
	public ServerModel() {
		// TODO Auto-generated constructor stub
		SERVER_ID = UUID.randomUUID(); //접속 구분
	}
	
	public ServerModel(TextArea textArea) {
		this();
		this.textArea = textArea;
		initServer();	
		
		sevThread.setDaemon(true);
		sevThread.start();
	}
	
	public void initServer() {
		textArea.appendText("서버 세팅중...");
		
		try {
			serverSocket = new ServerSocket(8000);
			
			sevThread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					while(true) {
						
						try {
							textArea.appendText("클라이언트 대기중...");
							Socket socket = serverSocket.accept();//소켓 대기
							br = new BufferedReader(new InputStreamReader(socket.getInputStream())); //데이터 수신 대기
					
							textArea.appendText("<< "+br.readLine());
							socket.close();
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				}
			});

		} catch (Exception e) {
		
		}
	}
	
	public void setTextArea(TextArea textArea) {
		this.textArea = textArea;
	}
}
