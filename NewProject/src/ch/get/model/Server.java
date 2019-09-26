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
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

import ch.get.util.Protocol;
import ch.get.view.RootLayoutController;

public class Server extends Thread {
	
	private Socket socket;
	private List<PrintWriter> listWriters;
	private String request;
	private String name;
	private String endUserIp;
	
	//스트림
	private PrintWriter pw;
	
	public Server(Socket socket, List<PrintWriter> temp, List<String> clientIpLists) {
		this.socket = socket;
		listWriters = temp;
		endUserIp = socket.getInetAddress().getHostAddress();
		
		clientIpLists.add(endUserIp);
	}
	
	@Override
	public void run() {
		request = null;
		
		try {
			BufferedReader br = 
					new BufferedReader(
							new InputStreamReader(
									socket.getInputStream(), StandardCharsets.UTF_8));
			
			pw = new PrintWriter
					(new BufferedWriter(
							new OutputStreamWriter(
									socket.getOutputStream(), StandardCharsets.UTF_8)), true);
			
			while(true) {
				request = br.readLine();
				System.out.println(request);
				
				if(request == null) {
					printText("클라이언트 접속 종료.");
					break;
				}
				
				String protocol[] = request.split(":");
				String proTemp = protocol[0].toUpperCase();	
				String msg = protocol[1];
				
				if(proTemp.equals(Protocol.JOIN.name())) {
					printText(endUserIp+" : "+msg);
					procJoin(msg, pw); //조인 했을때 브로드 캐스트 함.
				} else if(proTemp.equals(Protocol.QUIT.name())) {
					printText(endUserIp+" : "+msg+" 접속 끊김");
					procQuit(pw);
				} else if(proTemp.equals(Protocol.MSG.name())) {
					System.out.println("chk");
					procSendMsg(msg);
				} else {
					if(proTemp.equals(Protocol.FILE.name())) {
						
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();	
		}
	}
	
	/*서버 명령*/
	public void procQuitFromServer() { //서버 명령
		printText(endUserIp+" : "+"접속 끊김 - 서버 명령");
		pw.println("서버에서 접속을 종료 시켰습니다.");
		
		String data = this.name+"님이 퇴장했습니다.";
		procQuit(getPw()); //procQuit 에 브로드 캐스트 포함
	}
	
	/*클라 명령*/
	private void procSendMsg(String msg) {
		printText(endUserIp+" : "+msg);
		broadCaster(this.name+" : "+msg);
	}
	
	private void procJoin(String nickname, PrintWriter pw) {
		this.name = nickname;
		addWriter(pw);
		broadCaster(this.name+" 접속.");
	}
	
	private void procQuit(PrintWriter pw) { // 브로드 캐스트 포함
		removeWriter(pw); //스트림 삭제
		
		String data = this.name+"님이 퇴장했습니다.";
		broadCaster(data);
	}
	
	private void broadCaster(String temp) { //클라이언트 브로드 캐스트
		pw.println("전송");
		synchronized (listWriters) {
			for (PrintWriter printWriter : listWriters) {
				printWriter.println(temp);
				printWriter.flush();
			}
		}
	}
	
	private void addWriter(PrintWriter pw) { //클라이언트 접속 할대 스트림 추가
		synchronized (listWriters) {
			listWriters.add(pw);
		}
	}
	
	private void removeWriter(PrintWriter pw) { //클라이언트 접속 종료 할때 스트림 지움
		synchronized (listWriters) {
			listWriters.remove(pw);
		}
	}

	private void printText(String msg) { //서버 콘솔 창에 접속 메시지 출력
		RootLayoutController.rcl.printText(msg);
	}
	
	public PrintWriter getPw() {
		return pw;
	}
}
