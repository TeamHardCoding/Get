package ch.get.view;

import java.net.URL;
import java.util.ResourceBundle;

import ch.get.MainApp;
import ch.get.model.AsNotingClock;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;

public class RootLController implements Initializable{

	private static RootLController inst; //��Ʈ�ѷ�
	
	//�ð�
	public Thread threadClock;
	
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
		threadClock = new Thread(AsNotingClock.getInstance()); 
		
		threadClock.setDaemon(true);
		threadClock.start();
	}
	
	public void tikTok(int sec)
	{
		Platform.runLater(()->
			timeViewLabel.setText(String.valueOf(sec))
		);
	}
	
	public void doubleClickMain()
	{
		mainApp.initSetting();
	}
	
	public void clickMain()
	{
		SplitPane pane = new SplitPane();
	}
}