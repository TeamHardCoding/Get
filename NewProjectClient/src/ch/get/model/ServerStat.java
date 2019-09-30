package ch.get.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import ch.get.util.Protocol;
import ch.get.view.LoginLayoutController;
import javafx.scene.control.Label;

public class ServerStat implements Runnable {
	private Socket socket;
	private Label serverStatLabel;

	private PrintWriter pw;

	private String serverIp;
	private int serverPort;
	private String nickName;
	private static HashMap<Integer, ServerInfo> serverLists;
	private boolean isOkClicked;

	public ServerStat(Label label) {
		serverStatLabel = label;
		isOkClicked = false; // OK���ȴ���
		serverLists = LoginLayoutController.getServerLists(); // static
		serverStatLabel.setStyle("-fx-background-color: red;");
		socket = getNewSocket();
		nickName = "����Ŭ��";

	}

	@Override
	public void run() {
		String msg = null;

		while (true) {
			try {
				Thread.sleep(1000);

				if (serverLists.size() > 0) {
					if (!socket.isConnected()) {
						serverIp = serverLists.get(0).getServerIp();
						serverPort = Integer.parseInt(serverLists.get(0).getServerPort());
						socket.connect(new InetSocketAddress(serverIp, serverPort));
						pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
						new ReceiveThread(socket).start();
					}

					if(socket.isConnected() && !socket.isClosed()) {
						sendOpenMsgToServer(socket, pw);
					}
//					if (socket.isConnected() && !socket.isClosed()) {
//						serverStatLabel.setStyle("-fx-background-color: green;");
//						System.out.println("Server is open");
//						sendOpenMsgToServer(socket, pw); // ���� ���� ����.
//					} else {
//						serverStatLabel.setStyle("-fx-background-color: red;");
//						System.out.println("server is close");
//					}
				}
			} catch (Exception e) { // ������ ������ ������ �Ϸο�
//				System.out.println("c");
				e.printStackTrace();
				serverStatLabel.setStyle("-fx-background-color: red;");

				if (socket.isConnected()) {
					if ((socket != null) || (!socket.isClosed())) {

						closeSocket(socket);
					}
				}
			}
		}
	}

	// ���� Ȯ��
	public void sendOpenMsgToServer(Socket socket, PrintWriter pw) {
		String openMsg = Protocol.OPEN + ":" + nickName;

		try {
			pw.println(openMsg);
			pw.flush();
		} catch (Exception e) {
			if (pw != null) {
				pw.close();
			}

			e.printStackTrace();
		}
	}

	// JOIN �� ���� ����
	public void sendJoinMsgToServer(Socket socket, PrintWriter pw) {
//		PrintWriter pw = null;
		String joinMsg = Protocol.JOIN + ":" + nickName;

		try {
			pw.println(joinMsg);
			pw.flush();
		} catch (Exception e) {
			if (pw != null) {
				pw.close();
			}

			e.printStackTrace();
		}
	}

	public void closeSocket(Socket socket) {

		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static HashMap<Integer, ServerInfo> getServerLists() {
		return serverLists;
	}

	public Socket getConnectionSocket() {
		return socket;
	}

	public Socket getNewSocket() {
		socket = new Socket();
		return socket;
	}

	class ReceiveThread extends Thread {

		private BufferedReader br;
		private Socket socket;
		private String msg;

		public ReceiveThread(Socket socket) throws IOException {
			this.socket = socket;
			br = new BufferedReader(new InputStreamReader(socket.getInputStream())); // ���ú�� ���۸���
			super.setDaemon(true); // ���� ������
		}

		@Override
		public void run() {
			msg = null;

			while (true) {
				try {
					msg = br.readLine(); // ���� �������� ���ú�
					
					if (msg.equals(Protocol.OPEN.name()) ) { // ���� �������� ���ú�
						serverStatLabel.setStyle("-fx-background-color: green;"); // ������ ���� �ִٸ� �ʷϺ�
						System.out.println("ServerStat 158Line : "+msg);
					} else {
						serverStatLabel.setStyle("-fx-background-color: red;"); // ������ ���� �ִٸ� �ʷϺ�
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		public Socket getSocket() {
			return socket;
		}
	}
}
