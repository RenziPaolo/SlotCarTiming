package timing;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;

public class SensorComunicationDirect implements Runnable{
	private boolean go;
	private Sensor sensor;
	private Thread t;
	
	public static double convertToFloat(byte[] array) {
		byte tmp;
        for (int i = 0; i<(array.length)/2;i++) {
            tmp = array[i];
            array[i] = array[(array.length-1)-i];
            array[(array.length-1)-i] = tmp;
        }
		
	    ByteBuffer buffer = ByteBuffer.wrap(array);
	    return buffer.getDouble();
	}
	public static int convertToInt(byte[] array) {
		byte tmp;
        for (int i = 0; i<(array.length)/2;i++) {
            tmp = array[i];
            array[i] = array[(array.length-1)-i];
            array[(array.length-1)-i] = tmp;
        }
		
	    ByteBuffer buffer = ByteBuffer.wrap(array);
	    return buffer.getInt();
	}
	
	public SensorComunicationDirect(Sensor sensor) {
		this.sensor = sensor;
	}
	
	public void start() {
		t = new Thread(this,"CComunication");
		t.start();
	}
		
	public void stop() {
		go = false;
	}
	public void go() {
		go = true;
	}
	@Override
	public void run() {
		Runtime r = Runtime.getRuntime();
		Process p = null;
		String fromC = "";
		ProcessBuilder builder = new ProcessBuilder();
		builder.command("./SlotCarTimingCshared-test.out","6");
		File f = new File(System.getProperty("user.dir")+"/c/test"); 
		builder.directory(f);
		builder.redirectErrorStream(true);
		//only for debug
		//builder.inheritIO();

		
		try {
			p = builder.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		InputStream input = p.getInputStream();
		BufferedReader streamC = new BufferedReader(new InputStreamReader(input));  
		while (go) {
			try {
				fromC = streamC.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (fromC == null) {
				System.out.println("null");
				continue;
			}
			System.out.println(fromC);
			if (fromC.equals("")) {continue;}
			String[] LapTimeAndLane = fromC.split(":");
			sensor.setTime(Double.valueOf(LapTimeAndLane[0]), Integer.valueOf(LapTimeAndLane[1]));
		}
		try {
			streamC.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		p.destroy();
		
	}
	/*
	*	Exception in thread "CComunication" java.lang.NullPointerException
	*		 
	*	at GUI.qualifing.Qualifing.setClassification(Qualifing.java:79)
	*	at GUI.qualifing.Qualifing.update(Qualifing.java:55)
	*	at timing.EventT.updatePilota(EventT.java:33)
	*	at timing.Sensor.setTime(Sensor.java:44)
	*	at timing.SensorComunicationDirect.run(SensorComunicationDirect.java:87)
	*	at java.base/java.lang.Thread.run(Thread.java:830)
	*/
}
