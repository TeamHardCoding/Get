package ch.get.model;

import java.time.LocalTime;

import ch.get.view.RootLController;
import javafx.application.Platform;

public class AsNotingClock<T> implements Runnable {
	
	private static AsNotingClock inst = null;
	private LocalTime time;
	public boolean active;

	private T tmp;
	
	public static AsNotingClock getInstance()
	{
		if(inst == null)
		{
			synchronized(AsNotingClock.class)
			{
				inst = new AsNotingClock();
			}
		}
		
		return inst;
	}
	
	private AsNotingClock() {
		// TODO Auto-generated constructor stub
		time = null;
		active = false;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!active)
		{
			try {
				Thread.sleep(1000);
				time = LocalTime.now();
				RootLController.getInst().tikTok(time.getSecond());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}