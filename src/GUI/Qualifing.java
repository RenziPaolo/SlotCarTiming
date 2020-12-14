package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import timing.Lane;
import timing.Sensor;

public class Qualifing implements Event,Initializable{
	@FXML private Text rightTimer;
	@FXML private Button start;
	@FXML private Button stop;
	@FXML private ScrollPane classification;
	
	private static MyTimer timer;
	private static Text[] currentCorsie;
	private static Text[] bestCorsie;
	private static Text[] numberCorsie;
	private static Sensor sensor;
	
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
		setClassification();
	}
	
	private void setClassification() {
		VBox classification = new VBox();
		classification.getChildren().clear();
		Float[][][] classificationfloat = new QualifingWaiting().getQualifing().getClassification();
		int position = 0;
		for (int i = 0; i<classificationfloat.length;i++) {
			if(classificationfloat[i][1][0]!=0) {
				position++;
				HBox riga = new HBox();
				Text pos = new Text();
				pos.setText(position+"  ");
				riga.getChildren().add(pos);
				Text name = new Text();
				name.setText(new QualifingWaiting().getQualifing().getPiloti().get((int)(float)classificationfloat[i][0][0]).getNomePilota()+"  ");
				riga.getChildren().add(name);
				Text laptime = new Text();
				laptime.setText(classificationfloat[i][1]+"");
				riga.getChildren().add(laptime);
				classification.getChildren().add(riga);
			}
		}
		this.classification.setContent(classification);
	}
	
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
		}
		new MainMenu().getStage().getScene().setRoot(current);
	}
	
	public void addSensor(Sensor sensor) {
		Qualifing.sensor = sensor;
	}
	
	@Override
	public void stop(ActionEvent e) {
		sensor.Stop();
		timer.stop();
		stop.setVisible(false);
		start.setVisible(true);
	}

	@Override
	public void start(ActionEvent e) {
		sensor.Start();
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
	
}
