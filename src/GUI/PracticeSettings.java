package GUI;

import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import testing.test;
import timing.Corsia;
import timing.Pilota;
import timing.Prove;
import timing.Sensore;

public class PracticeSettings implements Initializable{
	
	@FXML private TextField durata;

	public void Inizia(ActionEvent start) throws Exception {
		Pane practice = FXMLLoader.load(getClass().getResource("FXML/Practice.fxml"));
		
		Dati dati = new Dati();
		int numCorsie =  dati.getNumCorsie();
		HashMap<Integer,Pilota> mappa = new HashMap<Integer,Pilota>();
		Colore[] colori = dati.getColori(); 
		
		for (int i = 1; i<=(numCorsie);i++) {
			Corsia corisa = new Corsia(i,colori[i-1]);
			Pilota pilota = new Pilota("test",corisa);
			mappa.put(i, pilota);
		}
		
		Dati.setBackground(practice);
		Practice prove = new Practice(practice);
		Sensore sensor = new Sensore(new Prove(mappa, prove),dati.getMinLapTime());
		prove.addSensor(sensor);
		test test =new test(6,new Prove(mappa, prove),sensor);	
		test.testCorsie(6);
		
		try (FileChannel filelanes = (FileChannel) Files.newByteChannel(Path.of("settings practice.config"), StandardOpenOption.WRITE,StandardOpenOption.CREATE)){
			ByteBuffer buffer = ByteBuffer.allocate(4);
			buffer.putInt(Integer.parseInt(durata.getText()));
			buffer.rewind();
			filelanes.write(buffer);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		new MainMenu().getStage().getScene().setRoot(practice);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		durata.setText(String.valueOf(new Dati().getPeriod()));
	}
	

}
