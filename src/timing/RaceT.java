package timing;

import java.util.ArrayList;
import java.util.Arrays;
import GUI.Event;

public class RaceT extends EventT{
	
	public RaceT(ArrayList<Driver> drivers, Event GUI,int currentHeat,int currentManche) {
		super(drivers,GUI,currentHeat,currentManche);
	}
	
	@Override
	public int[] getClassification() {
		return new Classification(drivers).getClassificationID();
	}
	
	public float[] getDistance() {
		return new Classification(drivers).getDistance();
	}
//		Float[][][] classification = drivers.stream().map(x -> 
//		new Float[][]
//				{
//					new Float[] {x.getId()},
//					Arrays.stream(x.getLanes()).map(y -> (float)y.getNumeroDiGiri()).toArray(size -> new Float[size]),
//					new Float[] {(float) Arrays.stream(x.getLanes()).mapToDouble(y -> (double)y.getTimeFromStart()).sum()},
//					new Float[] {(float) Arrays.stream(x.getLanes()).mapToDouble(y -> (double)y.getNumeroDiGiri()).sum()}
//				}
//				).toArray(size -> new Float[size][5][new Data().getNumCorsie()]);
//		
//		Arrays.sort(classification, (a, b) -> {
//			
//			if (a[3][0] != b[3][0])
//				return Float.compare(a[3][0], b[3][0]);
//			else
//				return Float.compare(b[2][0], a[2][0]);
//		
//		}
//		);
//		for (int i = 0;i<drivers.size();i++) {
//			if (i!=0) {
//				if (classification[3][i-1][0] == classification[3][i][0]) {
//					classification[4][i][0] = classification[2][i][0] - classification[2][i-1][0];
//				} else {
//					classification[4][i][0] = classification[3][i][0];
//				}
//			} else {
//				classification[4][i][0] = (float) 0;
//			}
//		}
//		
//		return classification;
	
	
	class Classification{
		
		class SimpleDriver{
			private int id;
			private int numberOfLaps;
			private float sumOflapTimes;
			
<<<<<<< main
			public SimpleDriver(int id, int numberOfLaps, float sumOflapTimes){
				this.id = id;
				this.numberOfLaps = numberOfLaps;
				this.sumOflapTimes = sumOflapTimes;
=======
<<<<<<< HEAD
			public SimpleDriver(int id, int numberOfLaps, float sumOflapTimes){
				this.id = id;
				this.numberOfLaps = numberOfLaps;
				this.sumOflapTimes = sumOflapTimes;
=======
			if (a[3][0] != b[3][0])
				return Float.compare(a[3][0], b[3][0]);
			else
				return Float.compare(b[2][0], a[2][0]);
		
		}
		);
		for (int i = 0;i<drivers.size();i++) {
			if (i!=0) {
				if (classification[i-1][3][0] == classification[i][3][0]) {
					classification[i][4][0] = classification[i][2][0] - classification[i-1][2][0];
				} else {
					classification[i][4][0] = classification[i][3][0];
				}
			} else {
				classification[i][4][0] = (float) 0;
>>>>>>> refs/remotes/origin/test
>>>>>>> ac4e6e2 Merge remote-tracking branch 'origin/test' into test
			}
			
			public int getId() {
				return id;
			} 
			public int getNumberOfLaps() {
				return numberOfLaps;
			}
			public float getSumOflapTimes() {
				return sumOflapTimes;
			}
			
		}
		
		
		private SimpleDriver[] drivers;
		
		public Classification(ArrayList<Driver> drivers) {
			this.drivers = new SimpleDriver[drivers.size()];
			for(int i = 0; i<drivers.size();i++) {
				this.drivers[i] = new SimpleDriver(drivers.get(i).getId(), drivers.get(i).getselectedLane().getNumeroDiGiri(), drivers.get(i).getselectedLane().getTimeFromStart());
			}
			
			Arrays.sort(this.drivers, ((driver1, driver2)->{
				if (driver1.numberOfLaps != driver1.numberOfLaps)
					return Integer.compare(driver1.numberOfLaps, driver2.numberOfLaps);
				else
					return Float.compare(driver1.sumOflapTimes, driver2.sumOflapTimes);
			}));
			
		}
		
		public float[] getDistance() {
			float[] distances = new float[drivers.length];
			
			distances[0] = 0;
			for(int i = 1; i<drivers.length;i++) {
				if(drivers[i-1].getNumberOfLaps() == drivers[i].getNumberOfLaps()) {
					distances[i] = drivers[i-1].getSumOflapTimes() - drivers[i].getSumOflapTimes();
				} else {
					distances[i] = drivers[i-1].getNumberOfLaps() - drivers[i].getNumberOfLaps();
				}
			}
			return distances;
		}
		
		public int[] getClassificationID() {
			int[] ClassificationID = new int[drivers.length];
			for(int i = 0; i<drivers.length;i++) {
				ClassificationID[i] = drivers[i].getId();
			}
			return ClassificationID;
		}	
	}

	public int getCurrentManche() {
		return currentManche;
	}
	
	public void setCurrentManche(int currentManche) {
		for (int j = 0;j<drivers.size();j++) {
			Driver driver = drivers.get(j);
			if (driver.getHeat()==currentHeat) 
				driver.setselectedLane(currentManche);
		}
		
		this.currentManche = currentManche;
	}
	
	@Override
	public void setCurrentHeat(int currentHeat) {
		this.currentHeat = currentHeat;
		setCurrentManche(0);
	}
}
