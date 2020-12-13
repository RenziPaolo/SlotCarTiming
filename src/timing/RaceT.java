package timing;

import java.util.ArrayList;
import java.util.Arrays;
import GUI.Data;
import GUI.Event;

public class RaceT extends EventT{
	
	public RaceT(ArrayList<Driver> drivers, Event GUI,int currentHeat,int currentManche) {
		super(drivers,GUI,currentHeat,currentManche);
	}

	@Override
	public Float[][][] getClassification() {
		Float[][][] classification = drivers.stream().map(x -> 
		new Float[][]
				{
					new Float[] {x.getId()},
					Arrays.stream(x.getLanes()).map(y -> (float)y.getNumeroDiGiri()).toArray(size -> new Float[size]),
					new Float[] {(float) Arrays.stream(x.getLanes()).mapToDouble(y -> (double)y.getTimeFromStart()).sum()},
					new Float[] {(float) Arrays.stream(x.getLanes()).mapToDouble(y -> (double)y.getNumeroDiGiri()).sum()},
					new Float[] {}
				}
				).toArray(size -> new Float[size][4][new Data().getNumCorsie()]);
		
		Arrays.sort(classification, (a, b) -> {
			
			if (a[3][0] != b[3][0])
				return Float.compare(a[3][0], b[3][0]);
			else
				return Float.compare(b[2][0], a[2][0]);
		
		}
		);
		return classification;
	}

	public int getCurrentManche() {
		return currentManche;
	}
	
	public void setCurrentManche(int currentManche) {
		for (int j = 0;j<drivers.size();j++) {
			Driver driver = drivers.get(j);
			if (driver.getHeat()==currentHeat) 
				driver.setselectedLane(currentManche);
		}
		
		this.currentManche = currentManche;
	}
	
	@Override
	public void setCurrentHeat(int currentHeat) {
		this.currentHeat = currentHeat;
		setCurrentManche(0);
	}
}
