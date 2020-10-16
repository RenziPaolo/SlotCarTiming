package timing;

import java.util.HashMap;

import GUI.Event;

public abstract class Evento {
	protected HashMap<Integer,Pilota> piloti;
	private Event GUI;
	
	public Evento(HashMap<Integer,Pilota> piloti, Event GUI) {
		this.piloti = piloti;
		this.GUI = GUI;
	}
	
    public abstract Float[] getClassifica();
	
    public void updatePilota(int numCorsia,Float tempo) {
    	Corsia corsia = piloti.get(numCorsia).getCorsia();
    	corsia.setLap(tempo);
    	GUI.update(corsia);
    }
    
    public HashMap<Integer,Pilota> getPiloti(){
    	return piloti;
    }
    
    public abstract void swap();
    
}
