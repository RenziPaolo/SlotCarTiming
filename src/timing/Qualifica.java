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
<<<<<<< HEAD
	public Float[][][] getClassification() {
		Float[][][] classification = piloti.stream().map(x -> new Float[][] {new Float[] {x.getId()} , new Float[] {x.getLanes()[x.getselectedLane()].getGiroVeloce()}}).toArray(size -> new Float[size][2][1]);
		Arrays.sort(classification, (a, b) -> Float.compare(a[1][0], b[1][0]));
=======
	public Float[][] getClassification() {
		Float[][] classification = piloti.stream().map(x -> new Float[] {x.getId() ,x.getLanes()[x.getselectedLane()].getGiroVeloce()}).toArray(size -> new Float[size][2]);
		Arrays.sort(classification, (a, b) -> Float.compare(a[1], b[1]));
>>>>>>> branch 'main' of https://github.com/RenziPaolo/SlotCarTiming.git
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
