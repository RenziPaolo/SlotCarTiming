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
		for (int i = 0;i<numLanes;i++)
			lanes[i] = new Corsia(i,colors[i]);
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
