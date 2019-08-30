package com.ch.get.view;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class RootLayoutController implements Initializable{

	@FXML Label time_Label;
	private LocalDateTime ldt;
	
	public RootLayoutController() {
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//initTime
		show_Time();
	}
	
	public void show_Time() {
		
		//시간 형식 변환
		DateTimeFormatter dtf = DateTimeFormatter.RFC_1123_DATE_TIME;
	}
	
	public void exit_Programs() {
		
		Platform.exit();
	}
}
