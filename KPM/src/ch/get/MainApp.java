package ch.get;

import java.io.File;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import ch.get.model.LogListWrapper;
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
	public final static String APP_TITLE = "KPM ������";
	
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
		
		//������Ʈ ��ġ
		rootLayout.setCenter(viewLayout);
		rootLayout.setTop(timerLayout);
		rootLayout.setBottom(toolBarLayout);
		mainStage.setResizable(false);
		
		//��Ʈ�ѷ��� �ν��Ͻ� ��ȯ
		timerCont.setToolBar(toolsCont.getToolBar());
		timerCont.setSavBtn(toolsCont.getSavBtn());
		timerCont.setListView(viewCont.getListView()); //listview�Ѹ���
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
			mainStage.setTitle(APP_TITLE);
			
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
	
	public void initTimerView() //����ð� �� �Է°� �Է� �κ�
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
			
			//�������� ����
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
	
	public void initToolbar() //����
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
	
	public File getLogFilePath()
	{
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		String filePath = prefs.get("filePath", null);
		
		if(filePath != null)
		{
			return new File(filePath);
		}
		else
		{
			return null;
		}
	}
	
	public void setLogFilePath(File file)
	{
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		if(file != null)
		{
			prefs.put("filePath", file.getPath());
			
			//���α׷� Ÿ��Ʋ�� ������Ʈ
			mainStage.setTitle(APP_TITLE+" - "+file.getName());
		}
		else
		{
			prefs.remove("filePath");
			
			mainStage.setTitle(APP_TITLE);
		}
	}
	
	public void loadLogDataFromFile(File file)
	{
		try
		{
			JAXBContext context = JAXBContext.newInstance(LogListWrapper.class);
			Unmarshaller um = context.createUnmarshaller();
			
			//���Ϸκ��� XML�� ���� ���� �� �������Ѵ�.
			LogListWrapper wrapper = (LogListWrapper)um.unmarshal(file);
			
			/*
			 * listview �Ǵ� tableview�� ������ �Է� �ؾ���
			 */
			//wrapper.getLog();
			
			//���� ��θ� ������Ʈ���� �����Ѵ�.
			setLogFilePath(file);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
}
