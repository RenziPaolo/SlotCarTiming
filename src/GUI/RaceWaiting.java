package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import testing.test;
import timing.Gara;
import timing.Pilota;
import timing.Sensore;

public class RaceWaiting implements Initializable{
	@FXML private ChoiceBox<Integer> Heat;
	@FXML private ChoiceBox<Integer> Manche;
	@FXML private ScrollPane Classification;
	@FXML private AnchorPane manchePreview;
	
	private Text[] manchePreviewText;
	private test test;
	private Pane racePane;
	private Race qualifingGUI;
	private static Gara race;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Dati data = new Dati();
		int numLanes = data.getNumCorsie();
		String[] participants = new RaceSettings().getParticipants();
		int[][] startingInfo = new RaceSettings().getStartingInfo();
		Integer [] heatNumbers = new Integer[numLanes];
		Integer [] mancheNumbers = new Integer[Arrays.asList(startingInfo).stream().map(x->x[0]).max(Integer::compare).get()];
		for (int i = 0; i<numLanes;i++)
			heatNumbers[i] = i+1;
		for (int i = 0 ;i<Arrays.asList(startingInfo).stream().map(x->x[0]).max(Integer::compare).get();i++){
			mancheNumbers[i] = i+1;
		}
		Heat.setItems(FXCollections.observableArrayList(mancheNumbers));
		Heat.setValue(1);
		Manche.setItems(FXCollections.observableArrayList(heatNumbers));
		Manche.setValue(1);
		Heat.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
		@Override
		public void changed(ObservableValue<? extends Number> observableValue, Number oldnumber, Number newnumber) {
			race.setCurrentHeat(newnumber.intValue()+1);
			change(race.getCurrentHeat());
			}
		});
		Manche.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
		@Override
		public void changed(ObservableValue<? extends Number> observableValue, Number oldnumber, Number newnumber) {
			race.setCurrentManche(newnumber.intValue()+1);
			change(race.getCurrentHeat());
			}
		});
		Dati.setBackground(manchePreview,33,400);
		manchePreviewText = new Text[6];
		ArrayList<Pilota> drivers = new ArrayList<Pilota>();
		for (int i = 0;i<6;i++) {
			Text name = new Text();
			name.setFont(Font.font(new Dati().getFont(),FontWeight.BOLD,(double)25));
			name.setX(5);
			name.setY(i*33+27);
			manchePreviewText[i] = name;
			manchePreview.getChildren().add(name);
		}
		for (int i = 0; i<participants.length;i++) {
			int test = startingInfo[i][0];
			if(startingInfo[i][0]==1) {
				manchePreviewText[startingInfo[i][1]-1].setText(participants[i]);
				drivers.add(new Pilota(participants[i],(float)i,startingInfo[i][1],startingInfo[i][0]));
			} else {
				drivers.add(new Pilota(participants[i],(float)i,startingInfo[i][1],startingInfo[i][0]));				
			}
		}
		

		
		
		try {
			racePane = FXMLLoader.load(getClass().getResource("FXML/Race.fxml"));
			Dati.setBackground(racePane,120,500);
			qualifingGUI = new Race(racePane);
			Gara race = new Gara(drivers, qualifingGUI,1,1);
			this.race = race;
			Sensore sensor = new Sensore(race,new Dati().getMinLapTime());
			qualifingGUI.addSensor(sensor);
			test test = new test(6,race,sensor,7);
			this.test = test;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void change(int heat) {
		ArrayList<Pilota> drivers = race.getPiloti();
		
		for (int i = 0;i<6;i++) {
			manchePreviewText[i].setText("");
		}
		
		for (int i = 0;i<drivers.size();i++) {
			Pilota driver = drivers.get(i);
			if (driver.getHeat()==heat) {
				manchePreviewText[driver.getLanes()[driver.getselectedLane()-1].getNome()].setText(driver.getNomePilota());
			}
		}
	}
	
	public void back(ActionEvent indietro) {
		try {
			new MainMenu().getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("FXML/Race Settings.fxml"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Gara getRace() {
		return race;
	}
	
	public void start(ActionEvent indietro) {
		new MainMenu().getStage().setScene(new Scene(racePane));
	}
	
}
