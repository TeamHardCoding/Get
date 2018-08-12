package ch.get.model;

import ch.get.MainApp;
import javafx.application.Platform;
import javafx.scene.control.Label;

public class RollingTimer extends Thread{

	private static RollingTimer rt = null;
	
	private MainApp mainApp;
	private Boolean flag = false;
	
	//@@get Main from param 
	private Label rnd;
	private Label min;
	private Label sec;
	
	private Integer timeSec;
	private Integer timeMin;
	private Integer timeRnd;
	
	public RollingTimer() {
		// TODO Auto-generated constructor stub
	}
	
	//@@Singleton
	public static RollingTimer getInst()
	{
		synchronized(RollingTimer.class)
		{
			if(rt == null) { rt = new RollingTimer(); }	
		}
		
		return rt;	
	}
	
	public void setResource(Label rnd, Label min, Label sec) {
		// TODO Auto-generated constructor stub
		this.rnd = rnd;
		this.min = min;
		this.sec = sec;
		
		timeSec = new Integer(0);
		timeMin = new Integer(0);
		timeRnd = new Integer(0);
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
					rnd.setText(timeRnd.toString());
					min.setText(timeMin.toString());
					sec.setText(timeSec.toString());
				}
			});
			
			try
			{
				Thread.sleep(1000);
				
				timeSec++;
				timeMin += (timeSec/60);
				timeRnd++;
				
				if(timeSec == 60) { timeSec = 0; }
			}
			catch(Exception e)
			{
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

	public Boolean getFlag() {
		return flag;
	}
}
	
