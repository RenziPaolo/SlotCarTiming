package GUI;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

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
	private static Colore qualifingLane;
	private static int qualifingPeriod;
	private static int mancheDuration;

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
	
	public static void setBackground(Pane practice,int heigth,int width) {
		for (int i = 0; i<numCorsie;i++) {
			Rectangle rettangolo = new Rectangle();
			float[] colorcode = colori[i].getRGB();
			rettangolo.setFill(Color.color(colorcode[0], colorcode[1], colorcode[2]));
			rettangolo.setLayoutX(0);
			rettangolo.setLayoutY((i)*heigth);
			rettangolo.setHeight(heigth);
			rettangolo.setWidth(width);
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
		if (period == 0) {
			try (FileChannel filelanes = (FileChannel) Files.newByteChannel(Path.of("settings practice.config"), StandardOpenOption.READ)){
				ByteBuffer buffer = ByteBuffer.allocate(4);
				filelanes.read(buffer);
				buffer.rewind();
				period = buffer.getInt();
				
			} catch (IOException e) {
				System.out.println("eccezione!!");
			}
		}
		return period;
	}

	public String getFont() {
		if (font == null) {
			try {
				font = Files.readString(Path.of("settings font.config"));
			} catch (IOException e) {
				System.out.println("eccezione!!");
			}
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

	public Colore getQualifingLane() {
		if (qualifingLane == null) {
			try (FileChannel filequaly = (FileChannel) Files.newByteChannel(Path.of("qualifing.config"), StandardOpenOption.READ)){
				ByteBuffer buffer = ByteBuffer.allocate(8);
				filequaly.read(buffer);
				buffer.rewind();
				int index = buffer.getInt(4);
				qualifingLane = Colore.values()[index-1];
				
			} catch (IOException e) {
				System.out.println("eccezione!!");
			}
		}
		
		return qualifingLane;
	}

	public int getQualifingPeriod() {
		if (qualifingPeriod == 0) {
			try (FileChannel filequaly = (FileChannel) Files.newByteChannel(Path.of("qualifing.config"), StandardOpenOption.READ)){
				ByteBuffer buffer = ByteBuffer.allocate(8);
				filequaly.read(buffer);
				buffer.rewind();
				qualifingPeriod = buffer.getInt();
				
			} catch (IOException e) {
				System.out.println("eccezione!!");
			}
		}
		
		return qualifingPeriod;
	}
	
	public int getMancheduration() {
		if (mancheDuration == 0) {
			try (FileChannel fileMancheDuration = (FileChannel) Files.newByteChannel(Path.of("settings MancheDuration.config"), StandardOpenOption.READ)){
				ByteBuffer buffer = ByteBuffer.allocate(8);
				fileMancheDuration.read(buffer);
				buffer.rewind();
				mancheDuration = buffer.getInt();
				
			} catch (IOException e) {
				System.out.println("eccezione!!");
			}
		}
		return mancheDuration;
	}

	public int getCodeQualyLane() {
		return Arrays.asList(colori).indexOf(qualifingLane);
	}
	
}
