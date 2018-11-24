package ch.get.view;

import java.net.URL;
import java.util.ResourceBundle;

import ch.get.MainApp;
import ch.get.model.AsNotingClock;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class RootLController implements Initializable{

	public static RootLController inst; //컨트롤러
	
	//시계
	public Thread threadClock;
	public static AsNotingClock clock;
	
	private MainApp mainApp;
	
	@FXML private Label timeViewLabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		inst = this;
		startClock();
		//뷰 에 로드될 객체들
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	public static RootLController getInst() {
		return inst;
	}
	
	public void startClock()
	{
		clock = AsNotingClock.getInstance();
		threadClock = new Thread(clock); 
		
		threadClock.setDaemon(true);
		threadClock.start();
	}
}
