package ch.get.view;

import java.net.URL;
import java.util.ResourceBundle;
import ch.get.MainApp;
import javafx.fxml.Initializable;

public class RootLayoutController implements Initializable{

	private MainApp mainApp;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void setMainApp(MainApp temp)
	{
		mainApp = temp;
	}
}
