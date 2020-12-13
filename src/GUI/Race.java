package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import timing.Lane;
import timing.Sensor;

public class Race implements Event, Initializable{
	@FXML private Text rightTimer;
	@FXML private Button start;
	@FXML private Button stop;
	@FXML private VBox provClassification;
	
	private static MyTimer timer;
	private static Text[] currentLanes;
	private static Text[] bestLanes;
	private static Text[] numberLanes;
	private static Text[] distanceLanes;
	private static Sensor sensor;

	public Sensor getSensor() {
		return sensor;
	}
	
	public MyTimer getTimer() {
		return timer;
	}
	
	public Race() {}
	
	public Race(Pane racePane) {
		int numLanes = new Data().getNumCorsie();
		currentLanes = new Text[numLanes];
		bestLanes = new Text[numLanes];
		numberLanes = new Text[numLanes];
		distanceLanes = new Text[numLanes];
		for (int i = 0; i<numLanes;i++) {
			Text currentText = new Text();
			currentText.setLayoutX(100);
			currentText.setLayoutY(((i+1)*120)-40);
			currentText.setFont(Font.font(new Data().getFont(),FontWeight.BOLD,(double)90));
			currentLanes[i] = currentText;
			racePane.getChildren().add(currentText);
			
			Text bestText = new Text();
			bestText.setLayoutX(100);
			bestText.setLayoutY(((i+1)*120)-20);
			bestText.setFont(Font.font(new Data().getFont(),FontWeight.BOLD,(double)25));
			bestLanes[i] = bestText;
			racePane.getChildren().add(bestText);
			
			Text numberText = new Text();
			numberText.setLayoutX(280);
			numberText.setLayoutY(((i+1)*120)-20);
			numberText.setFont(Font.font(new Data().getFont(),FontWeight.BOLD,(double)25));
			numberLanes[i] = numberText;
			racePane.getChildren().add(numberText);
			
			Text distanceText = new Text();
			distanceText.setLayoutX(280);
			distanceText.setLayoutY(((i+1)*120)-20);
			distanceText.setFont(Font.font(new Data().getFont(),FontWeight.BOLD,(double)25));
			distanceLanes[i] = distanceText;
			racePane.getChildren().add(distanceText);
			
		}
		new MainMenu().getStage().getScene().setRoot(racePane);
	}

	public void exit(ActionEvent exit) throws Exception {
		System.exit(0);
	}
	
	public void addSensor(Sensor sensor) {
		Race.sensor = sensor;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		timer = new MyTimer(new Data().getMancheduration()*60,rightTimer,this);
	}
	
	@Override
	public void update(Lane corsia) {
		currentLanes[corsia.getNome()].setText(String.format("%.3f",corsia.getUltimoGiro()));
		bestLanes[corsia.getNome()].setText(String.format("%.3f",corsia.getGiroVeloce()));
		numberLanes[corsia.getNome()].setText(corsia.getNumeroDiGiri()+"");
		
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
	public void exit() {
		try {
			new MainMenu().getStage().getScene().setRoot(FXMLLoader.load(getClass().getResource("FXML/Race Waiting.fxml")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		sensor.reset();
		for (int i = 0; i<new Data().getNumCorsie();i++) {
			currentLanes[i].setText("");
			bestLanes[i].setText("");
			numberLanes[i].setText("");
		}
		
	}
	
	public void resetTimer() {
		timer.setTime(new Data().getMancheduration()*60);
	}

}
