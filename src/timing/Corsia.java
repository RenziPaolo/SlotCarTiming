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
        return Collections.min(laps);
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
