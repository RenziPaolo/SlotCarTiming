package timing;

import java.util.ArrayList;

import GUI.Event;

public abstract class EventT {
	protected ArrayList<Driver> drivers;
	private Event GUI;
	protected int currentHeat;
	protected int currentManche;
	
	public EventT(ArrayList<Driver> driver, Event GUI,int currentHeat) {
		this.drivers = driver;
		this.GUI = GUI;
		this.currentHeat = currentHeat;
		this.currentManche = 0;
	}
	
	public EventT(ArrayList<Driver> driver, Event GUI,int currentHeat, int currentManche) {
		this.drivers = driver;
		this.GUI = GUI;
		this.currentHeat = currentHeat;
		this.currentManche = currentManche;
	}
	
    public void updatePilota(int numCorsia,Float tempo) {
    	for (int i = 0;i<drivers.size();i++) {
    		Driver driver = drivers.get(i);
	    	if (driver.getHeat() == currentHeat && driver.getselectedLane()==numCorsia) {
		    Lane lane = drivers.get(i).getLanes()[currentManche];
		    lane.setLap(tempo);
		    GUI.update(lane);
		    }
    	}
     }
    
    public ArrayList<Driver> getPiloti(){
    	return drivers;
    }

	public abstract Float[][][] getClassification();

	public int getCurrentHeat() {
		return currentHeat;
	}

	public void setCurrentHeat(int currentHeat) {
		this.currentHeat = currentHeat;
	}
    
}
