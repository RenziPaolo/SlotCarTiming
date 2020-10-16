package timing;

import java.util.HashMap;

import GUI.Event;

public class Qualifica extends Evento{

	public Qualifica(HashMap<Integer, Pilota> piloti, Event GUI) {
		super(piloti,GUI);
	}

	@Override
	public Float[] getClassification() {
		piloti.forEach((key,value) -> value.getCorsia().getGiroVeloce());
		System.out.println(piloti);
		return null;
	}

	@Override
	public void swap() {
		// TODO Auto-generated method stub
		
	}

}
