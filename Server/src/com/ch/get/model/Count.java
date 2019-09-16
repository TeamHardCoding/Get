package com.ch.get.model;

import com.ch.get.ServerStart;
import com.ch.get.view.RootLayoutController;

import javafx.scene.control.TextArea;

public class Count implements Runnable{

	private int value;
	private TextArea textArea;
	private ServerStart mainApp;
	
	public Count(int val, TextArea textArea, ServerStart mainApp) {
		this.value = val;
		this.textArea = textArea;
		this.mainApp = mainApp;
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				textArea.appendText("서버 종료 "+value+"초 전.....\n");
				Thread.sleep(1000);
				value--;
				
				if(value == 0) {
					mainApp.exit_Now();
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void setMainApp(ServerStart mainApp) {
		this.mainApp = mainApp;
	}
}
