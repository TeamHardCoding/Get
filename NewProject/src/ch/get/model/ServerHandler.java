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
		tpe = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()); // �ֻG������
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
			serverSocket = new ServerSocket(); // �������� ����
			serverIp = InetAddress.getLocalHost().getHostAddress(); // ���� IP�� �޾ƿ�
			serverSocket.bind(new InetSocketAddress(serverIp, PORT));
			RootLayoutController.rcl.printText("���� IP : " + serverIp + " ���� PORT : " + getPORT());
			RootLayoutController.rcl.printText("���� ���� �����...");

			while (true) {
				Socket socket = serverSocket.accept();

				tpe.execute(new Server(socket, clientLists, clientIpLists));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ���� ����
	@FXML
	private boolean closeServer() {
		boolean op = false;

		try {
			tpe.shutdownNow();
			serverSocket.close();
			RootLayoutController.rcl.printText("���� ����");
			op = true;
		} catch (Exception e) {
			e.printStackTrace();
			op = false;
		}

		return op;
	}

	public boolean handleCloseServer() { // �ܺ� ����

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
