package GUI;

import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RaceSettings {
	@FXML private VBox startingList;
	@FXML private CheckBox qualy;
	@FXML private TextField qualyduration;
	@FXML private TextField raceName;
	
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
		riga.getChildren().addAll(partecipante,batteria,corsia);
		startingList.getChildren().add(riga);
	}
	
	public void RimuoviPilota(ActionEvent RimuoviPilota) {
		if (startingList.getChildren().size() >0)
			startingList.getChildren().remove(startingList.getChildren().size()-1);
	}
	
	public void Inizia(ActionEvent inizia) {
		if (qualyduration.getText() == null )
		if (qualy.isSelected()) {
			try {
				participants = new String[startingList.getChildren().size()];
				for (int i = 0 ; i<startingList.getChildren().size();i++) {
					participants[i] = ((TextField)((HBox)startingList.getChildren().get(i)).getChildren().get(0)).getText();
				}
				
				new MainMenu().getStage().getScene().setRoot(FXMLLoader.load(getClass().getResource("FXML/Qualifing Waiting.fxml")));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				new MainMenu().getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("FXML/Race Waiting.fxml"))));
			} catch (IOException e) {
				e.printStackTrace();
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
			for (int i = 0; i<startingList.getChildren().size();i++) {
				ObservableList<Node> list = ((HBox)startingList.getChildren().get(0)).getChildren();
				list.get(i+1).setDisable(true);
				list.get(i+1).setVisible(false);
				list.get(i+2).setDisable(true);
				list.get(i+2).setVisible(false);

			}
		} else {
			for (int i = 0; i<startingList.getChildren().size();i++) {
				ObservableList<Node> list = ((HBox)startingList.getChildren().get(0)).getChildren();
				list.get(i+1).setDisable(false);
				list.get(i+1).setVisible(true);
				list.get(i+2).setDisable(false);
				list.get(i+2).setVisible(true);
			}
		}
		tot++;
	}
	
	public TextField getDurataq() {
		return qualyduration;
	}
	
}
