package timing;

import java.util.ArrayList;
import java.util.HashMap;

import GUI.Event;

public class Gara extends Evento{
	
	public Gara(ArrayList<Pilota> piloti, Event GUI) {
		super(piloti,GUI);
	}

	@Override
	public Float[] getClassification() {
		return null;
	}

	@Override
	public void swap() {
		// TODO Auto-generated method stub
		
	}
}
