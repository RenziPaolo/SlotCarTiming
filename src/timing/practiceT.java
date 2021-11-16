package timing;

import java.util.ArrayList;

import GUI.Event;

public class practiceT extends EventT{

	public practiceT(ArrayList<Driver> driver, Event GUI, int currentHeat) {
		super(driver, GUI, currentHeat);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int[] getClassification() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
    public void updatePilota(int numCorsia,Float tempo) {
    	for (int i = 0;i<drivers.size();i++) {
    		Driver driver = drivers.get(i);
	    	if (driver.getHeat() == currentHeat && driver.getselectedLaneIndex()==numCorsia) {
			    Lane lane = driver.getLanes()[0];
			    lane.setLap(tempo);
			    GUI.update(lane);
		    }
    	}
     }

}
