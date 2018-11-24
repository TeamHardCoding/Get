package ch.get.model;

import java.time.LocalTime;

public class AsNotingClock implements Runnable {
	
	private static AsNotingClock inst = null;
	private LocalTime time;
	public boolean active;
	
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
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public LocalTime getTime() {
		return time;
	}
}
