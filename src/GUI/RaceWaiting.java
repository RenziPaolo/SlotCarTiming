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
import timing.RaceT;
import timing.Driver;
import timing.Lane;
import timing.Sensor;

public class RaceWaiting implements Initializable{
	@FXML private ChoiceBox<Integer> Heat;
	@FXML private ChoiceBox<Integer> Manche;
	@FXML private ScrollPane Classification;
	@FXML private AnchorPane manchePreview;
	
	private Text[] manchePreviewText;
	private test test;
	private Pane racePane;
	private Race raceGUI;
	private static RaceT race;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Data data = new Data();
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
		Data.setBackground(manchePreview,33,400,false);
		manchePreviewText = new Text[6];
		ArrayList<Driver> drivers = new ArrayList<Driver>();
		for (int i = 0;i<6;i++) {
			Text name = new Text();
			name.setFont(Font.font(new Data().getFont(),FontWeight.BOLD,(double)25));
			name.setX(5);
			name.setY(i*33+27);
			manchePreviewText[i] = name;
			manchePreview.getChildren().add(name);
		}
		for (int i = 0; i<participants.length;i++) {
			if(startingInfo[i][0]==1) {
				manchePreviewText[startingInfo[i][1]].setText(participants[i]);
				drivers.add(new Driver(participants[i],i,startingInfo[i][1],startingInfo[i][0]));
			} else {
				drivers.add(new Driver(participants[i],i,startingInfo[i][1],startingInfo[i][0]));				
			}
		}
		if (race!=null) {
			int[] classification = race.getClassification();
			VBox classificationInside = new VBox();
			HBox row = new HBox();
			ColorLane[] colors = data.getColori();
			Text textRow = new Text();
			textRow.setFont(Font.font(new Data().getFont(),FontWeight.BOLD,(double)25));
			textRow.setText("Pilota");
			row.getChildren().add(textRow);
			for (int i = 0; i<colors.length;i++) {
				Text textRowcolors = new Text();
				textRowcolors.setFont(Font.font(new Data().getFont(),FontWeight.BOLD,(double)25));
				textRowcolors.setText(colors[i].toStringlanguage(1));
				row.getChildren().add(textRowcolors);
			}	
			classificationInside.getChildren().add(row);
		
			for (int i = 0; i<classification.length;i++) {
				row = new HBox();
				Text textRowdriver = new Text();
				textRowdriver.setFont(Font.font(new Data().getFont(),FontWeight.BOLD,(double)25));
				textRowdriver.setText(race.getPiloti().get(classification[i]).getNomePilota()+"  ");
				row.getChildren().add(textRowdriver);
				Lane[] heats = race.getPiloti().get(i).getLanes();
				int tot = 0;
				for (int j = 0;j<heats.length;j++) {
					Text textRowmanche = new Text();
					textRowmanche.setFont(Font.font(new Data().getFont(),FontWeight.BOLD,(double)25));
					textRowmanche.setText(heats[j].getNumeroDiGiri()+"    ");
					row.getChildren().add(textRowmanche);
					tot+= heats[j].getNumeroDiGiri();
				}
				Rectangle space = new Rectangle();
				space.setLayoutY(100);
				row.getChildren().add(space);
				Text textRowtot = new Text();
				textRowtot.setFont(Font.font(new Data().getFont(),FontWeight.BOLD,(double)25));
				textRowtot.setText(tot+"");
				row.getChildren().add(textRowtot);
				
				classificationInside.getChildren().add(row);
			}
		
			Classification.setContent(classificationInside);
		}
		try {
			racePane = FXMLLoader.load(getClass().getResource("FXML/Race.fxml"));
			Data.setBackground(racePane,120,500,true);
			raceGUI = new Race(racePane);
			RaceT race = new RaceT(drivers, raceGUI,1,0);
			RaceWaiting.race = race;
			raceGUI.setRace(race);
			Sensor sensor = new Sensor(race,new Data().getMinLapTime());
			raceGUI.addSensor(sensor);	
			test test = new test(6,race,sensor,7);
			this.test = test;
		} catch (IOException e1) {
//			e1.printStackTrace();
		}
		
	}
	
	public void change(int heat) {
		ArrayList<Driver> drivers = race.getPiloti();
		
		for (int i = 0;i<6;i++) {
			manchePreviewText[i].setText("");
		}
		
		for (int i = 0;i<drivers.size();i++) {
			Driver driver = drivers.get(i);
			if (driver.getHeat()==heat) {
				manchePreviewText[driver.getselectedLane().getNome()].setText(driver.getNomePilota());
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

	public static RaceT getRace() {
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
