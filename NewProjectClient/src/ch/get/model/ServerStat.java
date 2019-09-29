package ch.get.model;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;

import ch.get.view.ClientInfoSettingController;
import ch.get.view.LoginLayoutController;
import javafx.scene.control.Label;

public class ServerStat implements Runnable {

	private Label serverStatLabel;
	private Socket socket;
	private boolean isOkClicked;
	private String serverIp;
	private String serverPort;
	private static HashMap<Integer, ServerInfo> serverLists;

	public ServerStat(Label label) {
		serverStatLabel = label;
		isOkClicked = false; // OK´­·È´ÂÁö
		serverLists = LoginLayoutController.getServerLists();
	}

	@Override
	public void run() {

		while (true) {

			if (Thread.currentThread().isInterrupted()) {
				System.out.println("Thread Exit --");
				break;
			}

			try {
				Thread.sleep(1000);
				if (serverLists.size() > 0) {
					isOkClicked = ClientInfoSettingController.inst.isOkClicked();

					if (isOkClicked) {
						socket = getNewSocket();
						serverIp = serverLists.get(0).getServerIp();
						serverPort = serverLists.get(0).getServerPort();
						
						socket.connect(new InetSocketAddress(serverIp, Integer.parseInt(serverPort)));
						ClientInfoSettingController.inst.setOkClicked(false);
						socket.close();
					}
				}
			} catch (Exception e) {
				serverStatLabel.setStyle("-fx-background-color: red;");
			}

			if (socket != null) {
				if (socket.isConnected()) {
					serverStatLabel.setStyle("-fx-background-color: green;");
				} else {
					serverStatLabel.setStyle("-fx-background-color: red;");
				}
			}
		}
	}

	public Socket getNewSocket() {
		socket = new Socket();
		return socket;
	}
}
