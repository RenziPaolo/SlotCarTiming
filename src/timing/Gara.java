package timing;

import java.util.HashMap;

import GUI.Event;

public class Gara extends Evento{
	
	public Gara(HashMap<Integer, Pilota> piloti, Event GUI) {
		super(piloti,GUI);
	}

	@Override
	public Float[] getClassifica() {
		return null;
	}

	@Override
	public void swap() {
		// TODO Auto-generated method stub
		
	}
}
