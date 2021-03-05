package timing;

import java.util.ArrayList;
import java.util.Arrays;
import GUI.Event;

public class QualifingT extends EventT{
	
	int currentDriver;

	public QualifingT(ArrayList<Driver> piloti, Event GUI,int currentHeat) {
		super(piloti,GUI,currentHeat);
	}

	@Override

	public int[] getClassification() {
		Float[][][] classification = drivers.stream().map(x -> new Float[][] {new Float[] {} , new Float[] {x.getselectedLane().getGiroVeloce()}}).toArray(size -> new Float[size][2][1]);
		Arrays.sort(classification, (a, b) -> Float.compare(a[1][0], b[1][0]));

		return null;
	}
	
	class Classification{
		
		class SimpleDriver{
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
			
		}
		
		
		private SimpleDriver[] drivers;
		
		public Classification(ArrayList<Driver> drivers) {
			this.drivers = new SimpleDriver[drivers.size()];
			for(int i = 0; i<drivers.size();i++) {
				this.drivers[i] = new SimpleDriver(drivers.get(i).getId(), drivers.get(i).getselectedLane().getGiroVeloce());
			}
			Arrays.sort(this.drivers);
		}
		
		public int[] getClassificationID() {
			int[] ClassificationID = new int[drivers.length];
			for(int i = 0; i<drivers.length;i++) {
				ClassificationID[i] = drivers[i].getId();
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

}
