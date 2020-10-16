package testing;

import java.util.ArrayList;
import java.util.EventObject;

import timing.Sensore;

public class TimingEvent extends EventObject{
	private ArrayList<Sensore> listeners = new ArrayList<Sensore>(); 
	private int corsia;
	
	public TimingEvent(Object source, int corsia) {
		super(source);
		this.corsia = corsia;
	}
	
	public int getCorsia() {
		return corsia;
	}

	public void addSensore(Sensore listener) {
		listeners.add(listener);
	}
	
	public void setCorsia(int corsia) {
		this.corsia = corsia;
		
		for (int i = listeners.size()-1;i>=0;i--) {
			listeners.get(i).setTime(this);
		}
	}
	
}
