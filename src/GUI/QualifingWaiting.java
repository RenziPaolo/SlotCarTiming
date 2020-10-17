package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import testing.test;
import timing.Corsia;
import timing.Pilota;
import timing.Prove;
import timing.Qualifica;
import timing.Sensore;

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
			participantButton.setLayoutY((i*30)-20);
			participantButton.setPrefWidth(100);
//			participantButton.setBackground(Background.EMPTY);
//			participantButton.setPickOnBounds(true);
			participantButton.setOnAction((e->{
				try {
					Pane qualifingPane = FXMLLoader.load(getClass().getResource("FXML/Qualifing.fxml"));
					Dati dati = new Dati();
					HashMap<Integer,Pilota> mappa = new HashMap<Integer,Pilota>();
					for (int j = 0; j<participants.length ;j++){
						Corsia corisa = new Corsia(j,dati.getQualifingLane());
						Pilota pilota = new Pilota("test",corisa);
						mappa.put(j, pilota);
					}
					Dati.setBackground(qualifingPane);
					Qualifing qualifing = new Qualifing(qualifingPane);
					Sensore sensor = new Sensore(new Qualifica(mappa, qualifing),dati.getMinLapTime());
					qualifing.addSensor(sensor);
					test test =new test(6,new Prove(mappa, qualifing),sensor);	
					test.testCorsie(6);
							
					new MainMenu().getStage().getScene().setRoot(qualifingPane);
				} catch (IOException e1) {
				}
				
			}));
			
			buttons[i] = participantButton;
			list.getChildren().add(participantButton);
			list.getChildren().add(participant);
		}
		
	}
}
