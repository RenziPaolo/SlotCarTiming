package timing;

import java.util.ArrayList;
import java.util.Collections;

import GUI.utilities.ColorLane;

public class Lane {
    private ArrayList<Float> laps = new ArrayList<Float>();
    private float lastLap;
    private int nome;
    private ColorLane colore;
    private Driver driver;
    
    public Lane(int name,ColorLane color,Driver driver) {
    	this.nome = name;
    	this.colore = color;
    	this.driver = driver;
    }
    
    public ColorLane getColore() {
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
    	if (obj instanceof Lane && ((Lane) obj).getNome() == nome)
    		return true;
    	return false;
    }

	public Driver getDriver() {
		return driver;
	}
    
}
