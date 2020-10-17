package timing;

import java.util.ArrayList;
import java.util.HashMap;

import GUI.Event;

public abstract class Evento {
	protected ArrayList<Pilota> piloti;
	private Event GUI;
	
	public Evento(ArrayList<Pilota> piloti, Event GUI) {
		this.piloti = piloti;
		this.GUI = GUI;
	}
	
    public void updatePilota(int numCorsia,Float tempo) {
    	Corsia corsia = piloti.get(numCorsia).getCorsia();
    	corsia.setLap(tempo);
    	GUI.update(corsia);
    }
    
    public ArrayList<Pilota> getPiloti(){
    	return piloti;
    }
    
    public abstract void swap();

	public abstract Float[] getClassification();
    
}
