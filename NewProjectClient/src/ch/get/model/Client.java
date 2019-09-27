/*
 * Author ch.Get
 * 
 * protocol lists / JOIN, QUIT, MSG, FILE
 */
package ch.get.model;

import java.io.BufferedReader;
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
			
			//키보드 입력
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			//데이터 통신 쓰레드 실행
			chatReceiveThread = new ChatClientReceiveThread(socket); //리시브
//			chatWritingThread = new ChatClientWritingThread(socket); //센드
			
			//쓰레드 스타트
			chatReceiveThread.start();
//			chatWritingThread.start();
			String nickName = "클라";
			joinToServer(nickName);
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
							new OutputStreamWriter(
									socket.getOutputStream(), StandardCharsets.UTF_8));
					
					pw.println(Protocol.QUIT.name()+":"+"\r\n");
					pw.flush();
//					System.out.println("closeClient");
					socket.close();
				} catch (Exception e) {
					System.out.println("Exception");
					e.printStackTrace();
				}
			}
		}
	}
	
	public void sendMsgToServer(String msg) { //메시지 보내기
		PrintWriter pw;
		
		try {
			String temp = Protocol.MSG.name() //프로토콜 명시
					+":" //프로토콜 토큰
					+msg+"\r\n"; //메시지 명시
			
			ClientLayoutController.cliContInstance.inputDataListView(msg);
			
			pw = new PrintWriter(
					new OutputStreamWriter(
							socket.getOutputStream(), StandardCharsets.UTF_8)); 
			
			pw.println(temp);
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void joinToServer(String msg) {
		String temp = Protocol.JOIN.name()
				+":"
				+msg+"\r\n";
		
		//sendMsg
		PrintWriter pw;
		
		try {
			pw = new PrintWriter(
					new OutputStreamWriter(
							socket.getOutputStream(), StandardCharsets.UTF_8));
			pw.println(temp);
		} catch (Exception e) {
			// TODO: handle exception
		}
		//메시지 입력을 위한 스트림 생성

		
	}
	
	//메시지 받기
	class ChatClientReceiveThread extends Thread {
		//initClient
		private Socket socket;
		private String request;
		
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
					request = br.readLine();
					System.out.println(request);
					cont.inputDataListView(request);
				}
			} catch (IOException e) {
			} catch (Exception e) {
			}
		}

	}
	
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
}