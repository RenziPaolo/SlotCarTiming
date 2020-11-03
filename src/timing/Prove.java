package timing;

import java.util.ArrayList;
import java.util.HashMap;

import GUI.Event;

public class Prove extends Evento{

	public Prove(ArrayList<Pilota> piloti, Event GUI,int currentHeat) {
		super(piloti,GUI,currentHeat);
	}

	@Override
	public Float[][] getClassification() {
		return null;
	}

	@Override
	public void swap() {
		
		
	}

}
