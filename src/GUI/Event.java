package GUI;

import javafx.event.ActionEvent;
import timing.Lane;

public interface Event {
	public void update(Lane lane);
	public void stop(ActionEvent e);
	public void start(ActionEvent e);
	public void exit();
	public void error();
}