package ch.get;

/*
 * Server Author ch.Get
 */
import java.io.IOException;

import ch.get.view.RootLayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	private MainApp mainApp;
	private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.mainApp = this;

		// initLayout
		initRoot();

//		String toTemp[] = "join:msg".split(":");
//		for (String string : toTemp) {
//			System.out.println(string);
//		}

		/*
		 * param join msg
		 */
	}

	public void initRoot() {

		try {
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/RootLayout.fxml"));

			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);

			primaryStage.setScene(scene);
			primaryStage.setTitle("Server");
			primaryStage.setAlwaysOnTop(false);
			primaryStage.setResizable(false);
			primaryStage.setOnCloseRequest(event -> {
				RootLayoutController.rcl.exitProgramHandler();
			});
			primaryStage.show();

			RootLayoutController.rcl.setMainApp(mainApp);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
