package ch.get.util;

import javafx.scene.control.Alert;

public class ShowAlertWindow extends Alert {

	private String title;
	private String head;
	
	public ShowAlertWindow(AlertType type, String title, String head) {
		super(type);
		super.setTitle(title);
		super.setHeaderText(head);
		showAndWait();
	}
}
