package timing;

import java.util.ArrayList;
import GUI.Dati;
import GUI.Event;

public class Gara extends Evento{
	
	private int currentManche;
	
	public Gara(ArrayList<Pilota> piloti, Event GUI,int currentHeat,int currentManche) {
		super(piloti,GUI,currentHeat);
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
		Dati data = new Dati();
		int[] swapArray = data.getSwap();
		int numLanes = data.getNumCorsie();
		int swapIndex = currentManche-this.currentManche;
		
		if(swapIndex<0)
			swapIndex = numLanes+swapIndex;
		
		for (int i= 0;i<swapIndex;i++)
			for (int j = 0;j<piloti.size();j++) {
				Pilota driver = piloti.get(j);
				if (driver.getHeat()==currentHeat) 
					driver.setselectedLane(swapArray[driver.getselectedLane()-1]);
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
		setCurrentManche(currentManche);
	}
}
