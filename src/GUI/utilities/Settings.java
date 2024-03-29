package GUI.utilities;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.ResourceBundle;

import GUI.MainMenu;
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
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Settings implements Initializable{
	
	private int numberOfLanes = 6;
	private ColorLane[] colors;
	private int[] swap;
	
	@FXML private ChoiceBox<String> numberOFLanes;
	@FXML private VBox lanesPreferences;
	@FXML private TextField minLapTime;
	@FXML private ChoiceBox<String> font;
	@FXML private TextField mancheDuration;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
			Data dati = new Data();
			colors = dati.getColori();
			swap = dati.getSwap();
			if(dati.getNumCorsie()!=0) 
				numberOfLanes = dati.getNumCorsie();
			
			numberOFLanes.setItems(FXCollections.observableArrayList("8","6","4","2"));
			for(int k = 1;k<= numberOfLanes;k++) 
				lanesPreferences.getChildren().add(getLineCorsie(k,numberOfLanes));
			
			numberOFLanes.setValue(String.valueOf(numberOfLanes));
				
			numberOFLanes.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			      @Override
			      public void changed(ObservableValue<? extends Number> observableValue, Number oldnumber, Number newnumber) {
			        numberOfLanes = Integer.valueOf((String)numberOFLanes.getItems().get((Integer) newnumber));
			        lanesPreferences.getChildren().clear();
			        
			        for(int j = 1;j<= numberOfLanes;j++) 
			        	lanesPreferences.getChildren().add(getLineCorsie(j,numberOfLanes));
			      }
			    });
			
			font.setValue("Calibri");
			minLapTime.setText(dati.getMinLapTime()+"");
			mancheDuration.setText(dati.getMancheduration()+"");
		} catch (Exception e) {
		}
	}
	
	private Node getLineCorsie(int corsiaAttuale, int totaleCorsie) {
		HBox line = new HBox();
		Text number = new Text();
		Rectangle space = new Rectangle();
		space.setWidth(30);
		Rectangle space1 = new Rectangle();
		space1.setWidth(100);
		Rectangle space2 = new Rectangle();
		space2.setWidth(60);
		Rectangle space3 = new Rectangle();
		space3.setWidth(57);
		Rectangle space4 = new Rectangle();
		space4.setWidth(35);
		Rectangle space5 = new Rectangle();
		space5.setWidth(55);
		Rectangle space6 = new Rectangle();
		space6.setWidth(110);
		number.setText(corsiaAttuale+")");
		ChoiceBox<String> colori = new ChoiceBox<String>();
		ChoiceBox<Integer> scambio = new ChoiceBox<Integer>();
		colori.setItems(FXCollections.observableArrayList("Giallo","Verde","Bianco","Rosso","Blu","Arancione","Azzurro"));

		ChoiceBox<Integer> pinsensor = new ChoiceBox<Integer>();
		pinsensor.getItems().addAll(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30);
		ChoiceBox<Integer> pinrele = new ChoiceBox<Integer>();
		pinrele.getItems().addAll(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30);
		ChoiceBox<Integer> pinsensor1 = new ChoiceBox<Integer>();
		pinsensor1.getItems().addAll(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30);
		ChoiceBox<Integer> pinsensor2 = new ChoiceBox<Integer>();
		pinsensor2.getItems().addAll(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30);
		for (int i = 1;i<= totaleCorsie;i++)
			scambio.getItems().add(i);
		try {
			colori.setValue(colors[corsiaAttuale-1].toStringlanguage(2));
			scambio.setValue(swap[corsiaAttuale-1]+1);
		} catch (Exception e) {
			System.out.print("nessun dato gi� salvato");
			
		}
		line.getChildren().addAll(space,number,space1,colori,space2,scambio,space3,pinsensor,space4,pinrele,space5,pinsensor1,space6,pinsensor2);
		return line;
	}
	
	@SuppressWarnings("unchecked")
	@FXML
	public void back(ActionEvent indietro) {
		try {
			Files.deleteIfExists(Path.of("config/settings lanes.config"));
			Files.deleteIfExists(Path.of("config/settings minlaptime.config"));
			Files.deleteIfExists(Path.of("config/settings MancheDuration.config"));
			Files.deleteIfExists(Path.of("config/settings Requiredsensors.config"));
			Files.deleteIfExists(Path.of("config/settings Additionalsensors.config"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ArrayList<Integer> colori = new ArrayList<Integer>();
		ArrayList<Integer> swap = new ArrayList<Integer>();
			for(int i = 0;i<numberOfLanes;i++) {
				colori.add(ColorLane.fromlanguage(1,((ChoiceBox<String>) ((HBox) lanesPreferences.getChildren().get(i)).getChildren().get(3)).getValue()));
				swap.add(((ChoiceBox<Integer>)	(	(HBox)lanesPreferences.getChildren().get(i)	).getChildren().get(5)	).getValue()-1);
			}

		
		try (FileChannel filelanes = (FileChannel) Files.newByteChannel(Path.of("config/settings lanes.config"), StandardOpenOption.WRITE,StandardOpenOption.CREATE)){
			ByteBuffer buffer = ByteBuffer.allocate(numberOfLanes*2);
			for (int i = 0;i<numberOfLanes;i++) {
				buffer.put((byte) (int)colori.get(i));
				buffer.put((byte) (int)swap.get(i));
			}
			buffer.rewind();
			filelanes.write(buffer);
			
		} catch (IOException e) {
			Data.error("Error 404 file not found");
			return;
		}
		
		try (FileChannel fileMinLapTime = (FileChannel) Files.newByteChannel(Path.of("config/settings minlaptime.config"), StandardOpenOption.WRITE,StandardOpenOption.CREATE)){
			ByteBuffer buffer = ByteBuffer.allocate(16);
			
			buffer.put(minLapTime.getText().getBytes());
			buffer.rewind();
			fileMinLapTime.write(buffer);
			
		} catch (IOException e) {
			Data.error("Error 404 file not found, please check all files and retry");
			return;
		}
		try {
			Files.writeString(Path.of("config/settings font.config"), font.getValue(), StandardOpenOption.WRITE,StandardOpenOption.CREATE);
			
		} catch (IOException e) {
			Data.error("Error 404 file not found, please check all files and retry");
			return;
		}
		
		try (FileChannel fileMancheDuration = (FileChannel) Files.newByteChannel(Path.of("config/settings MancheDuration.config"), StandardOpenOption.WRITE,StandardOpenOption.CREATE)){
			ByteBuffer buffer = ByteBuffer.allocate(4);
			buffer.putInt(Integer.parseInt(mancheDuration.getText()));
			buffer.rewind();
			fileMancheDuration.write(buffer);
			
		} catch (Exception e) {
			Data.error("Error 404 file not found, please check all files and retry");
			e.printStackTrace();
			return;
		}
		
		try (OutputStream fileRequiredsensors = Files.newOutputStream(Path.of("config/settings Requiredsensors.config"), StandardOpenOption.WRITE,StandardOpenOption.CREATE)){
			
			for (int i = 0;i<numberOfLanes;i++) {
				String sensors = (((ChoiceBox<Integer>)	(	(HBox)lanesPreferences.getChildren().get(i)	).getChildren().get(7)	).getValue()+"");
				String rele = (((ChoiceBox<Integer>)	(	(HBox)lanesPreferences.getChildren().get(i)	).getChildren().get(9)	).getValue()+"");
				
				fileRequiredsensors.write(sensors.getBytes());
				fileRequiredsensors.write(",".getBytes());
				fileRequiredsensors.write(rele.getBytes());
				fileRequiredsensors.write(System.lineSeparator().getBytes());
			}
			
		} catch (Exception e) {
			Data.error("Error 404 file not found, please check all files and retry");
			return;
		}
		
		try (FileChannel fileAdditionalsensors = (FileChannel) Files.newByteChannel(Path.of("config/settings Additionalsensors.config"), StandardOpenOption.WRITE,StandardOpenOption.CREATE)){
			ByteBuffer buffer = ByteBuffer.allocate(numberOfLanes*16);
			for (int i = 0;i<numberOfLanes;i++) {
				char[] sensors1 = (((ChoiceBox<Integer>)	(	(HBox)lanesPreferences.getChildren().get(i)	).getChildren().get(11)	).getValue()+"").toCharArray();
				char[] sensors2 = (((ChoiceBox<Integer>)	(	(HBox)lanesPreferences.getChildren().get(i)	).getChildren().get(13)	).getValue()+"").toCharArray();
				for (int j = 0; j<sensors1.length;j++)
					buffer.putChar(sensors1[j]);
				buffer.putChar(',');
				for (int k = 0; k<sensors2.length;k++)
					buffer.putChar(sensors2[k]);	
				buffer.putChar('.');
				}
			buffer.rewind();
			fileAdditionalsensors.write(buffer);
			
		} catch (Exception e) {
		}
		
		try {
			new MainMenu().getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("FXML/Main Menu.fxml"))));
		} catch (IOException e) {
			Data.error("Error 404 file not found, please check all files and retry");
			return;
		}
		
	}
	
	@FXML
	public void addFonts(ActionEvent e) {
		font.setItems(FXCollections.observableArrayList(Font.getFamilies()));
	}

}
