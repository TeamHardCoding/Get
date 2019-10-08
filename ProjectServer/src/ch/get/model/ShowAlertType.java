package ch.get.model;

import javafx.scene.control.Alert;

public class ShowAlertType extends Alert {
	
	public ShowAlertType(AlertType alertType, String title, String content) {
		super(alertType);
		
		setTitle(title);
		setHeaderText(content);
		showAndWait();
	}
}
