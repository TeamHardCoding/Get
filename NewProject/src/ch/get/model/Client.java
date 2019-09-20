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
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

import ch.get.util.ShowAlertWindow;
import ch.get.view.ClientLayoutController;
import javafx.scene.control.Alert.AlertType;

public class Client{

	private ClientLayoutController cont = null;
	private final String SERVER_IP = "192.168.100.134";
	private final int SERVER_PORT = 8000;
	private Socket socket;
	
	private PrintWriter pw;
	
	public Client() {
		socket = new Socket();
		try {			
			socket.connect(
					new InetSocketAddress(SERVER_IP, SERVER_PORT), 10000); //서버 접속, 타임아웃 10초
			
			//Stream 생성
			pw = new PrintWriter(
					new BufferedWriter(
							new OutputStreamWriter(
									socket.getOutputStream(),
									StandardCharsets.UTF_8)),
									true); // AutoFlush
			pw.write(Inet4Address.getLocalHost()+" 접속 요청");
			
			 //클라이언트 실행
			cont = ClientLayoutController.cliContInstance;
			new StartClientProcessor(socket).start();
			cont.inputDataListView("클라이언트 서버 접속 완료!");
		} catch (UnknownHostException e) {
			new ShowAlertWindow(AlertType.INFORMATION, "서버를 찾을수 없습니다.", "IP나 PORT가 잘못 되었습니다.");
		} catch (IOException e) {
			new ShowAlertWindow(AlertType.INFORMATION, "서버를 찾을수 없습니다.", "IP나 PORT가 잘못 되었습니다.");
		} catch (Exception e) {
			
		}
	}
	
	class StartClientProcessor extends Thread{
		//initClient
		private Socket socket;
		private String msg;
		private Thread receiveThread;
		
		public StartClientProcessor(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			
			// receiveThread
			receiveThread = new Thread( () -> 
			{
				BufferedReader br;
				try {
					br = new BufferedReader( //메시지 받낭.
							new InputStreamReader(socket.getInputStream()));	
					while(true) {
						msg = br.readLine();
						cont.inputDataListView(msg);
						
						if(msg == null) {
							cont.inputDataListView("서버 연결 끊김...");
							break;
						}
					}
				} catch (IOException e) {
				} catch (Exception e) {
				}
			});
			receiveThread.setDaemon(true);
			receiveThread.start();
			System.out.println("cc");
			//File
		}
		
		/*
		 * getter/setter
		 */
		public Socket getSocket() {
			return socket;
		}
		public void setSocket(Socket socket) {
			this.socket = socket;
		}
	}
}
