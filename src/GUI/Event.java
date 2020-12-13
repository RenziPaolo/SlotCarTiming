package GUI;

import javafx.event.ActionEvent;
import timing.Lane;

public interface Event {
	public void update(Lane corsia);
	public void stop(ActionEvent e);
	public void start(ActionEvent e);
	public void exit();
}