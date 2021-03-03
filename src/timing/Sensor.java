package timing;

import java.util.EventListener;
import GUI.Data;

public class Sensor implements EventListener {
	
	private float[] lastPass = new float[new Data().getNumCorsie()];
	private float[] provisional = new float[new Data().getNumCorsie()];
	private EventT event;
	private boolean go = true;
	private float minLapTime;
	private CComunication cInterface;
	
	public Sensor(EventT evento, float minLapTime) {
		for(int i = 0; i<new Data().getNumCorsie();i++ ) {
			lastPass[i] = System.nanoTime();
		}
		this.event = evento;
		this.minLapTime = minLapTime;
		cInterface = new CComunication(this);
	}
	
	
	public void setTime(TimingEvent input) {
		int index = input.getCorsia();
		float lapTime = 0;
		if(provisional[index]==0 && go) {
			lapTime = (System.nanoTime()-lastPass[index])/1000000000;
		} else {
			if(provisional[index]!=0 && go) {
			float test =(System.nanoTime()-lastPass[index])/1000000000;
			lapTime = test+provisional[index];
			provisional[index] = 0;
			}
		}
		
		if (go && lapTime>minLapTime) {
		lastPass[index] = System.nanoTime();
		event.updatePilota(input.getCorsia(), lapTime);
		}
	}
	
	public void setTime(double lapTime, int lane) {
		if (go && lapTime>minLapTime) {
			event.updatePilota(lane, (float)lapTime);
		}
	}
	
	public EventT getEvento() {
		return event;
	}
	
	public void Stop() {
		for(int i = 0; i<new Data().getNumCorsie();i++ ) {
			provisional[i] = (System.nanoTime()-lastPass[i])/1000000000;
		}
		cInterface.stop();
		go = false;
	}
	
	public void Start() {
		for(int i = 0; i<new Data().getNumCorsie();i++ ) {
			lastPass[i] = System.nanoTime();
		}
		cInterface.go();
		cInterface.start();
		go = true;
	}

	public void reset() {
		lastPass = new float[new Data().getNumCorsie()];
		provisional = new float[new Data().getNumCorsie()];
		for(int i = 0; i<new Data().getNumCorsie();i++ ) {
			lastPass[i] = System.nanoTime();
		}
	}
	
}

