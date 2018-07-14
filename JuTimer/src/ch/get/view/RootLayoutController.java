package ch.get.view;

import java.net.URL;
import java.util.ResourceBundle;

import ch.get.MainApp;
import javafx.fxml.Initializable;

public class RootLayoutController implements Initializable{

	public static RootLayoutController inst; 
	private MainApp mainApp;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		inst = this;
	}
	
	//getter
	public static RootLayoutController getInst() {
		return inst;
	}

	//setter
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
