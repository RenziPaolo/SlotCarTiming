package timing;

public class Pilota {
	private String nameDriver;
	private int heat;
	private Corsia corsia;
	
	public Pilota(String nomePilota,Corsia corsia,int heat) {
		this.nameDriver = nomePilota;
		this.corsia = corsia;
		this.heat = heat;
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
	
	public void setHeat(int heat) {
		this.heat = heat;
	}
}
