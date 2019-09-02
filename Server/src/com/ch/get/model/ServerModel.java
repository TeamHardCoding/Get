package com.ch.get.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
				
				textArea.appendText("Ŭ���̾�Ʈ ���� �����...\n");
				Socket socket = serverSocket.accept();
				
				BufferedReader br = new BufferedReader(
						new InputStreamReader(
								socket.getInputStream()));
				
				BufferedWriter bw = new BufferedWriter(
						new OutputStreamWriter(
								socket.getOutputStream()));
				
				String msg = br.readLine();
				textArea.appendText(msg+"\n");
				
				msg = "Server ����";
				bw.write(msg);
				bw.newLine();
				bw.flush();
				
				socket.close();
			}
		} catch (Exception e) {
			
		}
	}
	
	public void setTextArea(TextArea textArea) {
		this.textArea = textArea;
	}
}
