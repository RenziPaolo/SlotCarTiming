package GUI;

public enum Colore {
	YELLOW(1),GREEN(2),WHITE(3),RED(4),BLUE(5),ORANGE(6),LIGHT_BLUE(7),DARK_GREEN(8),VIOLET(9);
	
	private int codice;
	
	Colore(int codice) {
		this.codice = codice;
	}
	
	public int getCodice() {
		return codice;
	}

	public float[] getRGB() {
		switch(codice){
		case 1: float[] test = {1,1,0};return test;
		case 2: float[] test1 = {0,(float)0.9,0};return test1;
		case 3: float[] test2 = {1,1,1};return test2;
		case 4: float[] test3 = {1,0,0};return test3;
		case 5: float[] test4 = {0,(float) 0.35,(float) 1};return test4;
		case 6: float[] test5 = {1,(float) 0.5,0};return test5;
		case 7: float[] test6 = {0,1,1};return test6;
		case 8: float[] test7 = {0,(float) 0.2,0};return test7;
		case 9: float[] test8 = {1,0,1};return test8;
		default: return null;
		}
	}
	
	public Colore getColore(int codice) {
		switch(codice){
		case 1: return YELLOW;
		case 2: return GREEN;
		case 3: return WHITE;
		case 4: return RED;
		case 5: return BLUE;
		case 6: return ORANGE;
		case 7: return LIGHT_BLUE;
		case 8: return DARK_GREEN;
		case 9: return VIOLET;		
		default: return null;
		}
	}
	
	public static int fromlanguage(int code,String color) {
		switch (code) {
		case 1	:switch(color){
			case "Giallo":return 1;
			case "Verde":return 2;
			case "Bianco":return 3;
			case "Rosso":return 4;
			case "Blu":return 5;
			case "Arancione":return 6;
			case "Azzurro":return 7;
			case "Verde scuro":return 8;
			case "Viola":return 9;	
			default: return 0;
			}
		default: return 0;
		}
	}
	
	public String toStringlanguage(int code) {
		switch (code) {
		case 1:toString();
		case 2:		
			switch(codice){
				case 1: return "Giallo";
				case 2: return "Verde";
				case 3: return "Bianco";
				case 4: return "Rosso";
				case 5: return "Blu";
				case 6: return "Arancione";
				case 7: return "Azzurro";
				case 8: return "Verde scuro";
				case 9: return "Viola";	
				default: return null;
			}
			
		default: return null;	
		}
	}
	
}
