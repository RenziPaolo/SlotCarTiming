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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import testing.test;
import timing.Corsia;
import timing.Pilota;
import timing.Qualifica;
import timing.Sensore;

public class QualifingWaiting implements Initializable{
	@FXML private AnchorPane list;
	@FXML private VBox classification;
	
	private Button[] buttons;
	private static Qualifica qualifing;
	private Pane qualifingPane;
	private testing.test test;
	
	public void back(ActionEvent back) {
		try {
			new MainMenu().getStage().getScene().setRoot(FXMLLoader.load(getClass().getResource("FXML/Race Settings.fxml")));
		} catch (IOException e) {
		}

	}
	
	public QualifingWaiting() {
		String[] participants = new RaceSettings().getParticipants();
		Dati data = new Dati();
		ArrayList<Pilota> list = new ArrayList<Pilota>();
		for (int j = 0; j< participants.length;j++){
			Corsia corisa = new Corsia(j,data.getQualifingLane());
			Pilota pilota = new Pilota(participants[j],corisa,0);
			list.add(pilota);
		}
		if (list.size()<6) {
			for (int j = list.size(); j<= 6;j++){
				Corsia corisa = new Corsia(j,data.getQualifingLane());
				Pilota pilota = new Pilota("test",corisa,0);
				list.add(pilota);
				}
		}
		
		try {
			Pane qualifingPane = FXMLLoader.load(getClass().getResource("FXML/Qualifing.fxml"));
			Dati.setBackground(qualifingPane);
			this.qualifingPane = qualifingPane;
			Qualifing qualifingGUI = new Qualifing(qualifingPane);
			Qualifica qualifing = new Qualifica(list, qualifingGUI);
			this.qualifing = qualifing;
			Sensore sensor = new Sensore(this.qualifing,new Dati().getMinLapTime());
			qualifingGUI.addSensor(sensor);
			test test = new test(6,qualifing,sensor);
			this.test = test;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public Qualifica getQualifing() {
		return qualifing;
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
			participantButton.setPrefHeight(50);
//			participantButton.setBackground(Background.EMPTY);
//			participantButton.setPickOnBounds(true);
			participantButton.setOnAction((e->{
				test.testCorsie(6);
				qualifing.setCurrentDriver(qualifing.getPiloti().get(Arrays.asList(buttons).indexOf(e.getSource())));
				new MainMenu().getStage().getScene().setRoot(this.qualifingPane);
				
			}));
			
			buttons[i] = participantButton;
			this.list.getChildren().add(participantButton);
			this.list.getChildren().add(participant);
		}
		
	}
}
