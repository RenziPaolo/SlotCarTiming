package timing;

import java.util.ArrayList;
import java.util.Collections;

import GUI.Colore;

public class Corsia {
    private ArrayList<Float> laps = new ArrayList<Float>();
    private float lastLap;
    private int nome;
    private Colore colore;
    
    public Corsia(int nome,Colore colore) {
    	this.nome = nome;
    	this.colore = colore;
    }
    
    public Colore getColore() {
    	return colore;
    }
    
    public void setLap(float lapTime){
        laps.add(lapTime);
        lastLap = lapTime;
    }
    public float getUltimoGiro(){
        return lastLap;    
    }
    public ArrayList<Float> getGiri(){
        return laps;    
    }
    public int getNumeroDiGiri(){
    	return laps.size();
    }
    
    public float getGiroVeloce(){
    	try {
        return Collections.min(laps);
        } catch(java.util.NoSuchElementException e) {
        return 0;
        }
    }
    
    public float getTimeFromStart() {
    	return (float) laps.stream().mapToDouble(x->(double)x).sum();
    }
    
    public int getNome() {
    	return nome;
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (obj instanceof Corsia && ((Corsia) obj).getNome() == nome)
    		return true;
    	return false;
    }
    
}
