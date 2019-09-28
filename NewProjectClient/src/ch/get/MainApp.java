package ch.get;

import ch.get.model.ServerStat;
import ch.get.util.ShowAlertWindow;
/*
 * Client
 */
import ch.get.view.ClientInfoSettingController;
import ch.get.view.ClientLayoutController;
import ch.get.view.LoginLayoutController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;

	private BorderPane rootLayout;
	private GridPane settingLayout;
	private GridPane loginLayout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;

		primaryStage.setResizable(false);
		primaryStage.setOnCloseRequest(event -> {
			System.exit(0);
		});

		// init
		boolean op = showLoginWindow();

		if (op) {
			initRoot();
		}
	}

	public boolean showLoginWindow() {

		try {
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/LoginLayout.fxml"));
			loginLayout = (GridPane) loader.load();
			Scene scene = new Scene(loginLayout);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("로그인");
			stage.setResizable(false);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.setOnCloseRequest(event -> {
				System.exit(0);
			});

			LoginLayoutController.loginCont.setMainApp(this);
			LoginLayoutController.loginCont.setLoginStage(stage);
			ButtonType btOp = new ShowAlertWindow(AlertType.INFORMATION, "계정 생성", "처음 접속 하신다면 관리자에게 계정을 문의 하세요.")
					.getButtonResult();

			if (btOp == ButtonType.OK) {
				stage.showAndWait();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return LoginLayoutController.loginCont.isOkClicked();
	}

	public boolean showSettingWindow() {

		try {
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/ClientInfoSetting.fxml"));
			settingLayout = (GridPane) loader.load();
			Scene scene = new Scene(settingLayout);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("클라이언트 설정");
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(primaryStage);
			ClientInfoSettingController.inst.setMainApp(this);
			ClientInfoSettingController.inst.setSettingStage(stage);

			stage.showAndWait();
			return ClientInfoSettingController.inst.isOkClicked();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void initRoot() {

		try {
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/ClientLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Client");
			primaryStage.show();

			ClientLayoutController.cliContInstance.setMainApp(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public BorderPane getRootLayout() {
		return rootLayout;
	}

	public GridPane getSettingLayout() {
		return settingLayout;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}
}