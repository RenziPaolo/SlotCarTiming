package GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import timing.Corsia;
import timing.Sensore;

public class Qualifing implements Event,Initializable{
	@FXML private Text rightTimer = new Text();
	@FXML private Button start;
	@FXML private Button stop;
	@FXML private VBox classification;
	
	private static MyTimer timer;
	private static Text[] currentCorsie;
	private static Text[] bestCorsie;
	private static Text[] numberCorsie;
	private static Button[] selezioneCorsie;
	private static Sensore sensor;
	
	
	@Override
	public void update(Corsia corsia) {
		// TODO Auto-generated method stub
		
	}
	
	public Qualifing(Pane current) {
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
			current.getChildren().add(currentTesto);
			
			Text bestTesto = new Text();
			bestTesto.setLayoutX(20);
			bestTesto.setLayoutY(((i+1)*120)-20);
			bestTesto.setFont(Font.font(new Dati().getFont(),FontWeight.BOLD,(double)25));
			bestCorsie[i] = bestTesto;
			current.getChildren().add(bestTesto);
			
			Text numberTesto = new Text();
			numberTesto.setLayoutX(200);
			numberTesto.setLayoutY(((i+1)*120)-20);
			numberTesto.setFont(Font.font(new Dati().getFont(),FontWeight.BOLD,(double)25));
			numberCorsie[i] = numberTesto;
			current.getChildren().add(numberTesto);
			
//			current.getChildren().add(selezioneCorsie[i]);
			
		}	
		new MainMenu().getStage().getScene().setRoot(current);
	}
	
	public void addSensor(Sensore sensor) {
		this.sensor = sensor;
	}
	
	@Override
	public void stop(ActionEvent e) {
		sensor.Stop();
	}

	@Override
	public void start(ActionEvent e) {
		sensor.Start();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		Dati dati = new Dati();
		if (dati.getPeriod()>0) {
			timer = new MyTimer(dati.getQualifingPeriod()*60,rightTimer);
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
				Corsia corsia = sensor.getEvento().getPiloti().get(numCorsia+1).getCorsia();
				ArrayList<Float> giri =  corsia.getGiri();
				classification.getChildren().clear();
				for (int j = 0; j<corsia.getNumeroDiGiri(); j++) {
					Text tempoGiri = new Text();
					Text numGiri = new Text();
					Text space = new Text();
					space.setText("         ");
					numGiri.setText("Giro N"+(j+1));
					tempoGiri.setText(giri.get(j)+"");
					HBox riga = new HBox();
					riga.getChildren().addAll(numGiri,space,tempoGiri);
					System.out.println(numGiri.getText());
					System.out.println(tempoGiri.getText());
					classification.getChildren().add(riga);
					}
				}));
			selezioneCorsie[i] = selezione;
		}
	}
	
}
