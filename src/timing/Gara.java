package timing;

import java.util.ArrayList;
import GUI.Event;

public class Gara extends Evento{
	
	private int currentManche;
	
	public Gara(ArrayList<Pilota> drivers, Event GUI,int currentHeat,int currentManche) {
		super(drivers,GUI,currentHeat);
		this.currentManche = currentManche;
	}

	@Override
	public Float[][] getClassification() {
		return null;
	}

	public int getCurrentManche() {
		return currentManche;
	}
	
	public void setCurrentManche(int currentManche) {
		for (int j = 0;j<piloti.size();j++) {
			Pilota driver = piloti.get(j);
			if (driver.getHeat()==currentHeat) 
				driver.setselectedLane(currentManche);
		}
		
		this.currentManche = currentManche;
	}
	
	private void resetManche() {
		currentManche = 1;
	}
	
	@Override
	public void setCurrentHeat(int currentHeat) {
		this.currentHeat = currentHeat;
		resetManche();
		setCurrentManche(1);
	}
}
