package timing;

import java.util.EventListener;
import GUI.Dati;
import testing.TimingEvent;

public class Sensore implements EventListener {
	
	private float[] lastPass = new float[new Dati().getNumCorsie()];
	float[] provisional = new float[new Dati().getNumCorsie()];
	private Evento evento;
	private boolean go = true;
	private float minLapTime;
	
	public Sensore(Evento evento, float minLapTime) {
		for(int i = 0; i<new Dati().getNumCorsie();i++ ) {
			lastPass[i] = System.nanoTime();
		}
		this.evento = evento;
		this.minLapTime = minLapTime;
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
		evento.updatePilota(input.getCorsia(), lapTime);
		}
	}
	
	public Evento getEvento() {
		return evento;
	}
	
	public void Stop() {
		for(int i = 0; i<new Dati().getNumCorsie();i++ ) {
			provisional[i] = (System.nanoTime()-lastPass[i])/1000000000;
		}
		
		go = false;
	}
	
	public void Start() {
		for(int i = 0; i<new Dati().getNumCorsie();i++ ) {
			lastPass[i] = System.nanoTime();
		}
		go = true;
	}

	public void reset() {
		lastPass = new float[new Dati().getNumCorsie()];
		provisional = new float[new Dati().getNumCorsie()];
		for(int i = 0; i<new Dati().getNumCorsie();i++ ) {
			lastPass[i] = System.nanoTime();
		}
	}
	
}

