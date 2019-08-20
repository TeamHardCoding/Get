package ch.get.view;

import java.net.URL;
import java.util.ResourceBundle;

import ch.get.Start;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class RootController implements Initializable {

	@FXML TextArea textBox;
	public Start mainApp;
	public static RootController inst = null;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		inst = this; //ΩÃ±€≈Ê
	}

	//handler
	@FXML
	private void ck_Exit() {
		Platform.exit();
	}

	@FXML
	private void ck_Info() {
		if(mainApp != null) {
			mainApp.init_Layout();
		}
	}
	
	//setter
	public void setMainApp(Start mainApp) {
		this.mainApp = mainApp;
	}
	//getter
}
