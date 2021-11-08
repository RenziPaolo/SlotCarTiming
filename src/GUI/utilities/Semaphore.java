package GUI.utilities;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.fxml.Initializable;

public class Semaphore implements Initializable{
	@FXML private Circle first;
	@FXML private Circle second;
	@FXML private Circle third;
	@FXML private Circle fourth;
	@FXML private Circle fifth;
	
	public Semaphore() {}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		first.setFill(Paint.valueOf("Red"));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		second.setFill(Paint.valueOf("Red"));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		third.setFill(Paint.valueOf("Red"));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		fourth.setFill(Paint.valueOf("Red"));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		fifth.setFill(Paint.valueOf("Red"));
		
		first.setFill(Paint.valueOf("Black"));
		second.setFill(Paint.valueOf("Black"));
		third.setFill(Paint.valueOf("Black"));
		fourth.setFill(Paint.valueOf("Black"));
		fifth.setFill(Paint.valueOf("Black"));
		
	}
	
}
