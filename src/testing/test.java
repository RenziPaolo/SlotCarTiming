package testing;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import timing.*;

public class test extends TimerTask{
	
	private static Sensor sensore;
	private int corsia;
	private int numberOfLaps;
	public static EventT evento;

	
	public test(int corsia, EventT evento,Sensor sensore,int numberOfLaps) {
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
		
		test.evento = evento;
		test.sensore = sensore;
		this.numberOfLaps = numberOfLaps;
	}
	
	public void testCorsie(int lanes,int numberOfLaps) {
		for (int i = 1;i<= lanes;i++)
			new Timer().schedule(new test(i,evento,sensore,numberOfLaps),0);
	}
	
	private void generateRandomTimingEvent(int upperLimit, int lowerLimit, int numberOfLaps, int lanes,Sensor sensore) {
		TimingEvent timing = new TimingEvent(new Object(),lanes-1);
		timing.addSensore(sensore);
		for(;numberOfLaps>0 ;numberOfLaps--) {
			float lapTime = getLaptime(upperLimit, lowerLimit);
			try {
				Thread.sleep((long)(lapTime*1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			timing.setCorsia();
		}
		
	}
	
	private float getLaptime(int upperLimit, int lowerLimit) {
		return lowerLimit+new Random().nextInt(upperLimit-lowerLimit)+(float)new Random().nextInt(999)/(float)(1000);
	}

	@Override
	public void run() {
	test test = new test(corsia,evento,sensore,numberOfLaps);
	test.generateRandomTimingEvent(14, 12, numberOfLaps, corsia, sensore);
	this.cancel();
	}
}
