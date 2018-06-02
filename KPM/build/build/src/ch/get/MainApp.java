package ch.get;

import ch.get.view.InputValueController;
import ch.get.view.RootLayoutController;
import ch.get.view.TImerViewController;
import ch.get.view.ToolsController;
import ch.get.view.ViewListController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage mainStage;
	
	private BorderPane rootLayout;
	private AnchorPane viewLayout;
	private GridPane timerLayout;
	private GridPane inputValue;
	private GridPane toolBarLayout;
	
	private RootLayoutController rootCont;
	private ViewListController viewCont;
	private TImerViewController timerCont;
	private InputValueController inputCont;
	private ToolsController toolsCont;
	
	@Override
	public void start(Stage primaryStage) {
		mainStage = primaryStage;
		
		//Main
		initRootLayout();		
		//ViewList
		initViewList();	
		//TimerView
		initTimerView();
		//Toolbar
		initToolbar();
		
		//컴포넌트 배치
		rootLayout.setCenter(viewLayout);
		rootLayout.setTop(timerLayout);
		rootLayout.setBottom(toolBarLayout);
		mainStage.setResizable(false);
		
		//컨트롤러간 인스턴스 교환
		timerCont.setToolBar(toolsCont.getToolBar());
		timerCont.setSavBtn(toolsCont.getSavBtn());
		timerCont.setListView(viewCont.getListView()); //listview뿌리기
		toolsCont.setListView(viewCont.getListView());
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void initRootLayout()
	{	
		try 
		{	
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane)loader.load();
			
			Scene scene = new Scene(rootLayout);
			mainStage.setScene(scene);
			mainStage.show();
			mainStage.setTitle("KPM 측정기 ch.Get");
			
			rootCont = loader.getController();
			rootCont.setMainApp(this);
		} 
		catch (Exception e) 
		{
			e.getStackTrace();
		}
	}
	
	public void initViewList()
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ViewList.fxml"));
			viewLayout = (AnchorPane)loader.load();
			  
			viewCont = loader.getController();
			viewCont.setMainApp(this);
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}
	
	public void initTimerView() //경과시간 및 입력값 입력 부분
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/TimerView.fxml"));
			timerLayout = (GridPane)loader.load();
			
			timerCont = loader.getController();
			timerCont.setMainApp(this);
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}
	
	public void initInputValue()
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/InputValue.fxml"));
			inputValue = (GridPane)loader.load();
			
			//스테이지 생성
			Stage stage = new Stage();
			stage.setTitle("Go! KPM");
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(mainStage);
			
			Scene scene = new Scene(inputValue);
			stage.setScene(scene);
			
			inputCont = loader.getController();
			inputCont.setThisStage(stage);
			
			inputCont.setTimerCont(timerCont);
			
			stage.setResizable(false);
			stage.showAndWait();
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}
	
	public void initToolbar() //툴바
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Tools.fxml"));
			toolBarLayout = (GridPane)loader.load();
			
			toolsCont = loader.getController();
			toolsCont.setMainApp(this);
			toolsCont.setMainApp_Util();
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}
	
	//getter
	public Stage getMainStage() {
		return mainStage;
	}
}
