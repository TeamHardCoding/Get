package ch.get;

import ch.get.view.ServerRootLayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ServerMain extends Application {

	private Stage primaryStage;

	private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;

		// Layout init
		initRoot();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void initRoot() {

		try {
			FXMLLoader loader = new FXMLLoader(ServerMain.class.getResource("view/ServerRootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			Scene scene = new Scene(rootLayout); // 씬 생성
			primaryStage.setScene(scene);
			primaryStage.setTitle("ServerRoot");
			primaryStage.setResizable(false);
			primaryStage.show();

			ServerRootLayoutController.sevRootCont.setMainApp(this); // MainApp 접근을 위해 setting
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
