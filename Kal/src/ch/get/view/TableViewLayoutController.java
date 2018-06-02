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
	
	@FXML private TableColumn<Result, String> houly; //�ñ� 
	@FXML private TableColumn<Result, String> monthly; //����
	@FXML private TableColumn<Result, String> monthlyNow; //���� ��¥ ������ ����
	@FXML private TableColumn<Result, LocalDate> startDate; //���� ��¥
	@FXML private TableColumn<Result, LocalDate> purposeDate; // ��ǥ ��¥
	
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
		
		resultTable.setItems(mainApp.getResult()); //���ξ� ����
	}
}
