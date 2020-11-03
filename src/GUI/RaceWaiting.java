package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;

public class RaceWaiting implements Initializable{
	@FXML private ChoiceBox<Integer> Heat;
	@FXML private ChoiceBox<Integer> Manche;
	@FXML private ScrollPane Classification;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void back(ActionEvent indietro) {
		try {
			new MainMenu().getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("FXML/Race Settings.fxml"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
