package ch.get.view;

import java.net.URL;
import java.util.ResourceBundle;

import ch.get.ServerMain;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ServerRootLayoutController implements Initializable {

	public static ServerRootLayoutController sevRootCont = null;

	private ServerMain mainApp;

	@FXML
	private TextField userChatTextField;
	@FXML
	private TextArea serverConsole;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		sevRootCont = this;

//		ServerRootLayoutController.sevRootCont.printConsole("�ȳ�1");
//		ServerRootLayoutController.sevRootCont.printConsole("�ȳ�2");
	}

	public void printConsole(String msg) {

		if (msg != null) {
			serverConsole.appendText(msg + "\n");
		}
	}

	public ServerMain getMainApp() {
		return mainApp;
	}

	public void setMainApp(ServerMain mainApp) {
		this.mainApp = mainApp;
	}
}