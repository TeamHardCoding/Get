package ch.get.view;

import java.net.URL;
import java.util.ResourceBundle;
import ch.get.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InputValueController implements Initializable{
	@FXML private TextField endKill;
	
	private MainApp mainApp;
	private Stage thisStage;
	
	private TImerViewController timerCont;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}

	@FXML
	private void subMitStage()
	{
		if(timerCont.isInputVaild(endKill.getText()))
		{
			try
			{
				timerCont.viewResult(Integer.parseInt(endKill.getText()));
				thisStage.close();
			}
			catch(Exception e)
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("마지막 킬 값이 시작 킬값 보다 작을 수 없습니다.");
				alert.showAndWait();
			}
		}
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void setThisStage(Stage thisStage) {
		this.thisStage = thisStage;
	}
	
	public void setTimerCont(TImerViewController timerCont) {
		this.timerCont = timerCont;
	}
}
