package ch.get;

/*
 * Author ch.Get
 */
import java.io.IOException;

import ch.get.model.ClientStatus;
import ch.get.view.RootLayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	private static int clientCount = 0;
	
	private Stage primaryStage;
	private Stage clientStage;
	private MainApp mainApp;
	private BorderPane rootLayout;
	private BorderPane clientLayout;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.mainApp = this;
		
		//initLayout
		initRoot();
//		initClient(); //클라이언트 테스트
	}
	
	public void initClient() {
		
		try {
			clientStage = new Stage();
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/ClientLayout.fxml"));
			setClientCount(ClientStatus.PLUS);
			
			clientLayout = (BorderPane) loader.load();
			Scene scene = new Scene(clientLayout);
			clientStage.setScene(scene);
			clientStage.setTitle("클라이언트");
			clientStage.setResizable(false);
			clientStage.setOnCloseRequest(event -> {
				setClientCount(ClientStatus.MINUS);
			});
			
			clientStage.show();
			RootLayoutController.rcl.printText(getClientCount()+" 번째 클라이언트 소스를 성공적으로 셋업 하였습니다.");
			RootLayoutController.rcl.printText("버튼을 눌러 서버를 동작 하세요. 아래 StartClient 버튼은 클라이언트 를 불러옵니다\n");
		} catch (IOException e) {
		} catch (Exception e) {
		}
	}
	
	public void initRoot() {
		
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/RootLayout.fxml"));
			
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("1차 프로젝트");
			primaryStage.setAlwaysOnTop(false);
			primaryStage.setResizable(false);
			primaryStage.setOnCloseRequest(event -> {
				RootLayoutController.rcl.stopServer();
			});
			primaryStage.show();
			
			RootLayoutController.rcl.setMainApp(mainApp);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setClientCount(ClientStatus value) {
		MainApp.clientCount += value.getState();
	}
	public static int getClientCount() {
		return clientCount;
	}
	public static void main(String[] args) {
		launch(args);
	}
}
