package GUI;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GUI {

	public void setScene(Pane parent,Stage stage) {
		Scene scene = new Scene(parent);
		System.out.println(scene);
		stage.setScene(scene);
	}
	
}
