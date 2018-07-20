package ch.get.model;

import ch.get.MainApp;
import javafx.application.Platform;

public class RollingTimer implements Runnable{

	private MainApp mainApp;
	private Boolean flag;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(flag)
		{
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					
				}
			});
		}
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
