package timing;

import java.util.ArrayList;
import GUI.Event;

public class Test extends EventT{

	public Test(ArrayList<Driver> piloti, Event GUI,int currentHeat) {
		super(piloti,GUI,currentHeat);
	}

	@Override
	public Float[][][] getClassification() {
		return null;
	}

}
