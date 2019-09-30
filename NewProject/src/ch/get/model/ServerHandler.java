package ch.get.model;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

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
	private ServerSocket serverSocket = null;

	private ThreadPoolExecutor tpe;
	private Object lock = new Object();

	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(); // 서버소켓 생성
			serverIp = InetAddress.getLocalHost().getHostAddress(); // 서버 IP를 받아옴
			serverSocket.bind(new InetSocketAddress(serverIp, PORT));
			RootLayoutController.rcl.printText("서버 IP : " + serverIp + " 서버 PORT : " + getPORT());
			RootLayoutController.rcl.printText("서버 접속 대기중...");

			while (true) {
				Socket socket = serverSocket.accept();

				tpe.execute(new Server(socket, clientLists, clientIpLists));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 서버 종료
	@FXML
	private boolean closeServer() {
		boolean op = false;

		try {
			tpe.shutdownNow();
			serverSocket.close();
			RootLayoutController.rcl.printText("서버 종료");
			op = true;
		} catch (Exception e) {
			e.printStackTrace();
			op = false;
		}

		return op;
	}

	public boolean handleCloseServer() { // 외부 접근

		return closeServer();
	}

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
