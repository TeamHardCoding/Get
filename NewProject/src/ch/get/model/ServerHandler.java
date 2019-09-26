package ch.get.model;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import ch.get.util.Protocol;
import ch.get.view.RootLayoutController;
import javafx.fxml.FXML;

public class ServerHandler extends Thread {

	public ServerHandler() {
		this.PORT = 8000;
		tpe = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()); // 멀팄쓰레딩
	}

	private final int PORT;
	private String serverIp;
	private List<PrintWriter> clientLists = new ArrayList<PrintWriter>();
	private List<String> clientIpLists = new ArrayList<String>();
	private List<Socket> clientSockets = new ArrayList<Socket>();
	private ServerSocket serverSocket = null;

	private ThreadPoolExecutor tpe;

//	private Server serverInst = null;
	private Object lock = new Object();

	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(); // 서버소켓 생성
			serverIp = InetAddress.getLocalHost().getHostAddress(); // 서버 IP를 받아옴
			serverSocket.bind(new InetSocketAddress(serverIp, PORT));
			RootLayoutController.rcl.printText("서버 IP : " + serverIp + "서버 PORT : " + getPORT());
			RootLayoutController.rcl.printText("서버 접속 대기중...");

			while (true) {
				Socket socket = serverSocket.accept();
				tpe.execute(new Server(socket, clientLists, clientIpLists));
				clientSockets.add(socket);
//				serverInst = new Server(socket, clientLists, clientIpLists);
//				serverInst.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeServer();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	// 서버 종료
	@FXML
	private boolean closeServer() {
		boolean op = false;
		PrintWriter pw;
		String request = Protocol.QUIT.name() + ":" + "서버가 종료 되었습니다.";

		try {
			if (!clientSockets.isEmpty()) {
				for (Socket socket : clientSockets) {
					pw = new PrintWriter(
							new OutputStreamWriter(
									socket.getOutputStream(), StandardCharsets.UTF_8));
					
					pw.println(request);
					pw.flush();
					RootLayoutController.rcl.printText(socket.getLocalAddress()+"에게 종료 명령 전송...");
				}
				
				RootLayoutController.rcl.printText("각 클라이언트 에게 종료명령 전송 완료...");
			}

			synchronized (lock) {
				serverSocket.close();
				op = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			op = false;
		}
		
		return op;
	}
	
	public boolean handleCloseServer() { //외부 접근
		
		return closeServer();
	}

//	/**************************************************/
//	public boolean stopServer() { // 컨트롤러 핸들링
//		boolean op = false;
//
//		try {
//			if (serverInst != null) {
//				serverInst.procQuitFromServer();
//				op = true;
//			} else {
//				closeServerSocket(getServerSocket());
//				op = false;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			op = false;
//		}
//
//		return op;
//	}
//
//	private void closeServerSocket(ServerSocket serverSocket) { // 서버소켓 close
//		try {
//			if ((serverSocket != null) || (!serverSocket.isClosed())) {
//
//				serverSocket.close();
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	// getter
	public int getPORT() {
		return PORT;
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public List<String> getClientIpLists() {
		return clientIpLists;
	}

	public List<PrintWriter> getClientLists() {
		return clientLists;
	}
}
