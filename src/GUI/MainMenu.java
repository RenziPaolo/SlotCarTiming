package GUI;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainMenu extends Application{
	private Pane window;
	static private Stage stage;
	
	public Stage getStage() {
		return stage;
	}
	
	public void Gara(ActionEvent gara) throws Exception {
		Pane raceSettings = FXMLLoader.load(getClass().getResource("FXML/Race Settings.fxml"));
		stage.setScene(new Scene(raceSettings));
	}
	
	public void Prove(ActionEvent prove){
		Pane practiceSettings = new AnchorPane();
		try {
			practiceSettings = FXMLLoader.load(getClass().getResource("FXML/Practice Settings.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		stage.getScene().setRoot(practiceSettings);
	}
	
	public void Impostazioni(ActionEvent Impostazioni){
		Pane settings = new AnchorPane();
		try {
			settings = FXMLLoader.load(getClass().getResource("FXML/Settings.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		stage.getScene().setRoot(settings);
	}
	
	public void exit(ActionEvent exit) throws Exception {
		System.exit(0);
	}

	public void draw() {
		launch();
		
	}

	@Override
	public void start(Stage stage) throws Exception {
        window = FXMLLoader.load(getClass().getResource("FXML/Main Menu.fxml"));
        Scene primaryScene = new Scene(window);
        stage.setTitle("SlotCar Timing");
        stage.setScene(primaryScene);
        stage.show();
        this.stage = stage;
	}
	
}
