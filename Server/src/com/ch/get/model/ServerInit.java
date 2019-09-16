package com.ch.get.model;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

import javafx.scene.control.TextArea;

public class ServerInit extends Thread{
	
	private Object lock = new Object();
	
	private HashMap<String, List<Object>> user;
	private final int PORT;
	private ServerSocket serverSocket;
	
	private String server_Name;
	private TextArea textArea;
	
	//������
	public ServerInit(String server_Name, int port, TextArea textArea) {
		this.server_Name = server_Name;
		this.PORT = port;
		this.textArea = textArea;
	}
	
	public ServerInit(TextArea textArea) {
		this("Default_Server", 8000, textArea);
	}

	//server ����
	public void closeServer() {
		try {
			synchronized (lock) {
				serverSocket.close();
				super.interrupt();
			}
		} catch (Exception e) {
			
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		startServer();
	}
	
	//��Ƽ ������ ���� ����
	public void startServer() {
		try {
			serverSocket = new ServerSocket(PORT);
			user = new HashMap<String, List<Object>>(); //User ���
			textArea.appendText("���� ������...\n");
			Thread.sleep(500);
			textArea.appendText("���� ���� �Ϸ�\n");
			
			while(true) {
				Socket socket = serverSocket.accept();
				ServerModel serverModel = 
						new ServerModel(textArea, socket, user, this.lock);
//				serverModel.setDaemon(true);
				serverModel.start(); // Ŭ�󸶴� ���� ������ �Ҵ�
			}
		} catch (Exception e) {
			textArea.appendText("���� ����\n");
		}
	}
	
	public String getServer_Name() {
		return server_Name;
	}
	public int getPORT() {
		return PORT;
	}
}
