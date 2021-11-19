package GUI.qualifing;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import GUI.Event;
import GUI.MainMenu;
import GUI.race.RaceSettings;
import GUI.utilities.Data;
import GUI.utilities.MyTimer;
import GUI.utilities.Rele;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import timing.Lane;
import timing.Sensor;

public class Qualifing implements Event,Initializable{
	@FXML private Text rightTimer;
	@FXML private Button start;
	@FXML private Button stop;
	
	//private static ScrollPane classification;
	private static MyTimer timer;
	private static Text[] currentCorsie;
	private static Text[] bestCorsie;
	private static Text[] numberCorsie;
	private static Sensor sensor;
	private Rele r = new Rele();
	
	public Sensor getSensor() {
		return sensor;
	}
	
	public MyTimer getTimer() {
		return timer;
	}
	
	@Override
	public void update(Lane lane) {
		Data data = new Data();
		currentCorsie[data.getCodeQualyLane()].setText(String.format("%.3f",lane.getUltimoGiro()));
		bestCorsie[data.getCodeQualyLane()].setText(String.format("%.3f",lane.getGiroVeloce()));
		numberCorsie[data.getCodeQualyLane()].setText(lane.getNumeroDiGiri()+"");
		//setClassification();
	}
	/*
	private void setClassification() {
		VBox classification = new VBox();
		classification.getChildren().clear();
		int[] classificationID = new QualifingWaiting().getQualifing().getClassification();
		int position = 0;
		for (int i = 0; i<classificationID.length;i++) {
			if(classificationID[i]!=0) {
				position++;
				HBox riga = new HBox();
				Text pos = new Text();
				pos.setText(position+"  ");
				riga.getChildren().add(pos);
				Text name = new Text();
				name.setText(new QualifingWaiting().getQualifing().getPiloti().get((int)(float)classificationID[i]).getNomePilota()+"  ");
				riga.getChildren().add(name);
				Text laptime = new Text();
				laptime.setText(classificationID[i]+"");
				riga.getChildren().add(laptime);
				classification.getChildren().add(riga);
			}
		}
		Qualifing.classification.setContent(classification);
	}
	*/
	
	public Qualifing() {}
	
	public Qualifing(Pane current) {
		int numCorsie = new Data().getNumCorsie();
		currentCorsie = new Text[numCorsie];
		bestCorsie = new Text[numCorsie];
		numberCorsie = new Text[numCorsie];
		for (int i = 0; i<numCorsie;i++) {
			Text currentTesto = new Text();
			currentTesto.setLayoutX(100);
			currentTesto.setLayoutY(((i+1)*120)-40);
			currentTesto.setFont(Font.font(new Data().getFont(),FontWeight.BOLD,(double)90));
			currentCorsie[i] = currentTesto;
			current.getChildren().add(currentTesto);
			
			Text bestTesto = new Text();
			bestTesto.setLayoutX(100);
			bestTesto.setLayoutY(((i+1)*120)-20);
			bestTesto.setFont(Font.font(new Data().getFont(),FontWeight.BOLD,(double)25));
			bestCorsie[i] = bestTesto;
			current.getChildren().add(bestTesto);
			
			Text numberTesto = new Text();
			numberTesto.setLayoutX(280);
			numberTesto.setLayoutY(((i+1)*120)-20);
			numberTesto.setFont(Font.font(new Data().getFont(),FontWeight.BOLD,(double)25));
			numberCorsie[i] = numberTesto;
			current.getChildren().add(numberTesto);
		}/*
		classification = new ScrollPane();
		classification.setPrefWidth(280);
		classification.setPrefHeight(600);
		classification.setLayoutX(800);
		classification.setLayoutY(80);
		current.getChildren().add(classification);
		*/
		new MainMenu().getStage().getScene().setRoot(current);
	}
	
	public void addSensor(Sensor sensor) {
		Qualifing.sensor = sensor;

	}
	
	public void start() {
		sensor.Start();
		r.start();
		timer.restart();
	}
	
	@Override
	public void stop(ActionEvent e) {
		sensor.Stop();
		r.stop();
		timer.stop();
		stop.setVisible(false);
		start.setVisible(true);
	}

	@Override
	public void start(ActionEvent e) {
		sensor.Start();
		r.start();
		timer.restart();
		stop.setVisible(true);
		start.setVisible(false);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		timer = new MyTimer(new RaceSettings().getQualyduration()*60,rightTimer,this);
	}
	
	public void back(ActionEvent e) {
		try {
			new QualifingWaiting().getQualifing().deselectCurrentDriver();
			new MainMenu().getStage().getScene().setRoot(FXMLLoader.load(getClass().getResource("FXML/Qualifing Waiting.fxml")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		this.stop(new ActionEvent());
		sensor.reset();
		for (int i = 0; i<new Data().getNumCorsie();i++) {
			currentCorsie[i].setText("");
			bestCorsie[i].setText("");
			numberCorsie[i].setText("");
		}
	}

	@Override
	public void exit() {
		back(new ActionEvent());
	}

	public void resetTimer() {
		timer.setTime(new RaceSettings().getQualyduration()*60);
	}

	@Override
	public void error() {
		Data.error("errore nella comunicazione con i sensori");	
	}
	
}
