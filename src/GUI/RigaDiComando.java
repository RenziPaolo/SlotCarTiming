package GUI;

import javafx.application.Application;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class RigaDiComando extends Application{
    public void draw(){
    	launch();
    }
	@Override
	public void start(Stage stage) throws Exception {
		
		
		stage.setTitle("SlotCar timing");
		stage.setWidth(1280);
		stage.setHeight(720);
		Button exit = new Button("esci");
		Button home = new Button("home");
		Button home1 = new Button("home");
		Button home2 = new Button("home");
		Button home3 = new Button("home");

		home.setCursor(Cursor.HAND);
		home1.setCursor(Cursor.HAND);
		home2.setCursor(Cursor.HAND);
		home3.setCursor(Cursor.HAND);


		Pane tipiEventi = new HBox();
		Button gara = new Button("Gara");
		Button prove = new Button("Prove");
		gara.setPrefSize(300, 150);
		prove.setPrefSize(300, 150);
		gara.setCursor(Cursor.HAND);
		prove.setCursor(Cursor.HAND);
		gara.setFont(new Font(null, 70));
		prove.setFont(new Font(null, 70));
		tipiEventi.getChildren().add(gara);
		tipiEventi.getChildren().add(prove);
		tipiEventi.getChildren().add(home);
		tipiEventi.getChildren().add(exit);
		Scene mainMenu = new Scene(tipiEventi);
		
		
		Pane settingsGara = new FlowPane();
		Button test = new Button("test");
		test.setCursor(Cursor.HAND);
		settingsGara.getChildren().add(test);
		settingsGara.getChildren().add(home1);
		Scene scenaSettingsGara = new Scene(settingsGara);
		
		Pane settingsProve = new FlowPane();
		TextField durata = new TextField("Durata(non specificare per prove senza limiti)");
		durata.setPrefSize(500, 20);
		Button nextProve = new Button("partenza");
		nextProve.setCursor(Cursor.HAND);
		Text test1 = new Text();
		test1.setText("  s");
		settingsProve.getChildren().add(durata);
		settingsProve.getChildren().add(test1);
		settingsProve.getChildren().add(nextProve);
		settingsProve.getChildren().add(home2);
		Scene scenaSettingsProve = new Scene(settingsProve);
		
		Pane provePanel = new FlowPane();
		provePanel.getChildren().add(new Rectangle(500,150,Color.YELLOW));
		provePanel.getChildren().add(new Rectangle(500,150,Color.GREEN));
		provePanel.getChildren().add(new Rectangle(500,150,Color.WHITE));
		provePanel.getChildren().add(new Rectangle(500,150,Color.RED));
		provePanel.getChildren().add(new Rectangle(500,150,Color.BLUE));
		provePanel.getChildren().add(new Rectangle(500,150,Color.ORANGE));
		provePanel.getChildren().add(home3);
		Scene scenaProve = new Scene(provePanel);

		
		stage.setScene(mainMenu);
		stage.show();
		gara.setOnMouseClicked(e-> stage.setScene(scenaSettingsGara));
		prove.setOnMouseClicked(e-> stage.setScene(scenaSettingsProve));
		nextProve.setOnMouseClicked(e-> stage.setScene(scenaProve));
		exit.setOnMouseClicked(e-> System.exit(10));
		home.setOnMouseClicked(e-> stage.setScene(mainMenu));
		home1.setOnMouseClicked(e-> stage.setScene(mainMenu));
		home2.setOnMouseClicked(e-> stage.setScene(mainMenu));
		home3.setOnMouseClicked(e-> {stage.setScene(mainMenu);
									provePanel.getChildren().remove(provePanel.getChildren().size()-1);});
		nextProve.setOnMouseClicked(e->
		{String min = durata.getText();
		if (Integer.valueOf(min)>0)
		{
			
			Thread taskThread = new Thread(new Runnable() {
				@Override
				public void run() {
					MyTimer timer = new MyTimer(Integer.valueOf(min)%60,Integer.valueOf(min)/60,Integer.valueOf(min)/3600,new Text(),new Practice());
					timer.toString();
				}
			});
			
			
			taskThread.start();;
			stage.setScene(scenaProve);
		}});
		
		
		
		
	}
	public static <T> void record(T variabile, T valore)
	{
		variabile = valore;
		System.out.println(variabile);
	}
	
    public void update(){
    }
}