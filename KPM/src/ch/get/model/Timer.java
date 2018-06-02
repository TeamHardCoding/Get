package ch.get.model;

import java.util.ArrayList;
import ch.get.view.TImerViewController;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Timer implements Runnable{
	
	private Label hour;
	private Label minute;
	private Label second;
	
	private TextField startKill;
	private TextField myPower;
	private Button startBtn;
	
	private Integer cnt_h;
	private Integer cnt_m;
	private Integer cnt_s;
	
	private ArrayList<Integer> timeResult;
	private TImerViewController viewCont;
	private Boolean flag;
	
	public Timer(TImerViewController cont) {
		// TODO Auto-generated constructor stub
		flag = true;
		viewCont = cont;
		timeResult = new ArrayList<Integer> ();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		flag = false;
		
		cnt_h = 0;
		cnt_m = 0;
		cnt_s = 0;
		
		try
		{
			viewCont.startInfoView();
			
			while(!flag)
			{	
				Platform.runLater(new Runnable() {					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						second.setText(cnt_s.toString());
						minute.setText(cnt_m.toString());
						hour.setText(cnt_h.toString());
					}
				});
				
				Thread.sleep(10);
					
				cnt_s++;
				cnt_m += (cnt_s/60);
				cnt_h += (cnt_m/60);
				if(cnt_s == 60) { cnt_s = 0; }
				if(cnt_m == 60) { cnt_m = 0; }	
			}		 
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}	
	}
	
	public void setTimerLabel(ArrayList<Label> temp)
	{
		hour = temp.get(0); //h
		minute = temp.get(1); //m
		second = temp.get(2); //s
	}
	
	public void stopTimer()
	{
		flag = true;
		
		timeResult.clear(); //List 비워주기
		if(viewCont.minTimeVaild(cnt_m))
		{		
			timeResult.add(cnt_s);
			timeResult.add(cnt_m);
			timeResult.add(cnt_h);
		}
		
		viewCont.setOnCompo(timeResult);
	}
	
	public Boolean getTimerState()
	{
		return flag;
	}
}