package ch.get.model;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import ch.get.util.ShowAlertWindow;
import javafx.scene.control.Alert.AlertType;

public class Client extends Thread {

	private final String SERVER_IP = "192.168.100.134";
	private final int SERVER_PORT = 8000;
	private Socket socket;
	
	public Client() {
		
		try {
			socket = new Socket(SERVER_IP, SERVER_PORT);
		} catch (UnknownHostException e) {
			new ShowAlertWindow(AlertType.INFORMATION, "서버를 찾을수 없습니다.", "IP나 PORT가 잘못 되었습니다.");
		} catch (IOException e) {
		} catch (Exception e) {
		}
	}
	
	@Override
	public void run() {
		
	}
	
	public void initClient() {
		
	}
}
