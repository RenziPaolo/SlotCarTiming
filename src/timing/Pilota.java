package timing;

public class Pilota {
	private String nameDriver;
	private int heat;
	private Corsia corsia;
	private float id;
	
	public Pilota(String nomePilota,Float id,Corsia corsia,int heat) {
		this.nameDriver = nomePilota;
		this.corsia = corsia;
		this.heat = heat;
		this.id = id;
	}
	
	public void setCorsia(Corsia corsia) {
		this.corsia = corsia;
	}
	
	public String getNomePilota() {
		return nameDriver;
	}

	public Corsia getCorsia() {
		return corsia;
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
}
