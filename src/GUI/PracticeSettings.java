package GUI;

import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import testing.test;
import timing.Pilota;
import timing.Prove;
import timing.Sensore;

public class PracticeSettings implements Initializable{
	
	@FXML private TextField durata;
	
	private static int duration;

	public void Inizia(ActionEvent start) throws Exception {
		duration = Integer.parseInt(durata.getText());
		try (FileChannel filelanes = (FileChannel) Files.newByteChannel(Path.of("settings practice.config"), StandardOpenOption.WRITE,StandardOpenOption.CREATE)){
			ByteBuffer buffer = ByteBuffer.allocate(4);
			buffer.putInt(Integer.parseInt(durata.getText()));
			buffer.rewind();
			filelanes.write(buffer);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Pane practice = FXMLLoader.load(getClass().getResource("FXML/Practice.fxml"));
		
		Dati data = new Dati();
		int numCorsie =  data.getNumCorsie();
		ArrayList<Pilota> list = new ArrayList<Pilota>();
		
		for (int i = 0; i<(numCorsie);i++) {
			Pilota pilota = new Pilota("test",(float)i,i,1);
			list.add(pilota);
		}
		
		Dati.setBackground(practice,120,500);
		Practice prove = new Practice(practice);
		Sensore sensor = new Sensore(new Prove(list, prove,1),data.getMinLapTime());
		prove.addSensor(sensor);
		test test =new test(6,new Prove(list, prove,1),sensor,10);	
		test.testCorsie(6,10);
		

		
		new MainMenu().getStage().getScene().setRoot(practice);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		durata.setText(String.valueOf(new Dati().getPeriod()));
	}
	
	public int getDuration() {
		return duration;
	}

}
