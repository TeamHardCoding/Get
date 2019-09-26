/*
 * Author ch.Get
 * 
 * protocol lists / JOIN, QUIT, MSG, FILE
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

import ch.get.util.Protocol;
import ch.get.util.ShowAlertWindow;
import ch.get.view.ClientLayoutController;
import javafx.scene.control.Alert.AlertType;

public class Client{

	private Object lock;
	private ClientLayoutController cont = null;
	private int serverPort;
	private String serverIp;
	private Socket socket;
	private ChatClientReceiveThread chatReceiveThread;
//	private ChatClientWritingThread chatWritingThread;
	
	//sendMsg
	private PrintWriter pw;
	
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
	
			//메시지 입력을 위한 스트림 생성
			pw = new PrintWriter(
					new BufferedWriter(
							new OutputStreamWriter(
									socket.getOutputStream(),StandardCharsets.UTF_8)),true);
			
			//데이터 통신 쓰레드 실행
			chatReceiveThread = new ChatClientReceiveThread(socket); //리시브
//			chatWritingThread = new ChatClientWritingThread(socket); //센드
			
			//쓰레드 스타트
			chatReceiveThread.start();
//			chatWritingThread.start();
			joinToServer("접속 하셨습니다.");
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
	
	//클라이언트 종료
	public void closeClient() {
		PrintWriter pw;
		
		synchronized (lock) {
			if(socket != null) {
				try {
					pw = new PrintWriter(
							new BufferedWriter(
									new OutputStreamWriter(socket.getOutputStream(),StandardCharsets.UTF_8)),true);
					
					pw.write("quit: ");
					pw.close();
					socket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void sendMsgToServer(String msg) { //메시지 보내기
		String temp = Protocol.MSG.name() //프로토콜 명시
				+":" //프로토콜 토큰
				+msg; //메시지 명시
		
		pw.println(temp);
	}
	
	public void joinToServer(String msg) {
		String temp = Protocol.JOIN.name()
				+":"
				+msg;
		
		pw.println(temp);
	}
	
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	
	
//	//데이터 송수신 스트링
//	class ChatClientWritingThread extends Thread {
//	
//		private Socket socket = null;
//		
//		public ChatClientWritingThread(Socket socket) {
//			this.socket = socket;
//		}
//		
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//			
//			try {
//
//				
//				while(true) {
//					
//				}	
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
	
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