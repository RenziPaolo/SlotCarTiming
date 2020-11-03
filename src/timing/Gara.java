package timing;

import java.util.ArrayList;

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

	@Override
	public void swap() {
		// TODO Auto-generated method stub
		
	}
}
