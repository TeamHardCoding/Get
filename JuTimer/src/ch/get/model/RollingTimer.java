package ch.get.model;

import ch.get.MainApp;
import javafx.application.Platform;
import javafx.scene.control.Label;

public class RollingTimer implements Runnable{

	private MainApp mainApp;
	private Boolean flag = true;
	
	//@@get Main from param 
	private Label rnd;
	private Label min;
	private Label sec;
	
	public RollingTimer(Label rnd, Label min, Label sec) {
		// TODO Auto-generated constructor stub
		this.rnd = rnd;
		this.min = min;
		this.sec = sec;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(flag)
		{
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					rnd.setText("rnd/");
					min.setText("min/");
					sec.setText("sec/");
				}
			});
			
			try
			{
				Thread.sleep(1000);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
				break;
			}
		}
	}
	
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
