/*
 * Author ch.Get
 */
package ch.get.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

import ch.get.util.ShowAlertWindow;
import ch.get.view.ClientLayoutController;
import javafx.scene.control.Alert.AlertType;

public class Client{

	private Object lock;
	private ClientLayoutController cont = null;
	private int serverPort;
	private String serverIp;
	private Socket socket;
	
	//sendMsg
	private PrintWriter pw;
	String msg;
	
	public Client(Object lock) 
	{
		//동기화 Mutex
		this.lock = lock;
		//최초 서버 설정
		cont = ClientLayoutController.cliContInstance;
	}
	
	public void initClient() {
		try {		
			socket = new Socket();
			serverIp = cont.getServerIp();
			serverPort = cont.getServerPort();
			
			socket.connect(
					new InetSocketAddress(serverIp, serverPort), 3000); //서버 접속, 타임아웃 10초
			
			pw = new PrintWriter(
					new BufferedWriter(
							new OutputStreamWriter(
									socket.getOutputStream(),
									StandardCharsets.UTF_8)),
									true);
			//데이터 통신 쓰레드 실행
			new ChatClientReceiveThread(socket).start(); //리시브
			
			new ChatClientWritingThread(socket).start(); //센드
			
			
		} catch (UnknownHostException e) {
			new ShowAlertWindow(AlertType.INFORMATION, "호스트를 찾을수 없습니다.", "서버관리자 에게 문의 하세요.");
		} catch (IOException e) {
			new ShowAlertWindow(AlertType.INFORMATION, "서버를 찾을수 없습니다.", "IP나 PORT가 잘못 되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			String msg = "입력값\n"+"IP : "+serverIp+"\nPORT : "+serverPort;
			new ShowAlertWindow(AlertType.ERROR, "Setting을 눌러 IP 설정", msg);
		}
	}
	
	public void closeClient() {
		PrintWriter pw;
		
		synchronized (lock) {
			if(socket != null) {
				try {
					pw = new PrintWriter(
							new BufferedWriter(
									new OutputStreamWriter(
											socket.getOutputStream(),
											StandardCharsets.UTF_8)),
											true);
					
					pw.println("quit");
					pw.close();
					socket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	
	public void sendMsgToServer(String msg) {
		this.msg = socket.getLocalAddress().getHostAddress()
				+"("
				+socket.getLocalAddress().getHostName()
				+")"
				+" : "
				+msg;
		
		pw.println(this.msg);
	}
	
	//데이터 송수신 스트링
	class ChatClientWritingThread extends Thread {
	
		private Socket socket = null;
		private String requestFromServer;
		
		public ChatClientWritingThread(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true) {
				
			}
		}
	}
	
	//메시지 받기
	class ChatClientReceiveThread extends Thread {
		//initClient
		private Socket socket;
		private String msg;
		
		public ChatClientReceiveThread(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			
			BufferedReader br;
			
			try { 
				br = new BufferedReader( //메시지 받는 쓰레드
						new InputStreamReader(socket.getInputStream()));	
				
				while(true) {
					msg = br.readLine();
					if(msg.equalsIgnoreCase("quit")) { //서버에서 종료 메시지가 온다면 종료시킴
						closeClient();
					}
					cont.inputDataListView(msg);	
				}
			} catch (IOException e) {
			} catch (Exception e) {
			}
		}

	}
}