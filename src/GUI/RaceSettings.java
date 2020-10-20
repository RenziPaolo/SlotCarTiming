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
	private static String[] participants;
	
	public String[] getParticipants() {
		return participants;
	}
	
	public int getQualyduration() {
		return Integer.valueOf(qualyduration.getText());
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

		if (qualy.isSelected()) {
			if (qualyduration.getText().equals("")) {
				Dati.error();
				return;
			}
			String participant;
			participants = new String[startingList.getChildren().size()];
			for (int i = 0 ; i<startingList.getChildren().size();i++) {
				participant = ((TextField)((HBox)startingList.getChildren().get(i)).getChildren().get(0)).getText();
				if (participant.equals("")) {
					Dati.error();
					return;
				}
				participants[i] = participant;
			}
			try (FileChannel filequaly = (FileChannel) Files.newByteChannel(Path.of("qualifing.config"), StandardOpenOption.WRITE,StandardOpenOption.CREATE)){
				ByteBuffer buffer = ByteBuffer.allocate(8);
				buffer.putInt((int)Integer.valueOf(qualyduration.getText()));
				buffer.putInt((int)Colore.fromlanguage(1, choiceLane.getValue()));
				buffer.rewind();
				filequaly.write(buffer);
				
				new MainMenu().getStage().getScene().setRoot(FXMLLoader.load(getClass().getResource("FXML/Qualifing Waiting.fxml")));
				
			} catch (IOException e) {
				System.out.println();
				return;
			}
		} else {
			
			try {
				String participant;
				String startingLane;
				String StartingHeat;
				
				for (int i = 0 ; i<startingList.getChildren().size();i++) {
					participant = ((TextField)((HBox)startingList.getChildren().get(i)).getChildren().get(0)).getText();
					startingLane = ((TextField)((HBox)startingList.getChildren().get(i)).getChildren().get(1)).getText();
					StartingHeat = ((TextField)((HBox)startingList.getChildren().get(i)).getChildren().get(2)).getText();
					if (participant.equals("") || startingLane.equals("") || StartingHeat.equals("")) {
						Dati.error();
						return;
					}
				}
				new MainMenu().getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("FXML/Race Waiting.fxml"))));
			} catch (IOException e) {
				Dati.error();
				return;
			}
			
		}
	}
	
	public void indietro(ActionEvent indietro) {
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
	
	public TextField getDurataq() {
		return qualyduration;
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
		
	}
	
}
