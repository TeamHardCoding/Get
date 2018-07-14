package ch.get;

import java.util.Iterator;

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
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage; //메인 stage
	
	//Layout 참조
	private BorderPane rootLayout;
	private AnchorPane bgLayout;
	private ImageView imgView;
	private Label hour;
	private Label min;
	private Label sec;
	
	//Controller 참조
	private RootLayoutController rootCont;
	private BackGroundController bgCont;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
		//Layout init
		initRootLayout();
		initBackGround();
		
		//Layout 배치
		rootLayout.setCenter(bgLayout);
		
		//@@get bg param 
		imgView = (ImageView) bgLayout.getChildren().get(0);
		hour = (Label) bgLayout.getChildren().get(1);
		min = (Label) bgLayout.getChildren().get(2);
		sec = (Label) bgLayout.getChildren().get(3);

		/*
		Iterator<Node> itr = bgLayout.getChildren().iterator();
		while(itr.hasNext())
		{
			System.out.println(itr.next());
		}
		*/
		
		imgView.setFitHeight(rootLayout.getHeight());
		imgView.setFitWidth(rootLayout.getWidth());

		//레이아웃 바인딩
		rootLayout.heightProperty().addListener(event -> {
			imgView.setFitHeight(rootLayout.getHeight());
			//hour.setLayoutY(rootLayout.getLayoutX());
			//min.setLayoutY((rootLayout.getLayoutX())+20);
			//sec.setLayoutY((rootLayout.getLayoutX())+35);
		});
		
		rootLayout.widthProperty().addListener(event -> {
			imgView.setFitWidth(rootLayout.getWidth());
		});
		
		//전체화면 리스너
		primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			
			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if((event.getCode() == KeyCode.ENTER) && !primaryStage.isFullScreen())
				{
					primaryStage.setFullScreen(true);
				}
				else
				{
					primaryStage.setFullScreen(false);
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
			
			primaryStage.setTitle("JIU-JITSU Timer");
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
			bgLayout = (AnchorPane) loader.load();
			
			bgCont = BackGroundController.getInst();
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}
	
	//getter
	public Stage getPrimaryStage() {
		return primaryStage;
	}	
}
