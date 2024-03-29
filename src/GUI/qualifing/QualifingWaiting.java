package GUI.qualifing;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import GUI.MainMenu;
import GUI.race.RaceSettings;
import GUI.utilities.Data;
import GUI.utilities.Utilities;
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
	
	public void back(ActionEvent back) {
		try {
			new MainMenu().getStage().getScene().setRoot(FXMLLoader.load(getClass().getResource("FXML/Race Settings.fxml")));
		} catch (IOException e) {
		}

	}
	
	public QualifingWaiting() {
		setQualy();

		
	}
	public void setQualy() {
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
				Utilities.setBackground(qualifingPane,120,500,true);
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
				qualifing.setCurrentDriver(qualifing.getPiloti().get(Arrays.asList(buttons).indexOf(e.getSource())));
				qualifingGUI.resetTimer();
				qualifingGUI.getTimer().restart();
				qualifingGUI.start();
				new MainMenu().getStage().getScene().setRoot(qualifingPane);
			}));
			
			buttons[i] = participantButton;
			this.list.getChildren().add(participant);
			this.list.getChildren().add(participantButton);
		}
		classification.getChildren().clear();
		if(qualifing == null) {
			setQualy();
		}
		int[] classificationID = qualifing.getClassification();
		float[] classificationLap = qualifing.getClassificationLap();
		int position = 0;
		for (int i = 0; i<classificationID.length;i++) {
			if(classificationLap[i]!=0.0) {
				position++;
				HBox riga = new HBox();
				Text pos = new Text();
				pos.setText(position+")  ");
				riga.getChildren().add(pos);
				Text name = new Text();
				name.setText(qualifing.getPiloti().get(classificationID[i]).getNomePilota()+"  ");
				riga.getChildren().add(name);
				Text laptime = new Text();
				laptime.setText(classificationLap[i]+"");
				riga.getChildren().add(laptime);
				pos.setFont(new Font(30));
				name.setFont(new Font(30));
				laptime.setFont(new Font(30));
				classification.getChildren().add(riga);
			}
		}	
	}
}
