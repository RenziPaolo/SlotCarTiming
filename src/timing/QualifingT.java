package timing;

import java.util.ArrayList;
import java.util.Arrays;
import GUI.Event;

public class QualifingT extends EventT{
	
	int currentDriver;

	public QualifingT(ArrayList<Driver> piloti, Event GUI,int currentHeat) {
		super(piloti,GUI,currentHeat);
	}

	@Override

	public Float[][][] getClassification() {
		Float[][][] classification = drivers.stream().map(x -> new Float[][] {new Float[] {x.getId()} , new Float[] {x.getLanes()[x.getselectedLane()].getGiroVeloce()}}).toArray(size -> new Float[size][2][1]);
		Arrays.sort(classification, (a, b) -> Float.compare(a[1][0], b[1][0]));

		return classification;
	}
	
	public void setCurrentDriver(Driver currentDriver) {
		this.currentDriver = drivers.indexOf(currentDriver);
		drivers.get(this.currentDriver).setHeat(1);
	}

	public void deselectCurrentDriver() {
		drivers.get(this.currentDriver).setHeat(0);
		
	}

}
