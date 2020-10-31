package GUI;

import javafx.event.ActionEvent;
import timing.Corsia;

public interface Event {
	public void update(Corsia corsia);
	public void stop(ActionEvent e);
	public void start(ActionEvent e);
	public void exit();
}