package GUI;

import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Settings implements Initializable{
	
	private int numberOfLanes = 6;
	private Colore[] colors;
	private int[] swap;
	
	@FXML ChoiceBox<String> numberOFLanes;
	@FXML VBox lanesPreferences;
	@FXML VBox championshipPoints;
	@FXML TextField minLapTime;
	@FXML ChoiceBox<String> font;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Dati dati = new Dati();
		colors = dati.getColori();
		swap = dati.getSwap();
		if(dati.getNumCorsie()!=0) 
			numberOfLanes = dati.getNumCorsie();
		
		numberOFLanes.setItems(FXCollections.observableArrayList("6","4","2"));
    	for(int k = 1;k<= numberOfLanes;k++) 
    		lanesPreferences.getChildren().add(getLineCorsie(k,numberOfLanes));
    	numberOFLanes.setValue(String.valueOf(numberOfLanes));
    		
		numberOFLanes.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
		      @Override
		      public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
		        numberOfLanes = Integer.valueOf((String)numberOFLanes.getItems().get((Integer) number2));
		        lanesPreferences.getChildren().clear();
		        
		        for(int j = 1;j<= numberOfLanes;j++) 
		        	lanesPreferences.getChildren().add(getLineCorsie(j,numberOfLanes));
		      }
		    });
		
		font.setItems(FXCollections.observableArrayList(Font.getFontNames()));
		font.setValue("Calibri");
		minLapTime.setText(dati.getMinLapTime()+"");
	}
	
	private Node getLineCorsie(int corsiaAttuale, int totaleCorsie) {
		HBox line = new HBox();
		Text number = new Text();
		Text space = new Text();
		space.setText("                                         ");
		Text space1 = new Text();
		space1.setText("                           ");
		number.setText(corsiaAttuale+")");
		ChoiceBox<String> colori = new ChoiceBox<String>();
		ChoiceBox<Integer> scambio = new ChoiceBox<Integer>();
		colori.setItems(FXCollections.observableArrayList("Giallo","Verde","Bianco","Rosso","Blu","Arancione","Azzurro"));
		colori.setValue(colors[corsiaAttuale-1].toStringlanguage(2));
		for (int i = 1;i<= totaleCorsie;i++)
			scambio.getItems().add(i);
		scambio.setValue(swap[corsiaAttuale-1]);
		line.getChildren().addAll(number,space,colori,space1,scambio);
		return line;
	}
	
	@FXML
	public void Indietro(ActionEvent indietro) {
		ArrayList<Integer> colori = new ArrayList<Integer>();
		ArrayList<Integer> swap = new ArrayList<Integer>();
		try {
			for(int i = 0;i<numberOfLanes;i++) {
				colori.add(Colore.fromlanguage(1,((ChoiceBox<String>) ((HBox) lanesPreferences.getChildren().get(i)).getChildren().get(2)).getValue()));
				swap.add(((ChoiceBox<Integer>)	(	(HBox)lanesPreferences.getChildren().get(i)	).getChildren().get(4)	).getValue());
			}
		} catch(java.lang.NullPointerException e1) {
			Dati.error();
			return;
		}
		try {
			new MainMenu().getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("FXML/Main Menu.fxml"))));
		} catch (IOException e) {
			Dati.error();
			return;
		}
		
		try (FileChannel filelanes = (FileChannel) Files.newByteChannel(Path.of("settings lanes.config"), StandardOpenOption.WRITE,StandardOpenOption.CREATE)){
			ByteBuffer buffer = ByteBuffer.allocate(numberOfLanes*2);
			for (int i = 0;i<numberOfLanes;i++) {
				buffer.put((byte) (int)colori.get(i));
				buffer.put((byte) (int)swap.get(i));
			}
			buffer.rewind();
			filelanes.write(buffer);
			
		} catch (IOException e) {
			Dati.error();
			return;
		}
		
		try (FileChannel fileMinLapTime = (FileChannel) Files.newByteChannel(Path.of("settings minlaptime.config"), StandardOpenOption.WRITE,StandardOpenOption.CREATE)){
			ByteBuffer buffer = ByteBuffer.allocate(4);
			buffer.putFloat(Float.parseFloat(minLapTime.getText()));
			buffer.rewind();
			buffer.rewind();
			fileMinLapTime.write(buffer);
			
		} catch (IOException e) {
			Dati.error();
			return;
		}
		try {
			Files.writeString(Path.of("settings font.config"), font.getValue(), StandardOpenOption.WRITE,StandardOpenOption.CREATE);
			
		} catch (IOException e) {
			Dati.error();
			return;
		}
	}
	
	@FXML
	public void Aggiungi() {
		
	}
	
	@FXML
	public void Togli() {
		
	}

}
