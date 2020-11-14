package timing;

import java.util.ArrayList;

import GUI.Colore;
import GUI.Dati;
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
		Dati data = new Dati();
		int[] swap = data.getSwap();
		Colore[] colors = data.getColori();
		for (int i = 0;i<piloti.size();i++) {
			Pilota driver = piloti.get(i);
			if (driver.getHeat()==currentHeat) {
				driver.setCorsia(new Corsia(swap[driver.getCorsia().getNome()],colors[swap[driver.getCorsia().getNome()]]));
			}
			
		}
		
	}

	public int getCurrentManche() {
		return currentManche;
	}
}
