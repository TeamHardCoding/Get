package ch.get.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ShowAlertWindow extends Alert {

	private String title;
	private String head;
	private ButtonType buttonResult;
	
	public ShowAlertWindow(AlertType type, String title, String head) {
		super(type);
		super.setTitle(title);
		super.setHeaderText(head);
		showAndWait();
	}
	
	public ButtonType getButtonResult() {
		buttonResult = getResult();
		return buttonResult;
	}
}
