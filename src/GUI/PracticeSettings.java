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
import timing.Driver;
import timing.Test;
import timing.Sensor;

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
		
		Data data = new Data();
		int numCorsie =  data.getNumCorsie();
		ArrayList<Driver> list = new ArrayList<Driver>();
		
		for (int i = 0; i<(numCorsie);i++) {
			Driver pilota = new Driver("test",(float)i,i,1);
			list.add(pilota);
		}
		
		Data.setBackground(practice,120,500);
		Practice prove = new Practice(practice);
		Sensor sensor = new Sensor(new Test(list, prove,1),data.getMinLapTime());
		prove.addSensor(sensor);
		test test =new test(6,new Test(list, prove,1),sensor,10);	
		test.testCorsie(6,10);
		

		
		new MainMenu().getStage().getScene().setRoot(practice);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		durata.setText(String.valueOf(new Data().getPeriod()));
	}
	
	public int getDuration() {
		return duration;
	}

}
