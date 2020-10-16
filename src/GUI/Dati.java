package GUI;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Dati {
	
	private static int numCorsie;
	private static Colore[] colori;
	private static int[] swap;
	private static float minLapTime;
	private static int period;
	private static String font;

	public Dati() {
		try(FileChannel fileLanes = (FileChannel) Files.newByteChannel(Path.of("settings lanes.config"), StandardOpenOption.READ)){
			numCorsie = (int)fileLanes.size()/2;
			colori = new Colore[numCorsie];
			swap = new int[numCorsie];
			ByteBuffer buffer = ByteBuffer.allocate(numCorsie*2);
			fileLanes.read(buffer);
			int dato;
			for (int i = 0; i<numCorsie*2;i++) {
				dato = (int)buffer.get(i);
				if (dato == 0) {
					System.out.println("eccezione!!");
					break;
				}
				if (i%2==0) {
					colori[i/2] = Colore.BLUE.getColore(dato);
				} else {
					swap[i/2] = dato;
				}
			}
			
		} catch(IOException e){
			System.out.println("eccezione!!");
		}
		try(FileChannel fileMinLapTime = (FileChannel) Files.newByteChannel(Path.of("settings minlaptime.config"), StandardOpenOption.READ)){
			ByteBuffer buffer = ByteBuffer.allocate(4);
			fileMinLapTime.read(buffer);
			buffer.rewind();
			minLapTime = buffer.getFloat();
		} catch(IOException e){
			System.out.println("eccezione!!");
		}
	}
	
	public static void setBackground(Pane practice) {
		for (int i = 0; i<numCorsie;i++) {
			Rectangle rettangolo = new Rectangle();
			float[] colorcode = colori[i].getRGB();
			rettangolo.setFill(Color.color(colorcode[0], colorcode[1], colorcode[2]));
			rettangolo.setLayoutX(0);
			rettangolo.setLayoutY((i)*120);
			rettangolo.setHeight(120);
			rettangolo.setWidth(500);
			practice.getChildren().add(rettangolo);
		}
	}
	
	public int getNumCorsie() {
		return numCorsie;
	}
	
	public Colore[] getColori() {
		return colori;
	}
	
	public int[] getSwap() {
		return swap;
	}
	
	public float getMinLapTime() {
		return minLapTime;
	}

	public int getPeriod() {
		try (FileChannel filelanes = (FileChannel) Files.newByteChannel(Path.of("settings practice.config"), StandardOpenOption.READ)){
			ByteBuffer buffer = ByteBuffer.allocate(4);
			filelanes.read(buffer);
			buffer.rewind();
			period = buffer.getInt();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return period;
	}

	public String getFont() {
		try {
			font = Files.readString(Path.of("settings font.config"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return font;
	}
	
	public static void error() {
		Text errore = new Text();
		errore.setText("inserire tutti i dati");
		errore.setLayoutY(500);
		errore.setLayoutX(300);
		errore.setFont(new Font(50));
		Pane pane = (Pane)new MainMenu().getStage().getScene().getRoot();
		pane.getChildren().add(errore);
	}
	
}
