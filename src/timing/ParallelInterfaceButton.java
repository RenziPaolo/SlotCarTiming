package timing;

import com.diozero.devices.Button;
import GUI.Dati;

public class ParallelInterfaceButton {
	
	private static Button[] interfaces;
	private static TimingEvent[] events;
	
	public ParallelInterfaceButton(Sensore sensor) {
		Dati data = new Dati();
		interfaces = new Button[data.getNumCorsie()];
		events = new TimingEvent[data.getNumCorsie()];
		int[] sensorint = data.getRequiredSensor();
		for (int i = 0;i<sensorint.length;i++) {
			events[i] = new TimingEvent(new Object(),i);
			events[i].addSensore(sensor);
			interfaces[i] = new Button(sensorint[i]);
			interfaces[i].whenPressed(events[i]::setCorsia);
		}
		
	}
}