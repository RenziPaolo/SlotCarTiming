package timing;

import GUI.Colore;
import GUI.Dati;

public class Pilota {
	private String nameDriver;
	private int heat;
	private int selectedLane;
	private Corsia[] lanes;
	private float id;
	
	public Pilota(String nomePilota,Float id,int lane,int heat) {
		this.nameDriver = nomePilota;
		this.selectedLane = lane;
		this.heat = heat;
		this.id = id;
		Dati data = new Dati();
		int numLanes = data.getNumCorsie();
		Colore[] colors = data.getColori();
		this.lanes = new Corsia[numLanes];
		int[] swap = data.getSwap();
		int j = 0;
		for (int i = lane;j<numLanes;i=swap[i]) {
			lanes[j] = new Corsia(i,colors[i]);
			j++;
		}
	}
	
	public void setselectedLane(int corsia) {
		this.selectedLane = corsia;
	}
	
	public String getNomePilota() {
		return nameDriver;
	}

	public int getselectedLane() {
		return selectedLane;
	}

	public int getHeat() {
		return heat;
	}
	
	public Float getId() {
		return id;
	}
	
	public void setHeat(int heat) {
		this.heat = heat;
	}

	public Corsia[] getLanes() {
		return lanes;
	}
}
