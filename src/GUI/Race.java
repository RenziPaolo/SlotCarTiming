package GUI;

import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import timing.Corsia;
import timing.Sensore;

public class Race implements Event{
	private Sensore sensor;

	public Race() {}
	
	public Race(Pane racePane) {
		// TODO Auto-generated constructor stub
	}

	public void exit(ActionEvent exit) throws Exception {
		System.exit(0);
	}
	
	public void addSensor(Sensore sensor) {
		this.sensor = sensor;
	}
	
	@Override
	public void update(Corsia corsia) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop(ActionEvent e) {
		sensor.Stop();
	}

	@Override
	public void start(ActionEvent e) {
		sensor.Start();
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		
	}

}
