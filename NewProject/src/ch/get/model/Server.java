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
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

import ch.get.util.Protocol;
import ch.get.view.RootLayoutController;

public class Server implements Runnable {

	private Socket socket;
	private List<PrintWriter> listWriters;
	private String request;
	private String name;
	private String endUserIp;

	// 스트림
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
			BufferedReader br = new BufferedReader(
					new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));

			while (true) {
				request = br.readLine();
//				System.out.println(request);

				if (request == null) {
					printText("클라이언트 접속 종료.");
					procQuit(pw);
					break;
				}
				
				if(Thread.currentThread().isInterrupted()) {
					System.out.println("shutdown");
					procQuitFromServer(pw);
					break;
				}

				String[] protocol = request.split(":");
//				System.out.println(request);

				if (protocol[0].equals(Protocol.JOIN.name())) {
					printText(endUserIp + ":" + protocol[1]); // 닉네임
					procJoin(protocol[1], pw); // 조인 했을때 브로드 캐스트 함.
				} else if (protocol[0].equals(Protocol.QUIT.name())) {
					printText(endUserIp + ":" + " 접속 끊김");
					procQuit(pw);
				} else if (protocol[0].equals(Protocol.MSG.name())) {
					procSendMsg(protocol[1]);
				} else if(protocol[0].equals(Protocol.OPEN.name())) {
					procOpenMsg(protocol[1]);
				} else {
					if (protocol[0].equals(Protocol.FILE.name())) {
						
					}
				}
			}
		} catch (IOException e) { // 클라쪽 접속 종료 일때 일로옴
			System.out.println("ck");
//			printText(this.name + " 접속 종료");
//			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("ck");
//			e.printStackTrace();
		}
	}

	/* 서버 명령 */
	public void procQuitFromServer(PrintWriter pw) { // 서버 명령
		printText(endUserIp + ":" + "접속 끊김 - 서버 명령");

		try {
			pw.println(Protocol.QUIT.name());
			pw.flush();
			removeWriter(pw);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		String data = this.name + "님이 퇴장했습니다.";
		procQuit(getPw()); // procQuit 에 브로드 캐스트 포함
	}

	/* 클라 명령 */
	private void procOpenMsg(String msg) {
//		System.out.println(msg);
		getPw().println(Protocol.OPEN.name());
		getPw().flush();
	}
	
	private void procSendMsg(String msg) {
		printText(endUserIp + ":" + msg);
		broadCaster(this.name + ":" + msg);
	}

	private void procJoin(String nickname, PrintWriter pw) {
		this.name = nickname;
		broadCaster(this.name + " 접속.");
		addWriter(pw);
	}

	private void procQuit(PrintWriter pw) { // 브로드 캐스트 포함
		removeWriter(pw); // 스트림 삭제

		String data = this.name + "님이 퇴장했습니다.";
		broadCaster(data);
	}

	private void broadCaster(String temp) { // 클라이언트 브로드 캐스트

		System.out.println(listWriters.size() + " left pW Size");
		synchronized (listWriters) {
			for (PrintWriter printWriter : listWriters) {
				printWriter.println(temp);
				printWriter.flush();
			}
		}
	}

	private void addWriter(PrintWriter pw) { // 클라이언트 접속 할대 스트림 추가
		synchronized (listWriters) {
			listWriters.add(pw);
		}
	}

	private void removeWriter(PrintWriter pw) { // 클라이언트 접속 종료 할때 스트림 지움
		synchronized (listWriters) {
			listWriters.remove(pw);
		}
	}

	private void printText(String msg) { // 서버 콘솔 창에 접속 메시지 출력
		RootLayoutController.rcl.printText(msg);
	}

	public PrintWriter getPw() {
		return pw;
	}
}
