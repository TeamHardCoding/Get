package ch.get.view;

import java.net.URL;
import java.util.ResourceBundle;

import ch.get.Start;
import ch.get.model.PrintTime_Thread;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;

public class RootController implements Initializable {

	@FXML private TextArea textBox;
	@FXML private MenuItem startThread;
	@FXML private MenuItem interrupt;
	public Start mainApp;
	public static RootController inst = null;
	
	private Thread printTime_Thread;
	
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
	
	@FXML
	private void ac_StartThread() {
		startThread.setDisable(true);
		interrupt.setDisable(false);
		
		printTime_Thread = new Thread(new PrintTime_Thread("printTime"));
		printTime_Thread.setDaemon(true);
		printTime_Thread.start();
	}
	
	@FXML
	private void ac_Interrupt() {
		startThread.setDisable(false);
		interrupt.setDisable(true);
		
		printTime_Thread.interrupt(); //¿Œ≈Õ∑¥∆Æ
	}
	
	//setter
	public void setMainApp(Start mainApp) {
		this.mainApp = mainApp;
	}
	//getter

	public TextArea getTextBox() {
		return textBox;
	}
}