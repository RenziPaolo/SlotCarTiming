package testing;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import GUI.Colore;
import GUI.MainMenu;
import GUI.Practice;
import timing.*;

public class test extends TimerTask{
	
	private static Sensore sensore;
	private int corsia;
	public static Evento evento;

	
	public test(int corsia, Evento evento,Sensore sensore) {
		this.corsia = corsia;
//		HashMap<Integer,Pilota> configurazione = new HashMap<Integer,Pilota>();
//		try(FileChannel file = (FileChannel) Files.newByteChannel(Path.of("settings.txt"), StandardOpenOption.READ)){
//			ByteBuffer buffer = ByteBuffer.allocate(12);
//			file.read(buffer);
//			for (int i = 1; i<7;i++) {
//				Corsia corisa = new Corsia(i,Colore.ARANCIONE.getColore(((int)buffer.get(i-1))));
//				configurazione.put(i, new Pilota("test",corisa));
//				}
//		} catch(IOException e){
//			
//		}
		
		this.evento = evento;
		this.sensore = sensore;
		
	}
	
	public void testCorsie(int lanes) {
		for (int i = 1;i<= lanes;i++)
			new Timer().schedule(new test(i,evento,sensore),0);
	}
	
	private void generateRandomTimingEvent(int upperLimit, int lowerLimit, int numberOfLaps, int lanes,Sensore sensore) {
		TimingEvent timing = new TimingEvent(new Object(),0);
		timing.addSensore(sensore);
		for(;numberOfLaps>0 ;numberOfLaps--) {
			float lapTime = getLaptime(upperLimit, lowerLimit);
			try {
				Thread.sleep((long)(lapTime*1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			timing.setCorsia(lanes);
		}
	}
	
	private float getLaptime(int upperLimit, int lowerLimit) {
		return lowerLimit+new Random().nextInt(upperLimit-lowerLimit)+(float)new Random().nextInt(999)/(float)(1000);
	}

	@Override
	public void run() {
		
		test test = new test(corsia,evento,sensore);
		test.generateRandomTimingEvent(14, 12, 10, corsia, sensore);
		
	}
}
