package ch.get.view;

import java.net.URL;
import java.util.ResourceBundle;

import javax.jws.soap.SOAPBinding;

import ch.get.MainApp;
import ch.get.model.Preset;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SettingLController implements Initializable {
	
	private static SettingLController inst;
	private Stage thisStage;
	
	@FXML private TableView<Preset> presetTable;
	@FXML private TableColumn<Preset, String> nameColumn;
	@FXML private TableColumn<Preset, Integer> timeColumn;
	
	@FXML private ColorPicker colorPicker;
	@FXML private TextField name;
	@FXML private TextField time;
	
	private MainApp mainApp;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		inst = this;
		
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		timeColumn.setCellValueFactory(cellData -> cellData.getValue().timeProperty().asObject());
		
		presetTable.setRowFactory(row -> new TableRow<Preset>() {
			
		});
	}

	@FXML
	private void addButton()
	{
		Preset preset = new Preset(name.getText(), Integer.parseInt(time.getText()), colorPicker.getValue());
		mainApp.getPresetData().add(preset);
	}
	
	@FXML
	private void delButton()
	{
		int selectIndex = presetTable.getSelectionModel().getSelectedIndex();
		
		if(selectIndex >= 0)
		{
			presetTable.getItems().remove(selectIndex);
		}
	}
	
	@FXML
	private void cancelButton()
	{
		thisStage.close();
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		
		presetTable.setItems(mainApp.getPresetData());
	}

	public static SettingLController getInst() {
		return inst;
	}

	public void setThisStage(Stage thisStage) {
		this.thisStage = thisStage;
	}
}