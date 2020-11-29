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
import timing.ParallelInterfaceButton;
import timing.Pilota;
import timing.Qualifica;
import timing.Sensore;

public class QualifingWaiting implements Initializable{
	@FXML private AnchorPane list;
	@FXML private VBox classification;
	
	private Button[] buttons;
	private static Qualifica qualifing;
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
			ArrayList<Pilota> list = new ArrayList<Pilota>();
			for (int j = 0; j< participants.length;j++){
				Pilota pilota = new Pilota(participants[j],(float)j,Arrays.asList(new Dati().getColori()).indexOf(new Dati().getQualifingLane()),0);
				list.add(pilota);
			}
			
			try {
				Pane qualifingPane = FXMLLoader.load(getClass().getResource("FXML/Qualifing.fxml"));
				Dati.setBackground(qualifingPane,120,500);
				QualifingWaiting.qualifingPane = qualifingPane;
				Qualifing qualifingGUI = new Qualifing(qualifingPane);
				QualifingWaiting.qualifingGUI = qualifingGUI;
	
				Qualifica qualifing = new Qualifica(list, qualifingGUI,1);
				QualifingWaiting.qualifing = qualifing;
				Sensore sensor = new Sensore(QualifingWaiting.qualifing,new Dati().getMinLapTime());
				qualifingGUI.addSensor(sensor);
				try {
					ParallelInterfaceButton parInterface = new ParallelInterfaceButton(sensor);
				} catch (Error | Exception error) {
					System.out.println("ERROR INITIALIZING TRACK INTERFACE");
		        }
				test test = new test(6,qualifing,sensor,4);
				QualifingWaiting.test = test;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
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
		Float[][][] classificationfloat = qualifing.getClassification();
		int position = 0;
		for (int i = 0; i<classificationfloat.length;i++) {
			if(classificationfloat[i][1][0]!=0) {
				position++;
				HBox riga = new HBox();
				Text pos = new Text();
				pos.setText(position+"  ");
				riga.getChildren().add(pos);
				Text name = new Text();
				name.setText(qualifing.getPiloti().get((int)(float)classificationfloat[i][0][0]).getNomePilota()+"  ");
				riga.getChildren().add(name);
				Text laptime = new Text();
				laptime.setText(classificationfloat[i][1]+"");
				riga.getChildren().add(laptime);
				classification.getChildren().add(riga);
			}
		}	
	}
}
