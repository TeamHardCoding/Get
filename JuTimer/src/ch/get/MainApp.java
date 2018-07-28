package ch.get;

import java.util.ArrayList;
import java.util.Iterator;

import ch.get.model.RollingTimer;
import ch.get.view.BackGroundController;
import ch.get.view.RootLayoutController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	private final double MAIN_HEIGHT = 600.0;
	private final double MAIN_WIDTH = 800.0;
	
	private Stage primaryStage; //메인 stage
	
	//TimerInst
	private Thread rollingTimer;
	private Thread breakTimer;
	
	//Layout 참조
	private BorderPane rootLayout;
	private AnchorPane bgLayout;
	private StackPane bgStackLayout;
	private GridPane labelLayout;
	private ImageView imgView;
	private Label rnd;
	private Label sec;
	private Label min;
	
	//Controller 참조
	public static RootLayoutController rootCont;
	public static BackGroundController bgCont;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
		//Layout init
		initRootLayout();
		initBackGround();
		
		//Layout 배치
		rootLayout.setCenter(bgLayout);
		
		//전체화면 리스너
		primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			
			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				switch(event.getCode())
				{
					case SPACE : setFullScreen();
					case ENTER : startRollingTimer();
					default : break;
				}
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void initRootLayout() {
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			rootCont = RootLayoutController.getInst();
			rootCont.setMainApp(this);
			
			primaryStage.setTitle("JIU-JITSU Timer");
			primaryStage.setResizable(true);
			primaryStage.setOnCloseRequest(event -> Platform.exit());
			primaryStage.show();
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}
	
	public void initBackGround() {
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/BackGround.fxml"));
			bgLayout = (AnchorPane) loader.load(); //컨트롤러 로드
			bgCont = BackGroundController.getInst();
			bgCont.setMainApp(this);
			
			bgStackLayout = (StackPane) bgLayout.getChildren().get(0);
			imgView = (ImageView) bgStackLayout.getChildren().get(0);
			labelLayout = (GridPane) bgStackLayout.getChildren().get(1);
			rnd = (Label) labelLayout.getChildren().get(0);
			sec = (Label) labelLayout.getChildren().get(1);
			min = (Label) labelLayout.getChildren().get(2);
				
			imgView.setFitHeight(rootLayout.getHeight());
			imgView.setFitWidth(rootLayout.getWidth());
			
			//레이아웃 바인딩
			rootLayout.heightProperty().addListener(event -> {
				imgView.setFitHeight(rootLayout.getHeight());
				
				if(primaryStage.getHeight() <= MAIN_HEIGHT)
				{
					primaryStage.setHeight(MAIN_HEIGHT);
				}
			});
			
			rootLayout.widthProperty().addListener(event -> {
				imgView.setFitWidth(rootLayout.getWidth());
				
				if(primaryStage.getWidth() <= MAIN_WIDTH)
				{
					primaryStage.setWidth(MAIN_WIDTH);
				}
			});
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}
	
	public Boolean startRollingTimer()
	{
		rollingTimer = new Thread(new RollingTimer(rnd, min, sec));
		rollingTimer.setName("RollingTimer");
		rollingTimer.setDaemon(true);
		rollingTimer.start();
		
		return true;
	}
	
	public void setFullScreen()
	{
		primaryStage.setFullScreen(true);
	}
	
	//getter
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public BorderPane getRootLayout() {
		return rootLayout;
	}
}
