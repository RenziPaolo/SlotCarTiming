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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import testing.test;
import timing.Driver;
import timing.QualifingT;
import timing.Sensor;

public class QualifingWaiting implements Initializable{
	@FXML private AnchorPane list;
	@FXML private VBox classification;
	
	private Button[] buttons;
	private static QualifingT qualifing;
	private static Pane qualifingPane;
	private static Qualifing qualifingGUI;
	private static testing.test test;
	
	public void back(ActionEvent back) {
		try {
			new MainMenu().getStage().getScene().setRoot(FXMLLoader.load(getClass().getResource("FXML/Race Settings.fxml")));
		} catch (IOException e) {
		}

	}
	
	public QualifingWaiting() {
		if(qualifing ==null) {
			String[] participants = new RaceSettings().getParticipants();
			if (participants == null)
				return;
			ArrayList<Driver> list = new ArrayList<Driver>();
			for (int j = 0; j< participants.length;j++){
				Driver pilota = new Driver(participants[j],j,Arrays.asList(new Data().getColori()).indexOf(new Data().getQualifingLane()),0);
				list.add(pilota);
			}
			
			try {
				Pane qualifingPane = FXMLLoader.load(getClass().getResource("FXML/Qualifing.fxml"));
				// !!!! TOGLI I NUMERI !!!!
				Data.setBackground(qualifingPane,120,500,true);
				QualifingWaiting.qualifingPane = qualifingPane;
				Qualifing qualifingGUI = new Qualifing(qualifingPane);
				QualifingWaiting.qualifingGUI = qualifingGUI;
	
				QualifingT qualifing = new QualifingT(list, qualifingGUI,1);
				QualifingWaiting.qualifing = qualifing;
				Sensor sensor = new Sensor(QualifingWaiting.qualifing,new Data().getMinLapTime());
				qualifingGUI.addSensor(sensor);
//				test test = new test(6,qualifing,sensor,4);
//				QualifingWaiting.test = test;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		
	}
	
	public QualifingT getQualifing() {
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
			participantButton.setPrefWidth(200);
			participantButton.setPrefHeight(30);
			participantButton.setBackground(Background.EMPTY);
			participantButton.setOnAction((e->{
				test.testCorsie(6,4);
				qualifing.setCurrentDriver(qualifing.getPiloti().get(Arrays.asList(buttons).indexOf(e.getSource())));
				qualifingGUI.resetTimer();
				qualifingGUI.getTimer().start();
				qualifingGUI.getSensor().reset();
				new MainMenu().getStage().getScene().setRoot(qualifingPane);
			}));
			
			buttons[i] = participantButton;
			this.list.getChildren().add(participant);
			this.list.getChildren().add(participantButton);
		}
		classification.getChildren().clear();
		int[] classificationID = qualifing.getClassification();
		int position = 0;
		for (int i = 0; i<classificationID.length;i++) {
			if(classificationID[i]!=0) {
				position++;
				HBox riga = new HBox();
				Text pos = new Text();
				pos.setText(position+"  ");
				riga.getChildren().add(pos);
				Text name = new Text();
				name.setText(qualifing.getPiloti().get(classificationID[i]).getNomePilota()+"  ");
				riga.getChildren().add(name);
				Text laptime = new Text();
				laptime.setText(classificationID[i]+"");
				riga.getChildren().add(laptime);
				classification.getChildren().add(riga);
			}
		}	
	}
}
