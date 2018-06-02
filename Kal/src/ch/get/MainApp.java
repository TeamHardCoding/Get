package ch.get;

import ch.get.model.Result;
import ch.get.view.RootLayoutController;
import ch.get.view.SubmitLayoutController;
import ch.get.view.TableViewLayoutController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {
	//private Handler handler;
	
	//List
	private ObservableList<Result> result = FXCollections.observableArrayList();
	
	//LayoutPane
	private Stage mainStage;
	private BorderPane rootLayout;
	private AnchorPane tableViewLayout;
	private GridPane submitLayout;
	
	//controller
	private RootLayoutController rootCont; 
	private TableViewLayoutController tableCont;
	private SubmitLayoutController subCont;
	
	public MainApp() {
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * @param result 추가를 위란 반환
	 */
	public ObservableList<Result> getResult() {
		return result;
	}

	@Override
	public void start(Stage primaryStage) {
		mainStage = primaryStage;
		
		//Layout_init
		initRootLayout();
		initTableViewLayout();
		
		//Set_Layout
		rootLayout.setCenter(tableViewLayout);
		
		//Layout_Binding
		rootLayout.heightProperty().addListener(event->{
			tableViewLayout.setPrefHeight(rootLayout.getHeight());
		});
		
		rootLayout.widthProperty().addListener(event->{
				tableViewLayout.setPrefWidth(rootLayout.getWidth());
		});
		//End_Layout_Binding
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void initRootLayout()
	{
		try
		{
			//메인 스테이지
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane)loader.load();
			
			Scene scene = new Scene(rootLayout);
			mainStage.setScene(scene);
			mainStage.setTitle("알바비 계산기");
			mainStage.show();
			
			rootCont = loader.getController();
			rootCont.setMainApp(this);
			rootCont.setPrimageStage(mainStage);
		}
		catch(Exception e) 
		{
			e.getStackTrace();
		}
	}
	
	public void initTableViewLayout()
	{
		try
		{
			//Table View
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/TableViewLayout.fxml"));
			tableViewLayout = (AnchorPane)loader.load();
			
			tableCont = loader.getController();
			tableCont.setMainApp(this);
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}
	
	public void addResultValue()
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SubmitLayout.fxml"));
			submitLayout = loader.load(); //Pane 인스턴스
			subCont = loader.getController();
			
			Stage stage = new Stage();
			stage.setScene(new Scene(submitLayout));
			stage.setTitle("SubmitTable");
			stage.setResizable(false);	
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(mainStage);
			
			stage.setX(mainStage.getX()+mainStage.getWidth()*0.3);
			stage.setY(mainStage.getY()+mainStage.getHeight()*0.3);
			
			subCont.setMainApp(this);
			subCont.setThisStage(stage);
			stage.showAndWait();
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}
}
