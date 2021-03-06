package ch.get.model;

import ch.get.MainApp;

public class RollingTimer implements Runnable{

	private static RollingTimer rt = null;
	
	private MainApp mainApp;
	private Boolean stop;
	
	public RollingTimer() {
		// TODO Auto-generated constructor stub
	}
	
	//@@Singleton
	public static RollingTimer getInst()
	{
		synchronized(RollingTimer.class) {
			if(rt == null) { rt = new RollingTimer(); }	
		}
		
		return rt;	
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!stop)
		{
			
		}
	}
	
	public void setFlag(Boolean stop) {
		this.stop = stop;
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}