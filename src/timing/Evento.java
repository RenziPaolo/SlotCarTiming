package timing;

import java.util.ArrayList;

import GUI.Event;

public abstract class Evento {
	protected ArrayList<Pilota> piloti;
	private Event GUI;
	protected int currentHeat;
	
	public Evento(ArrayList<Pilota> piloti, Event GUI,int currentHeat) {
		this.piloti = piloti;
		this.GUI = GUI;
		this.currentHeat = currentHeat;
	}
	
    public void updatePilota(int numCorsia,Float tempo) {
    	if (piloti.get(numCorsia-1).getHeat() == currentHeat) {
	    Corsia corsia = piloti.get(numCorsia-1).getCorsia();
	    corsia.setLap(tempo);
	    GUI.update(corsia);
	    }
     }
    
    public ArrayList<Pilota> getPiloti(){
    	return piloti;
    }
    
    public abstract void swap();

	public abstract Float[][] getClassification();

	public int getCurrentHeat() {
		return currentHeat;
	}

	public void setCurrentHeat(int currentHeat) {
		this.currentHeat = currentHeat;
	}
    
}
