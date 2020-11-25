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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import testing.test;
import timing.Gara;
import timing.ParallelInterface;
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
	private Race raceGUI;
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
			Manche.setValue(1);
			change(race.getCurrentHeat());
			}
		});
		Manche.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
		@Override
		public void changed(ObservableValue<? extends Number> observableValue, Number oldnumber, Number newnumber) {
			race.setCurrentManche(newnumber.intValue());
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
			if(startingInfo[i][0]==1) {
				manchePreviewText[startingInfo[i][1]].setText(participants[i]);
				drivers.add(new Pilota(participants[i],(float)i,startingInfo[i][1],startingInfo[i][0]));
			} else {
				drivers.add(new Pilota(participants[i],(float)i,startingInfo[i][1],startingInfo[i][0]));				
			}
		}
		if (race!=null) {
			Float[][][] classification = race.getClassification();
			VBox classificationInside = new VBox();
			HBox row = new HBox();
			Colore[] colors = data.getColori();
			Text textRow = new Text();
			textRow.setFont(Font.font(new Dati().getFont(),FontWeight.BOLD,(double)25));
			textRow.setText("Pilota");
			row.getChildren().add(textRow);
			for (int i = 0; i<colors.length;i++) {
				Text textRowcolors = new Text();
				textRowcolors.setFont(Font.font(new Dati().getFont(),FontWeight.BOLD,(double)25));
				textRowcolors.setText(colors[i].toStringlanguage(1));
				row.getChildren().add(textRowcolors);
			}	
			classificationInside.getChildren().add(row);
		
			for (int i = 0; i<classification.length;i++) {
				row = new HBox();
				Text textRowdriver = new Text();
				textRowdriver.setFont(Font.font(new Dati().getFont(),FontWeight.BOLD,(double)25));
				textRowdriver.setText(race.getPiloti().get((int)(float)classification[i][0][0]).getNomePilota()+"  ");
				row.getChildren().add(textRowdriver);
				for (int j = 0;j<classification[i][1].length;j++) {
					Text textRowmanche = new Text();
					textRowmanche.setFont(Font.font(new Dati().getFont(),FontWeight.BOLD,(double)25));
					textRowmanche.setText(Math.round(classification[i][1][j])+"    ");
					row.getChildren().add(textRowmanche);
				}
				Rectangle space = new Rectangle();
				space.setLayoutY(100);
				row.getChildren().add(space);
				Text textRowtot = new Text();
				textRowtot.setFont(Font.font(new Dati().getFont(),FontWeight.BOLD,(double)25));
				textRowtot.setText(Math.round(classification[i][3][0])+"");
				row.getChildren().add(textRowtot);
				
				classificationInside.getChildren().add(row);
			}
		
			Classification.setContent(classificationInside);
		}
		try {
			racePane = FXMLLoader.load(getClass().getResource("FXML/Race.fxml"));
			Dati.setBackground(racePane,120,500);
			raceGUI = new Race(racePane);
			Gara race = new Gara(drivers, raceGUI,1,0);
			RaceWaiting.race = race;
			Sensore sensor = new Sensore(race,new Dati().getMinLapTime());
			raceGUI.addSensor(sensor);
			ParallelInterface parInterface = new ParallelInterface(sensor);
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
				manchePreviewText[driver.getLanes()[driver.getselectedLane()].getNome()].setText(driver.getNomePilota());
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
		test.testCorsie(6, 30);
		raceGUI.resetTimer();
		raceGUI.getTimer().start();
		raceGUI.getSensor().reset();
		new MainMenu().getStage().getScene().setRoot(this.racePane);
	}
	
}
