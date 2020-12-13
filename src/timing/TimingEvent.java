package timing;

import java.util.ArrayList;
import java.util.EventObject;

public class TimingEvent extends EventObject{
	private static final long serialVersionUID = 1L;
	private ArrayList<Sensor> listeners = new ArrayList<Sensor>(); 
	private int corsia;
	
	public TimingEvent(Object source, int corsia) {
		super(source);
		this.corsia = corsia;
	}
	
	public int getCorsia() {
		return corsia;
	}

	public void addSensore(Sensor listener) {
		listeners.add(listener);
	}
	
	public void setCorsia() {
		for (int i = listeners.size()-1;i>=0;i--) {
			listeners.get(i).setTime(this);
		}
	}
	
}
