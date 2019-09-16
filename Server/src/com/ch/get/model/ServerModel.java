package com.ch.get.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javafx.scene.control.TextArea;

public class ServerModel extends Thread{
	
	private final UUID uuid;
	private HashMap<String, List<Object>> user;
	private List<Object> clientStream;
	private Socket socket;
	private TextArea textArea;
	private LocalTime time;
	
	private BufferedReader br; //문자 스트림
	private BufferedWriter bw;
	
	private boolean ready = true;
	private Object lock;
	
	//public ServerModel() {}
	
	public ServerModel(TextArea textArea, Socket socket, HashMap<String, List<Object>> user, Object lock) {
		uuid = UUID.randomUUID();
		this.textArea = textArea;
		this.socket = socket;
		this.user = user;
		this.lock = lock;
		
		//Stream 생성
		clientStream = new ArrayList<Object>();
		clientStream.clear();
		
		try {
			bw = new BufferedWriter(
					new OutputStreamWriter(
							socket.getOutputStream()));
			
			br = new BufferedReader(
					new InputStreamReader(
							socket.getInputStream()));
			
			clientStream.add(bw);
			clientStream.add(br);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			user.put(uuid.toString(), clientStream);
		}
	}

	@Override
	public void run() {
		try {
			synchronized (lock) {
				String msg = br.readLine();
				InetAddress name = socket.getInetAddress();
				textArea.appendText(name.getHostAddress()+" : "+name.getHostName()+"\n");
				textArea.appendText(user.size()+"\n"+clientStream.get(0)+"\n");
				
				time = LocalTime.now();
				msg = "Hi "+name.getHostName()+" "+time.toString();
				
				bw.write(msg);
				bw.newLine();
				bw.flush();
			}
		} catch (Exception e) {
			textArea.appendText(socket.getInetAddress()+" 클라이언트 종료\n");
		}
	}
	
	//getter
	public Socket getSocket() {
		return socket;
	}
	public String getUuid() {
		return uuid.toString();
	}
	public BufferedWriter getBw() {
		return bw;
	}
	public BufferedReader getBr() {
		return br;
	}
}
