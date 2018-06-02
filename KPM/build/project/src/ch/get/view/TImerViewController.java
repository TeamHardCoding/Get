package ch.get.view;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.ResourceBundle;

import ch.get.MainApp;
import ch.get.model.Timer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Alert.AlertType;

public class TImerViewController implements Initializable{

	@FXML Label hour;
	@FXML Label minute;
	@FXML Label second;
	@FXML TextField startKill;
	@FXML TextField myPower;	
	@FXML Button startBtn;
	
	private Integer h, m, s;
	private Integer cnt_h, cnt_m, cnt_s;
	private Integer srtKill, endKill, kpm, kpmTime;
	private String errorMessage;
	private String hhmmss;
	
	private Thread startTimer;
	private Timer timerInst;
	private ArrayList<Label> labelTemp;
	
	private ListView<String> listView;
	private MainApp mainApp;
	private ObservableList<String> listItems;
	private ToolBar toolBar;
	private Button savBtn;
	
	private LocalTime time;
	DateTimeFormatter dtf;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		timerInst = new Timer(this);
		listItems = FXCollections.observableArrayList();
		dtf = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
		
		cnt_s = 0;
		cnt_m = 0;
		cnt_h = 0;
		endKill = 0;
		srtKill = 0;
		kpm = 0;
		kpmTime = 0;
		
		labelTemp = new ArrayList<Label>();

		/* -- Label list -- */		
		labelTemp.add(hour);
		labelTemp.add(minute);
		labelTemp.add(second);
		/* -- Label list -- */
		
		//컴포넌트 
		timerInst.setTimerLabel(labelTemp);
	}
	
	public void startInfoView()
	{	
		try
		{
			time = LocalTime.now();
			hhmmss = time.format(DateTimeFormatter.ofPattern("hh:mm:ss"));
			
			myPower.setDisable(true);
			startKill.setDisable(true);
		
			srtKill = Integer.parseInt(startKill.getText());
			h = time.getHour();
			m = time.getMinute();
			s = time.getSecond();
		
			Platform.runLater(new Runnable() {		
				@Override
				public void run() {
					// TODO Auto-generated method stub
					startBtn.setText("중지");
					listItems.add("(Start) :: "+hhmmss+" 시작킬 : "+startKill.getText());
					listView.setItems(listItems);
				}
			});
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}
	
	public void setOnCompo(ArrayList<Integer> temp)
	{
		if(!temp.isEmpty())
		{
			mainApp.initInputValue();
			
			cnt_s  = temp.get(0);
			cnt_m = temp.get(1);
			cnt_h = temp.get(2);
		}

		startBtn.setText("시작");
		startKill.setText("");
		
		startKill.setDisable(false);
		myPower.setDisable(false);
		
		hour.setText("0");
		minute.setText("0");
		second.setText("0");
	}
	
	public void viewResult(Integer temp) throws Exception
	{
		endKill = temp;
		if(endKill-srtKill <= 0)
		{
			throw new Exception();
		}
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				kpmTime = (cnt_h*60)+(cnt_m);
				kpm = (endKill - srtKill)/kpmTime;
				
				listItems.add("(Stop) :: 경과 : "+cnt_h+":"+cnt_m+":"+cnt_s+" 분당킬 : "+kpm+"+@");
				listView.setItems(listItems);
			}
		});
	}
	
	@FXML
	private void startBtnHandle()
	{	
		if(isInputVaild(startKill.getText()))
		{
			if(timerInst.getTimerState())
			{
				//toolBar.setDisable(true);
				savBtn.setDisable(true);
				startTimer = new Thread(timerInst);
				startTimer.setDaemon(true);
				startTimer.start();	
			}
			else if(!timerInst.getTimerState())
			{
				//toolBar.setDisable(false);
				savBtn.setDisable(false);
				timerInst.stopTimer(); // 타이머 멈추고
			}
		}
		else
		{
			return;
		}
	}

	public Boolean isInputVaild(String temp)
	{
		errorMessage = "";
		
		if(temp.length() == 0)
		{
			errorMessage += "값을 입력 해주세요.";
		}
		else
		{
			try
			{
				Integer.parseInt(temp);
			}
			catch(Exception e)
			{
				errorMessage += "정수(양수) 형태의 숫자만 입력 가능합니다.";
			}
		}
		
		if(errorMessage.length() == 0)
		{
			errorMessage = null;
			return true;
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setResizable(true);
			alert.setTitle("InputVaild error");
			alert.setHeaderText("입력 값을 확인 해주세요.");
			alert.setContentText(errorMessage);
			alert.showAndWait();
		}
		
		return false;
	}
	
	public Boolean minTimeVaild(Integer temp)
	{
		if(temp < 3)
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("책정 시간 가이드");
			alert.setHeaderText("책정 시간 부족");
			alert.setContentText("정확한 책정을 위해 3분 미만은 책정 할 수 없습니다.");
			alert.showAndWait();
			
			listItems.add("(User_Stop) :: 책정 불가 3분 미만 정지");
			listView.setItems(listItems);
			
			return false;
		}	
		
		return true;
	}
	
	public Boolean getTimerState()
	{
		return timerInst.getTimerState();
	}

	public void setToolBar(ToolBar toolBar) {
		this.toolBar = toolBar;
	}

	public void setListView(ListView<String> listView) {
		this.listView = listView;
	}
	
	public void setSavBtn(Button savBtn) {
		this.savBtn = savBtn;
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}