package com.ch.get.view;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.ch.get.model.ServerModel;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class RootLayoutController implements Initializable{

	@FXML Label time_Label;
	@FXML TextArea textArea;
	private LocalDateTime ldt;
	private Task<Void> task;
	private Thread server;
	
	public RootLayoutController() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//initTime
		show_Time();
	}
	
	public void start_Server() {
		server = new Thread(new ServerModel(textArea));
		server.setDaemon(true);
		server.start();
	}
	
	public void show_Time() {
		
		//시간 형식 변환
		DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_TIME;
		
		task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				
				while(true) {
					ldt = LocalDateTime.now();
					updateMessage(ldt.format(dtf));
					Thread.sleep(10);
				}
			}
		};
		
		time_Label.textProperty().bind(task.messageProperty());
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}
	
	public void exit_Programs() {
		
		Platform.exit();
	}
}
