package GUI;

import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import timing.Qualifica;

public class RaceSettings implements Initializable{
	@FXML private VBox startingList;
	@FXML private CheckBox qualy;
	@FXML private TextField qualyduration;
	@FXML private TextField raceName;
	@FXML private Text textLane;
	@FXML private Text min;
	@FXML private Text startingLaneText;
	@FXML private Text startingHeatText;
	@FXML private ChoiceBox<String> choiceLane;
	private int tot;
	private static Colore chosenLane;
	private static int qualyDurationInt;
	private static String[] participants;
	private static int[][] startingInfo;
	
	public Colore getChosenLane() {
		return chosenLane;
	}
	
	public String[] getParticipants() {
		return participants;
	}
	
	public int[][] getStartingInfo() {
		return startingInfo;
	}
	
	public int getQualyduration() {
		return qualyDurationInt;
	}
	
	public void AggiungiPilota(ActionEvent aggiungiPilota) {
		HBox riga = new HBox();
		TextField partecipante = new TextField();
		TextField batteria = new TextField();
		TextField corsia = new TextField();
		corsia.setPromptText("Corsia Di partenza");
		batteria.setPromptText("Batteria di partenza");
		partecipante.setPromptText("Partecipante");
		if (qualy.isSelected()) {
			batteria.setVisible(false);
			corsia.setVisible(false);
		}
		riga.getChildren().addAll(partecipante,batteria,corsia);
		startingList.getChildren().add(riga);
	}
	
	public void RimuoviPilota(ActionEvent RimuoviPilota) {
		if (startingList.getChildren().size() >0)
			startingList.getChildren().remove(startingList.getChildren().size()-1);
	}
	
	public void Inizia(ActionEvent inizia) {
		participants = new String[startingList.getChildren().size()];
		String participant;
		
		for (int i = 0 ; i<startingList.getChildren().size();i++) {
			participant = ((TextField)((HBox)startingList.getChildren().get(i)).getChildren().get(0)).getText();
			if (participant.equals("")) {
				Dati.error();
				return;
			}
			participants[i] = participant;
		}
		
		if (qualy.isSelected()) {
			if (qualyduration.getText().equals("")) {
				Dati.error();
				return;
			}
			
			qualyDurationInt = Integer.valueOf(qualyduration.getText());
			chosenLane = Colore.values()[Colore.fromlanguage(1, choiceLane.getValue())];
			try (FileChannel filequaly = (FileChannel) Files.newByteChannel(Path.of("qualifing.config"), StandardOpenOption.WRITE,StandardOpenOption.CREATE)){
				ByteBuffer buffer = ByteBuffer.allocate(8);
				buffer.putInt((int)Integer.valueOf(qualyduration.getText()));
				buffer.putInt((int)Colore.fromlanguage(1, choiceLane.getValue()));
				buffer.rewind();
				filequaly.write(buffer);
				
				new MainMenu().getStage().getScene().setRoot(FXMLLoader.load(getClass().getResource("FXML/Qualifing Waiting.fxml")));
				
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		} else {
			
			try {
				String startingLane;
				String StartingHeat;
				startingInfo = new int[startingList.getChildren().size()][2];
				
				String raceNameString = raceName.getText();
				if (raceNameString.equals("")) {
					Dati.error();
					return;
				}
				
				for (int i = 0 ; i<startingList.getChildren().size();i++) {
					startingLane = ((TextField)((HBox)startingList.getChildren().get(i)).getChildren().get(2)).getText();
					StartingHeat = ((TextField)((HBox)startingList.getChildren().get(i)).getChildren().get(1)).getText();
					if (startingLane.equals("") || StartingHeat.equals("")) {
						Dati.error();
						return;
					}
					startingInfo[i][0] = Integer.valueOf(StartingHeat);
					startingInfo[i][1] = Integer.valueOf(startingLane)-1;
				}
				new MainMenu().getStage().getScene().setRoot(FXMLLoader.load(getClass().getResource("FXML/Race Waiting.fxml")));
			} catch (IOException e) {
				e.printStackTrace();
				Dati.error();
				return;
			}
			
		}
	}
	
	public void back(ActionEvent indietro) {
		try {
			new MainMenu().getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("FXML/Main Menu.fxml"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void qualifiche(ActionEvent qualifiche) {
		if (tot%2==0) {
			startingLaneText.setVisible(false);
			startingHeatText.setVisible(false);
			qualyduration.setVisible(true);
			min.setVisible(true);
			textLane.setVisible(true);
			choiceLane.setVisible(true);
			for (int i = 0; i<startingList.getChildren().size();i++) {
				ObservableList<Node> list = ((HBox)startingList.getChildren().get(i)).getChildren();
				list.get(1).setDisable(true);
				list.get(1).setVisible(false);
				list.get(2).setDisable(true);
				list.get(2).setVisible(false);
			}
			
		} else {
			startingLaneText.setVisible(true);
			startingHeatText.setVisible(true);
			textLane.setVisible(false);
			min.setVisible(false);
			choiceLane.setVisible(false);
			qualyduration.setVisible(false);
			for (int i = 0; i<startingList.getChildren().size();i++) {
				ObservableList<Node> list = ((HBox)startingList.getChildren().get(i)).getChildren();
				list.get(1).setDisable(false);
				list.get(1).setVisible(true);
				list.get(2).setDisable(false);
				list.get(2).setVisible(true);
			}
		}
		tot++;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		qualyduration.setDisable(false);
		Dati data = new Dati();
		qualyduration.setText(data.getQualifingPeriod()+"");
		Colore[] colors = data.getColori();
		String[] choices = new String[colors.length];
		for (int i = 0; i< colors.length; i++)
			choices[i] = colors[i].toStringlanguage(1);
		
		choiceLane.setItems(FXCollections.observableArrayList(choices));
		choiceLane.setValue(data.getQualifingLane().toStringlanguage(1));
		int numLanes = data.getNumCorsie();
		Qualifica classification = new QualifingWaiting().getQualifing();
		if (classification==null)
			return;
		for (int i = 0;i<participants.length-1;i++) 
			AggiungiPilota(new ActionEvent());

		starting(numLanes,classification,classification.getClassification().length);
	}
	
	private void starting(int numLanes, Qualifica classification,int index) {
		Float[][] classificationFloat = classification.getClassification();
		if ((index+1/2)<=numLanes*2) {
			for(int i = 0;i<index;i++) {
				if(i<index+1/2) {
					((TextField)((HBox)startingList.getChildren().get(i)).getChildren().get(0)).setText(classification.getPiloti().get((int)(float)classificationFloat[i][0]).getNomePilota()+"");
					((TextField)((HBox)startingList.getChildren().get(i)).getChildren().get(1)).setText("1");
					((TextField)((HBox)startingList.getChildren().get(i)).getChildren().get(2)).setText(i+1+"");
				} else {
					((TextField)((HBox)startingList.getChildren().get(i)).getChildren().get(0)).setText(classification.getPiloti().get((int)(float)classificationFloat[i][0]).getNomePilota()+"");
					((TextField)((HBox)startingList.getChildren().get(i)).getChildren().get(1)).setText("2");
					((TextField)((HBox)startingList.getChildren().get(i)).getChildren().get(2)).setText(i-(index/2)+1+"");
				}
			}
		} else {
			int groups = numLanes/index+1;
			for (int j = 0; j<groups/index;j++) {
				((TextField)((HBox)startingList.getChildren().get(j)).getChildren().get(0)).setText(classification.getPiloti().get((int)(float)classificationFloat[j][0]).getNomePilota()+"");
				((TextField)((HBox)startingList.getChildren().get(j)).getChildren().get(1)).setText(groups+"");
				((TextField)((HBox)startingList.getChildren().get(j)).getChildren().get(2)).setText(j+"");	
			}
			starting(numLanes,classification,index-groups/index);
			//da testare!!!
		}
	}

	
}
