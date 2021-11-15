package GUI.utilities;
/*
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
*/
public class Rele {
	/*
	private BufferedWriter output;
	
	public Rele() {
		
		Process p = null;
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
		
		output = new BufferedWriter(new OutputStreamWriter(p.getOutputStream())); 
		
	}
	
	public void start() {
		try {
			output.write(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void stop() {
		try {
			output.write(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
}
