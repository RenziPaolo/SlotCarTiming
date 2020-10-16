package GUI;

import javafx.event.ActionEvent;
import timing.Corsia;
import timing.Sensore;

public class Qualifing implements Event{
	private Sensore sensor;
	
	@Override
	public void update(Corsia corsia) {
		// TODO Auto-generated method stub
		
	}
	
	public void addSensor(Sensore sensor) {
		this.sensor = sensor;
	}
	
	@Override
	public void stop(ActionEvent e) {
		sensor.Stop();
	}

	@Override
	public void start(ActionEvent e) {
		sensor.Start();
	}

}
