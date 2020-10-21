package timing;

import java.util.ArrayList;

import GUI.Event;

public class Qualifica extends Evento{

	public Qualifica(ArrayList<Pilota> piloti, Event GUI) {
		super(piloti,GUI);
	}

	@Override
	public Float[] getClassification() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void swap() {
		// TODO Auto-generated method stub
		
	}
	
	public void setCurrentDriver(Pilota currentDriver) {
		// TODO Auto-generated method stub
	}
	
//	public Pilota getCurrentDriver() {
//		return null;
//	}

	public void deselectCurrentDriver() {
		// TODO Auto-generated method stub
		
	}

}
