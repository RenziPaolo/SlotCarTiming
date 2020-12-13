package timing;

import GUI.Colore;
import GUI.Data;

public class Driver {
	private String nameDriver;
	private int heat;
	private int selectedLane;
	private Lane[] lanes;
	private float id;
	
	public Driver(String nomePilota,Float id,int lane,int heat) {
		this.nameDriver = nomePilota;
		this.selectedLane = lane;
		this.heat = heat;
		this.id = id;
		Data data = new Data();
		int numLanes = data.getNumCorsie();
		Colore[] colors = data.getColori();
		this.lanes = new Lane[numLanes];
		int[] swap = data.getSwap();
		int j = 0;
		for (int i = lane;j<numLanes;i=swap[i]) {
			lanes[j] = new Lane(i,colors[i]);
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

	public Lane[] getLanes() {
		return lanes;
	}
	
	public float compareToDriver(){
		return (float) 0.0;
	}
}
