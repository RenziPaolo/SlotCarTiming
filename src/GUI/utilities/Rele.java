package GUI.utilities;

import java.io.BufferedWriter;
import java.io.IOException;

public class Rele {
	private Runtime r = Runtime.getRuntime();
	private Process p = null;
	private String fromC = null;
	private BufferedWriter output;
	
	public Rele() {
	/*
		File f = new File(System.getProperty("user.dir"));
		System.out.println(f.getAbsolutePath());
		try {
			p = r.exec("c/Rele.out");
		} catch (Exception e) {
			e.printStackTrace();
		}
		output = new BufferedWriter(new OutputStreamWriter(p.getOutputStream())); 
		*/
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
}
