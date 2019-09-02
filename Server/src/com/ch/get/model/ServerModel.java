package com.ch.get.model;

import java.net.ServerSocket;
import java.net.Socket;

import javafx.scene.control.TextArea;

public class ServerModel implements Runnable{
	
	private TextArea textArea;
	private ServerSocket serverSocket;
	
	public ServerModel() {}
	
	public ServerModel(TextArea textArea) {
		this.textArea = textArea;
	}

	@Override
	public void run() {
		
		try {
			serverSocket = new ServerSocket(8000);
			textArea.appendText("���� ������...\n");
			while(true) {
				
				for(int i=3; i>=1; i--) {
					textArea.appendText(i+"\n");
					Thread.sleep(1000);
				}
				
				textArea.appendText("Ŭ���̾�Ʈ ���� �����...\n");
				Socket socket = serverSocket.accept();
			}
		} catch (Exception e) {
			
		}
	}
	
	public void setTextArea(TextArea textArea) {
		this.textArea = textArea;
	}
}
