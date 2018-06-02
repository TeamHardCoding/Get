package ch.get.view;

import java.net.URL;
import java.util.ResourceBundle;

import ch.get.MainApp;
import ch.get.model.Result;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SubmitLayoutController implements Initializable{
	
	@FXML private DatePicker startDate;
	@FXML private DatePicker purposeDate;
	@FXML private TextField hourly;
	@FXML private TextField totalWorks;
	@FXML private TextField workTime;
	
	private MainApp mainApp;
	private Stage thisStage;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}
	
	@FXML
	private void handleOkayButton()
	{
		mainApp.getResult().add(new Result(hourly.getText(), totalWorks.getText(), workTime.getText(), startDate.getValue(), purposeDate.getValue()));
		handleCancelButton(); 
	}
	
	@FXML
	private void handleCancelButton()
	{
		thisStage.close();
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	/*
	 *  @param subStage
	 */
	public void setThisStage(Stage thisStage) {
		this.thisStage = thisStage;
	}
}
