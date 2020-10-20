package timing;

import java.util.ArrayList;

import GUI.Event;

public class Qualifica extends Evento{

	public Qualifica(ArrayList<Pilota> piloti, Event GUI) {
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
	
	public void setCurrentDriver(Pilota currentDriver) {
		
	}

}
