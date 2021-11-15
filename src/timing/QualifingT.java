package timing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import GUI.Event;
import GUI.utilities.Data;

public class QualifingT extends EventT{
	
	int currentDriver;

	public QualifingT(ArrayList<Driver> piloti, Event GUI,int currentHeat) {
		super(piloti,GUI,currentHeat);
	}

	@Override
	public int[] getClassification() {
		return new Classification(drivers).getClassificationID();
	}
	
	public float[] getClassificationLap() {
		return new Classification(drivers).getClassificationLap();
	}
	
	class Classification{
		
		class SimpleDriver implements Comparable<SimpleDriver>{
			private int id;
			private float bestLapTime;
			
			public SimpleDriver(int id, float bestLapTime){
				this.id = id;
				this.bestLapTime = bestLapTime;
			}
			
			public int getId() {
				return id;
			} 
			public float getBestLapTime() {
				return bestLapTime;
			}

			@Override
			public int compareTo(SimpleDriver o) {
				return (int)(o.bestLapTime-this.bestLapTime);
			}
			
		}
		
		private SimpleDriver[] drivers;
		
		public Classification(ArrayList<Driver> drivers) {
			this.drivers = new SimpleDriver[drivers.size()];
			for(int i = 0; i<drivers.size();i++) {
				this.drivers[i] = new SimpleDriver(drivers.get(i).getId(), drivers.get(i).getLanes()[new Data().getCodeQualyLane()].getGiroVeloce());
			}
			Arrays.sort(this.drivers,(a,b) -> (int)(a.bestLapTime-b.bestLapTime));
			Collections.reverse(Arrays.asList(this.drivers));
		}
		
		public int[] getClassificationID() {
			int[] ClassificationID = new int[drivers.length];
			for(int i = 0; i<drivers.length;i++) {
				ClassificationID[i] = drivers[i].getId();
			}
			return ClassificationID;
		}
		public float[] getClassificationLap() {
			float[] ClassificationID = new float[drivers.length];
			for(int i = 0; i<drivers.length;i++) {
				ClassificationID[i] = drivers[i].getBestLapTime();
			}
			return ClassificationID;
		}

	}
	
	public void setCurrentDriver(Driver currentDriver) {
		this.currentDriver = drivers.indexOf(currentDriver);
		drivers.get(this.currentDriver).setHeat(1);
	}

	public void deselectCurrentDriver() {
		drivers.get(this.currentDriver).setHeat(0);
	}
	
	@Override
    public void updatePilota(int numCorsia,Float tempo) {
    	for (int i = 0;i<drivers.size();i++) {
    		Driver driver = drivers.get(i);
	    	if (driver.getHeat() == currentHeat && driver.getselectedLaneIndex()==numCorsia) {
			    Lane lane = driver.getLanes()[new Data().getCodeQualyLane()];
			    lane.setLap(tempo);
			    GUI.update(lane);
		    }
    	}
     }

}
