package GUI.utilities;

import java.io.IOException;

import GUI.MainMenu;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Utilities {
	public static void setBackground(Pane pane,int heigth,int width,boolean numbers) {
		for (int i = 0; i<new Data().getNumCorsie();i++) {
			Rectangle rectangle = new Rectangle();
			float[] colorcode = new Data().getColori()[i].getRGB();
			rectangle.setFill(Color.color(colorcode[0], colorcode[1], colorcode[2]));
			rectangle.setLayoutX(0);
			rectangle.setLayoutY((i)*heigth);
			rectangle.setHeight(heigth);
			rectangle.setWidth(width);
			pane.getChildren().add(rectangle);
			if (numbers) {
				Text num = new Text();
				num.setLayoutX(5);
				num.setLayoutY(((i+1)*120-15));
				num.setFont(Font.font(new Data().getFont(),FontWeight.BOLD,(double)130));
				num.setText(""+(i+1));
				pane.getChildren().add(num);
			}
		}
	}
	
	public static void LightsTransition(Pane oldPane, Pane newPane) {
		Pane lights = new AnchorPane();
		try {
			lights = FXMLLoader.load(Utilities.class.getResource("FXML/Lights.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		new MainMenu().getStage().getScene().setRoot(lights);
		try {
			Thread.sleep(5100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		new MainMenu().getStage().getScene().setRoot(newPane);
	}
}
