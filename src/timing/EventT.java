package timing;

import java.util.ArrayList;

import GUI.Event;

public abstract class EventT {
	protected static ArrayList<Driver> drivers;
	protected Event GUI;
	protected int currentHeat;
	protected int currentManche;
	
	public EventT(ArrayList<Driver> driver, Event GUI,int currentHeat) {
		EventT.drivers = driver;
		this.GUI = GUI;
		this.currentHeat = currentHeat;
		this.currentManche = 0;
	}
	
	public EventT(ArrayList<Driver> driver, Event GUI,int currentHeat, int currentManche) {
		EventT.drivers = driver;
		this.GUI = GUI;
		this.currentHeat = currentHeat;
		this.currentManche = currentManche;
	}
	
    public void updatePilota(int numCorsia,Float tempo) {
    	for (int i = 0;i<drivers.size();i++) {
    		Driver driver = drivers.get(i);
	    	if (driver.getHeat() == currentHeat && driver.getselectedLaneIndex()==numCorsia) {
			    Lane lane = driver.getLanes()[currentHeat];
			    lane.setLap(tempo);
			    GUI.update(lane);
		    }
    	}
     }
    
    public void error() {
    	GUI.error();
    }
    
    public ArrayList<Driver> getPiloti(){
    	return drivers;
    }

	public abstract int[] getClassification();

	public int getCurrentHeat() {
		return currentHeat;
	}

	public void setCurrentHeat(int currentHeat) {
		this.currentHeat = currentHeat;
	}
    
}
