package timing;

import com.diozero.internal.provider.pigpioj.PigpioJDigitalInputDevice;;

public class ParallelInterfaceDevice {
	private PigpioJDigitalInputDevice sensor;
	
	public ParallelInterfaceDevice() {
	}

	public PigpioJDigitalInputDevice getSensor() {
		return sensor;
	}

	public void setSensor(PigpioJDigitalInputDevice sensor) {
		this.sensor = sensor;
	}
}
