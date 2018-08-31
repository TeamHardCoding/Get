package ch.get.model;

import javafx.scene.control.Label;

public class TimerChange {

	private Label rnd;
	private Label min;
	private Label sec;
	private Integer timeSec;
	private Integer timeMin;
	private Integer timeRnd;
	
	public TimerChange(Label rnd, Label min, Label sec) {
		// TODO Auto-generated constructor stub
		this.rnd = rnd;
		this.min = min;
		this.sec = sec;
		
		timeSec = new Integer(0);
		timeMin = new Integer(0);
		timeRnd = new Integer(0);
	}
	
	public synchronized void rollingThread()
	{
		try {
			wait();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public synchronized void breakThread()
	{
		try {
			wait();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
