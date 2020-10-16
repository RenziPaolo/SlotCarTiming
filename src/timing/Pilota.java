package timing;

public class Pilota {
	private String nomePilota;
	private Corsia corsia;
	
	public Pilota(String nomePilota,Corsia corsia) {
		this.nomePilota = nomePilota;
		this.corsia = corsia;
	}
	
	public void setCorsia(Corsia corsia) {
		this.corsia = corsia;
	}
	
	public String getNomePilota() {
		return nomePilota;
	}

	public Corsia getCorsia() {
		return corsia;
	}
	
}
