package ch.get.model;

import ch.get.MainApp;
import javafx.application.Platform;

public class BreakTimer implements Runnable{

	private static BreakTimer bt = null;
	
	private MainApp mainApp;
	private Boolean stop;
	
	//SingleTone
	public static BreakTimer getInst()
	{
		synchronized (BreakTimer.class) {
			if(bt == null) { bt = new BreakTimer(); }
		}
		
		return bt;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!stop)
		{
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
				}
			});	
		}
	}

	public void setFlag(Boolean stop) {
		this.stop = stop;
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}