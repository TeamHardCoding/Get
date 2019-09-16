package com.ch.get.view;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.ch.get.ServerStart;
import com.ch.get.model.Count;
import com.ch.get.model.CustomAlert;
import com.ch.get.model.ServerInit;

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
	private ServerInit server;
	private ServerStart mainApp;
	
	public RootLayoutController() {
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		RootLayoutController.rlc = this;
		//initTime
		show_Time();
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
	private void start_Server() { //handle line127
		
		if(server == null) {
			swTabComp();
			server_Start();
		} 
	}
	
	@FXML
	private void stop_Server() { //handle line126
		
		if(server != null) {
			swTabComp();
			server_Stop();	
			server = null;
		}
	}
	
	@FXML
	private void program_Exit() {
		
		String title;
		String con;
		
		if(server == null) {
			mainApp.exit_Now();
		} else {
			title = "서버 강제 종료";
			con = "서버가 아직 구동중 입니다. 그래도 종료 하시겠습니까?";
			
			ButtonType type = new CustomAlert(AlertType.WARNING, con, title).button_Result();
			if(type != null) {
				if(type.equals(ButtonType.OK)) {
					//count class 안에 종료 메소드 호출
					server.closeServer();
					Thread stop = new Thread(new Count(5, textArea, mainApp));
					stop.setDaemon(true);
					stop.start();
				}
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
	
	//handler
	public void server_Exit() { program_Exit(); } // 완전 종료
	public void server_Stop() { server.closeServer(); } // 서버만 종료
	public void server_Start() { // 서버만 생성
		server = new ServerInit(textArea);
		server.start();
	}
	
	public void setMainApp(ServerStart mainApp) {
		this.mainApp = mainApp;
	}
}
