package com.ch.get.model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class CustomAlert extends Alert{

	private String con;
	private String title;
	
	public CustomAlert(AlertType type, String con, String title) {
		super(type);
		this.title = title;
		this.con = con;
		
		super.setTitle(title);
		super.setContentText(con);
		super.showAndWait();
	}
	
	public ButtonType button_Result() {
		ButtonType type;
		type = this.getResult();
		
		return type;
	}
}
