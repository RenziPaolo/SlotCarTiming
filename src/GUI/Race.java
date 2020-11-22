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
import timing.Corsia;
import timing.Sensore;

public class Race implements Event, Initializable{
	@FXML private Text rightTimer;
	@FXML private Button start;
	@FXML private Button stop;
	@FXML private VBox provClassification;
	
	private static MyTimer timer;
	private static Text[] currentCorsie;
	private static Text[] bestCorsie;
	private static Text[] numberCorsie;
	private static Sensore sensor;

	public Sensore getSensor() {
		return sensor;
	}
	
	public MyTimer getTimer() {
		return timer;
	}
	
	public Race() {}
	
	public Race(Pane racePane) {
		int numCorsie = new Dati().getNumCorsie();
		currentCorsie = new Text[numCorsie];
		bestCorsie = new Text[numCorsie];
		numberCorsie = new Text[numCorsie];
		for (int i = 0; i<numCorsie;i++) {
			Text currentTesto = new Text();
			currentTesto.setLayoutX(20);
			currentTesto.setLayoutY(((i+1)*120)-40);
			currentTesto.setFont(Font.font(new Dati().getFont(),FontWeight.BOLD,(double)90));
			currentCorsie[i] = currentTesto;
			racePane.getChildren().add(currentTesto);
			
			Text bestTesto = new Text();
			bestTesto.setLayoutX(20);
			bestTesto.setLayoutY(((i+1)*120)-20);
			bestTesto.setFont(Font.font(new Dati().getFont(),FontWeight.BOLD,(double)25));
			bestCorsie[i] = bestTesto;
			racePane.getChildren().add(bestTesto);
			
			Text numberTesto = new Text();
			numberTesto.setLayoutX(200);
			numberTesto.setLayoutY(((i+1)*120)-20);
			numberTesto.setFont(Font.font(new Dati().getFont(),FontWeight.BOLD,(double)25));
			numberCorsie[i] = numberTesto;
			racePane.getChildren().add(numberTesto);
		}
		new MainMenu().getStage().getScene().setRoot(racePane);
	}

	public void exit(ActionEvent exit) throws Exception {
		System.exit(0);
	}
	
	public void addSensor(Sensore sensor) {
		Race.sensor = sensor;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		timer = new MyTimer(new Dati().getMancheduration()*60,rightTimer,this);
	}
	
	@Override
	public void update(Corsia corsia) {
		currentCorsie[corsia.getNome()].setText(String.format("%.3f",corsia.getUltimoGiro()));
		bestCorsie[corsia.getNome()].setText(String.format("%.3f",corsia.getGiroVeloce()));
		numberCorsie[corsia.getNome()].setText(corsia.getNumeroDiGiri()+"");
		
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
			new MainMenu().getStage().getScene().setRoot(FXMLLoader.load(getClass().getResource("FXML/Qualifing Waiting.fxml")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		sensor.reset();
		for (int i = 0; i<new Dati().getNumCorsie();i++) {
			currentCorsie[i].setText("");
			bestCorsie[i].setText("");
			numberCorsie[i].setText("");
		}
		
	}
	
	public void resetTimer() {
		timer.setTime(new Dati().getMancheduration()*60);
	}

}
