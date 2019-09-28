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
	private boolean serverStat;
	private String serverIp;
	private int serverPort;
	private static HashMap<String, ServerInfo> serverLists;
	
	public ServerStat(Label label) {
		serverStatLabel = label;
		socket = new Socket();
		serverStat = false; //OK가 눌리면 서버 상태 넣어줌
		isOkClicked = false; //OK눌렸는지
		serverLists = LoginLayoutController.getServerLists();
	}

	@Override
	public void run() {
		
		while(true) {

			if(Thread.currentThread().isInterrupted()) {
				System.out.println("Thread Exit --");
				break;
			}
			
			try {
				Thread.sleep(500);
				if(serverLists.size() > 0 ) {
					isOkClicked = ClientInfoSettingController.inst.isOkClicked();
					
					if(isOkClicked) {
						serverIp = serverLists.get("1").getServerIp();
						serverIp = serverLists.get("1").getServerPort();
						
						socket.connect(new InetSocketAddress(serverIp, serverPort));
						socket.setKeepAlive(false);
						serverStat = true;
					}				
				}
					
			} catch (Exception e) {
				serverStat = false;
				e.printStackTrace();
			}
			
			if(serverStat) {
				System.out.println("1");
				serverStatLabel.setStyle("-fx-background-color: slateblue;");
			} else {
				serverStatLabel.setStyle("-fx-background-color: red;");
			}
		}
	}
}
