package timing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import GUI.Event;

public class Qualifica extends Evento{
	
	int currentDriver;

	public Qualifica(ArrayList<Pilota> piloti, Event GUI) {
		super(piloti,GUI);
	}

	@Override
	public Float[][] getClassification() {
		Float[][] classification = piloti.stream().map(x -> new Float[] {x.getId() ,x.getCorsia().getGiroVeloce()}).toArray(size -> new Float[size][2]);
		Arrays.sort(classification, (a, b) -> Float.compare(a[1], b[1]));
		return classification;
	}

	@Override
	public void swap() {
		// TODO Auto-generated method stub
		
	}
	
	public void setCurrentDriver(Pilota currentDriver) {
		this.currentDriver = piloti.indexOf(currentDriver);
		piloti.get(this.currentDriver).setHeat(1);
	}
	
//	public Pilota getCurrentDriver() {
//		return null;
//	}

	public void deselectCurrentDriver() {
		piloti.get(this.currentDriver).setHeat(0);
		
	}

}
