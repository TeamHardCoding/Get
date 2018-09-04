package ch.get.model;

import javafx.scene.control.Label;

public class TimerChange {

	private static TimerChange tc = null;
	
	private Label rnd;
	private Label min;
	private Label sec;
	private Integer timeSec;
	private Integer timeMin;
	private Integer timeRnd;
	
	private Boolean sw = false;
	
	public static TimerChange getInst(Label rnd, Label min, Label sec)
	{
		synchronized (TimerChange.class) {
			if(tc == null) { tc = new TimerChange(rnd, min, sec); }
		}

		return tc;
	}
	
	public TimerChange(Label rnd, Label min, Label sec) {
		// TODO Auto-generated constructor stub
		this.rnd = rnd;
		this.min = min;
		this.sec = sec;
		
		timeSec = new Integer(0);
		timeMin = new Integer(0);
		timeRnd = new Integer(0);
	}
	
	public synchronized void rollingThread(int sec, int min, int rnd)
	{
		try {
			wait();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		if(sw == true)
		{
			notify();
		}
	}
	
	public synchronized void breakThread(int sec, int min, int rnd)
	{
		try {
			wait();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		if(sw == false)
		{
			notify();
		}
	}

	public void setSw(Boolean sw) {
		this.sw = sw;
	}
}
