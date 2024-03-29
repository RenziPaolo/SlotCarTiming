package GUI.practice;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import GUI.Event;
import GUI.MainMenu;
import GUI.utilities.Data;
import GUI.utilities.MyTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import timing.Lane;
import timing.Sensor;

public class Practice implements Event,Initializable{
	
	@FXML private Text rightTimer = new Text();
	@FXML private Button start;
	@FXML private Button stop;
	@FXML private ScrollPane classification;
	
	private static MyTimer timer;
	private static Text[] currentCorsie;
	private static Text[] bestCorsie;
	private static Text[] numberCorsie;
	private static Button[] selezioneCorsie;
	private static Sensor sensor;
//	private Rele r = new Rele();
	
	public void exit(ActionEvent exit) throws Exception {
		stop(exit);
		Pane mainmenu = FXMLLoader.load(getClass().getResource("FXML/Main Menu.fxml"));
		new MainMenu().getStage().getScene().setRoot(mainmenu);
	}
	
	public Text getTimer() {
		return rightTimer;
	}

	@Override
	public void update(Lane lane) {
		currentCorsie[lane.getNome()].setText(String.format("%.3f",lane.getUltimoGiro()));
		bestCorsie[lane.getNome()].setText(String.format("%.3f",lane.getGiroVeloce()));
		numberCorsie[lane.getNome()].setText(lane.getNumeroDiGiri()+"");
	}

	public Practice() {}
	
	public void addSensor(Sensor sensor) {
		Practice.sensor = sensor;
		sensor.Start();
	}
	
	public Practice(Pane current) {
		int numCorsie = new Data().getNumCorsie();
		currentCorsie = new Text[numCorsie];
		bestCorsie = new Text[numCorsie];
		numberCorsie = new Text[numCorsie];
		for (int i = 0; i<numCorsie;i++) {
			Text currentTesto = new Text();
			currentTesto.setLayoutX(105);
			currentTesto.setLayoutY(((i+1)*120)-35);
			currentTesto.setFont(Font.font(new Data().getFont(),FontWeight.BOLD,(double)110));
			currentCorsie[i] = currentTesto;
			current.getChildren().add(currentTesto);
			
			Text bestTesto = new Text();
			bestTesto.setLayoutX(105);
			bestTesto.setLayoutY(((i+1)*120)-10);
			bestTesto.setFont(Font.font(new Data().getFont(),FontWeight.BOLD,(double)30));
			bestCorsie[i] = bestTesto;
			current.getChildren().add(bestTesto);
			
			Text numberTesto = new Text();
			numberTesto.setLayoutX(285);
			numberTesto.setLayoutY(((i+1)*120)-10);
			numberTesto.setFont(Font.font(new Data().getFont(),FontWeight.BOLD,(double)30));
			numberCorsie[i] = numberTesto;
			current.getChildren().add(numberTesto);
			
			current.getChildren().add(selezioneCorsie[i]);
			
		}	
		new MainMenu().getStage().getScene().setRoot(current);
//		r = new Rele();
		
	}

	@Override
	public void stop(ActionEvent e) {
		sensor.Stop();
//		r.stop();
		timer.stop();
		stop.setVisible(false);
		start.setVisible(true);
	}

	@Override
	public void start(ActionEvent e) {
		sensor.Start();
//		r.start();
		timer.restart();
		stop.setVisible(true);
		start.setVisible(false);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		Data dati = new Data();
		
		int period = new PracticeSettings().getDuration();
		if (period>0) {
			timer = new MyTimer(period*60,rightTimer,this);
			timer.start();
		}
		
		selezioneCorsie = new Button[dati.getNumCorsie()];
		
		for (int i = 0; i<dati.getNumCorsie();i++) {
			Button selezione = new Button();
			selezione.setLayoutX(0);
			selezione.setLayoutY((i)*120);
			selezione.setPrefSize(500, 120);
			selezione.setBackground(Background.EMPTY);
			selezione.setPickOnBounds(true);
			selezione.setOnAction((e->{
				Integer numCorsia = Arrays.asList(selezioneCorsie).indexOf(e.getSource());
				Lane corsia = sensor.getEvento().getPiloti().get(numCorsia).getLanes()[0 ];
				ArrayList<Float> giri =  corsia.getGiri();
				VBox classificationData = new VBox();
				classificationData.getChildren().clear();
				for (int j = 0; j<corsia.getNumeroDiGiri(); j++) {
					Text tempoGiri = new Text();
					Text numGiri = new Text();
					Text space = new Text();
					space.setText("         ");
					numGiri.setText("Giro N° "+(j+1));
					tempoGiri.setText(giri.get(j)+"");
					numGiri.setFont(Font.font(new Data().getFont(),FontWeight.BOLD,(double)30));
					tempoGiri.setFont(Font.font(new Data().getFont(),FontWeight.BOLD,(double)30));
					HBox riga = new HBox();
					riga.getChildren().addAll(numGiri,space,tempoGiri);
					classificationData.getChildren().add(riga);
					}
				classification.setContent(classificationData);
				}));
			selezioneCorsie[i] = selezione;
		}
	}

	@Override
	public void exit() {
		try {
			exit(new ActionEvent());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void error() {
		Data.error("errore nella comunicazione con i sensori");	
	}
	
}
