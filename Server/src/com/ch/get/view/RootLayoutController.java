package com.ch.get.view;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.ch.get.model.Count;
import com.ch.get.model.CustomAlert;
import com.ch.get.model.ServerModel;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;

public class RootLayoutController implements Initializable{

	public static RootLayoutController rlc; 

	@FXML Label time_Label;
	@FXML TextArea textArea;
	@FXML TabPane tabPane;
	@FXML Button serverStart;
	@FXML Button serverStop;
	
	private LocalDateTime ldt;
	private Task<Void> task;
	private ServerModel server;
	
	public RootLayoutController() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		this.rlc = this;
		//initTime
		show_Time();
	}
	
	@FXML
	private void start_Server() {
		
		if(server == null) {
			swTabComp();
			server_Start();
		} 
	}
	
	@FXML
	private void stop_Server() {
		
		if(server != null) {
			swTabComp();
			server_Stop();	
			server = null;
		}
	}
	
	private void show_Time() {
		
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
	
	@FXML
	private void program_Exit() {
		
		String title;
		String con;
		
		if(server == null) {
			exit_Now();
		} else {
			title = "서버 강제 종료";
			con = "서버가 아직 구동중 입니다. 그래도 종료 하시겠습니까?";
			
			ButtonType type = new CustomAlert(AlertType.WARNING, con, title).button_Result();
			if(type.equals(ButtonType.OK)) {
				
				Thread stop = new Thread(new Count(5, textArea));
				stop.setDaemon(true);
				stop.start();
			}
		}			
	}
	
	//etc
	private void swTabComp() {;
		
		if(!serverStart.isDisable()) {
			serverStop.setDisable(false);
			serverStart.setDisable(true);
		} else {
			serverStop.setDisable(true);
			serverStart.setDisable(false);
		}
	}
	
	private void server_Stop() { server.closeServer(); }
	private void server_Start() {
		
		server = new ServerModel(textArea);
		Thread th = new Thread( server );
		th.setDaemon(true);
		th.start();	
	}
	
	public void exit_Now() { Platform.exit(); }
}
