package ch.get.view;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import ch.get.MainApp;
import ch.get.model.Result;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TableViewLayoutController implements Initializable{
	
	private MainApp mainApp;

	@FXML private TableView<Result> resultTable; //Table	
	
	@FXML private TableColumn<Result, String> houly; //시급 
	@FXML private TableColumn<Result, String> monthly; //월급
	@FXML private TableColumn<Result, String> monthlyNow; //현재 날짜 까지의 월급
	@FXML private TableColumn<Result, LocalDate> startDate; //시작 날짜
	@FXML private TableColumn<Result, LocalDate> purposeDate; // 목표 날짜
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		houly.setCellValueFactory(cellData -> cellData.getValue().hourlyProperty());
		monthly.setCellValueFactory(cellData -> cellData.getValue().monthlyProperty());
		monthlyNow.setCellValueFactory(cellData -> cellData.getValue().monthlyNowProperty());
		startDate.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());
		purposeDate.setCellValueFactory(cellData -> cellData.getValue().purposeProperty());
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		
		resultTable.setItems(mainApp.getResult()); //메인앱 참조
	}
}
