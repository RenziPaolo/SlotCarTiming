package timing;

import java.util.HashMap;

import GUI.Event;

public class Prove extends Evento{

	public Prove(HashMap<Integer, Pilota> piloti, Event GUI) {
		super(piloti,GUI);
	}

	@Override
	public Float[] getClassifica() {
		return null;
	}

	@Override
	public void swap() {
		
		
	}

}
