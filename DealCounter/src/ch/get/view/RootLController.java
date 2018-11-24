package ch.get.view;

import java.net.URL;
import java.util.ResourceBundle;

import ch.get.MainApp;
import ch.get.model.AsNotingClock;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class RootLController implements Initializable{

	public static RootLController inst; //��Ʈ�ѷ�
	
	//�ð�
	public Thread threadClock;
	public static AsNotingClock clock;
	
	private MainApp mainApp;
	
	@FXML private Label timeViewLabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		inst = this;
		startClock();
		//�� �� �ε�� ��ü��
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
