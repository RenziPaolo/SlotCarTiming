package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import timing.Corsia;

public class QualifingWaiting implements Initializable{
	@FXML private AnchorPane list;
	@FXML private VBox classification;
	
	private Button[] buttons;
	
	public void back(ActionEvent back) {
		try {
			new MainMenu().getStage().getScene().setRoot(FXMLLoader.load(getClass().getResource("FXML/Race Settings.fxml")));
		} catch (IOException e) {
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		String[] participants = new RaceSettings().getParticipants();
		buttons = new Button[participants.length];
		for (int i = 0; i<participants.length ;i++) {
			Text participant = new Text();
			participant.setText(participants[i]);
			participant.setFont(new Font(20));
			participant.setY(i*30);
			
			Button participantButton = new Button();
			participantButton.setLayoutY(i*30);
			participantButton.setBackground(Background.EMPTY);
			participantButton.setPickOnBounds(true);
			participantButton.setOnAction((e->{
				try {
					new MainMenu().getStage().getScene().setRoot(FXMLLoader.load(getClass().getResource("FXML/Qualifing.fxml")));
				} catch (IOException e1) {
				}
				
			}));
			
			buttons[i] = participantButton;
			list.getChildren().add(participantButton);
			list.getChildren().add(participant);
		}
		
	}
}
