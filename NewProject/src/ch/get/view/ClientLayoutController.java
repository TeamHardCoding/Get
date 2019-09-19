package ch.get.view;

import java.net.URL;
import java.util.ResourceBundle;

import ch.get.MainApp;
import javafx.fxml.Initializable;

public class ClientLayoutController implements Initializable {

	private MainApp mainApp;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		

	}
	
	/*
	 * setter/getter
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
