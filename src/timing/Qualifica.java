package timing;

import java.util.ArrayList;
import java.util.Arrays;
import GUI.Event;

public class Qualifica extends Evento{
	
	int currentDriver;

	public Qualifica(ArrayList<Pilota> piloti, Event GUI,int currentHeat) {
		super(piloti,GUI,currentHeat);
	}

	@Override
	public Float[][][] getClassification() {
		Float[][][] classification = piloti.stream().map(x -> new Float[][] {new Float[] {x.getId()} , new Float[] {x.getLanes()[x.getselectedLane()].getGiroVeloce()}}).toArray(size -> new Float[size][2][1]);
		Arrays.sort(classification, (a, b) -> Float.compare(a[1][0], b[1][0]));
		return classification;
	}
	
	public void setCurrentDriver(Pilota currentDriver) {
		this.currentDriver = piloti.indexOf(currentDriver);
		piloti.get(this.currentDriver).setHeat(1);
	}

	public void deselectCurrentDriver() {
		piloti.get(this.currentDriver).setHeat(0);
		
	}

}
