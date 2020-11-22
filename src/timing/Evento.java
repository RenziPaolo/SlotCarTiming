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
    	for (int i = 0;i<piloti.size();i++) {
    		Pilota pilota = piloti.get(i);
	    	if (pilota.getHeat() == currentHeat && pilota.getselectedLane()==numCorsia) {
		    Corsia corsia = piloti.get(i).getLanes()[piloti.get(i).getselectedLane()-1];
		    corsia.setLap(tempo);
		    GUI.update(corsia);
		    }
    	}
     }
    
    public ArrayList<Pilota> getPiloti(){
    	return piloti;
    }

	public abstract Float[][][] getClassification();

	public int getCurrentHeat() {
		return currentHeat;
	}

	public void setCurrentHeat(int currentHeat) {
		this.currentHeat = currentHeat;
	}
    
}
