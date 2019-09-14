package com.ch.get.model;

import com.ch.get.view.RootLayoutController;

import javafx.scene.control.TextArea;

public class Count implements Runnable{

	private int value;
	private TextArea textArea;
	
	public Count(int val, TextArea textArea) {
		this.value = val;
		this.textArea = textArea;
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				textArea.appendText("서버 종료 "+value+"초 전.....\n");
				Thread.sleep(1000);
				value--;
				
				if(value == 0) {
					RootLayoutController.rlc.exit_Now();
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
