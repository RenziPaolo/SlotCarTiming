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
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Data {
	
	private static int numLanes;
	private static ColorLane[] colors;
	private static int[] swap;
	private static float minLapTime;
	private static int period;
	private static String font;
	private static ColorLane qualifingLane;
	private static int qualifingPeriod;
	private static int mancheDuration;
	private static int[] requiredSensor;
	private static int[][] additionalSensors;
	private static int[] rele;
	
	public static void setBackground(Pane pane,int heigth,int width,boolean numbers) {
		for (int i = 0; i<numLanes;i++) {
			Rectangle rectangle = new Rectangle();
			float[] colorcode = colors[i].getRGB();
			rectangle.setFill(Color.color(colorcode[0], colorcode[1], colorcode[2]));
			rectangle.setLayoutX(0);
			rectangle.setLayoutY((i)*heigth);
			rectangle.setHeight(heigth);
			rectangle.setWidth(width);
			pane.getChildren().add(rectangle);
			if (numbers) {
				Text num = new Text();
				num.setLayoutX(0);
				num.setLayoutY(((i+1)*120-20));
				num.setFont(Font.font(new Data().getFont(),FontWeight.BOLD,(double)130));
				num.setText(""+(i+1));
				pane.getChildren().add(num);
			}
		}
	}
	
	public int getNumCorsie() {
		if (numLanes==0)
			try(FileChannel fileLanes = (FileChannel) Files.newByteChannel(Path.of("settings lanes.config"), StandardOpenOption.READ)){
				numLanes = (int)fileLanes.size()/2;
				colors = new ColorLane[numLanes];
				swap = new int[numLanes];
				ByteBuffer buffer = ByteBuffer.allocate(numLanes*2);
				fileLanes.read(buffer);
				int dato;
				for (int i = 0; i<numLanes*2;i++) {
					dato = (int)buffer.get(i);
					if (i%2==0) {
						colors[i/2] = ColorLane.BLUE.getColore(dato);
					} else {
						swap[i/2] = dato;
					}
				}
			
			} catch(IOException e){
				System.out.println("eccezione!!");
			}
		return numLanes;
	}
	
	public ColorLane[] getColori() {
		if (colors == null) {
			try(FileChannel fileLanes = (FileChannel) Files.newByteChannel(Path.of("settings lanes.config"), StandardOpenOption.READ)){
				numLanes = (int)fileLanes.size()/2;
				colors = new ColorLane[numLanes];
				swap = new int[numLanes];
				ByteBuffer buffer = ByteBuffer.allocate(numLanes*2);
				fileLanes.read(buffer);
				int dato;
				for (int i = 0; i<numLanes*2;i++) {
					dato = (int)buffer.get(i);
					if (i%2==0) {
						colors[i/2] = ColorLane.BLUE.getColore(dato);
					} else {
						swap[i/2] = dato;
					}
				}
			
			} catch(IOException e){
				System.out.println("eccezione!!");
			}
		}
		return colors;
	}
	
	public int[] getSwap() {
		if (swap==null) {
			try(FileChannel fileLanes = (FileChannel) Files.newByteChannel(Path.of("settings lanes.config"), StandardOpenOption.READ)){
				numLanes = (int)fileLanes.size()/2;
				colors = new ColorLane[numLanes];
				swap = new int[numLanes];
				ByteBuffer buffer = ByteBuffer.allocate(numLanes*2);
				fileLanes.read(buffer);
				int dato;
				for (int i = 0; i<numLanes*2;i++) {
					dato = (int)buffer.get(i);
					if (i%2==0) {
						colors[i/2] = ColorLane.BLUE.getColore(dato);
					} else {
						swap[i/2] = dato;
					}
				}
			
			} catch(IOException e){
				System.out.println("eccezione!!");
			}
		}
		return swap;
	}
	
	public float getMinLapTime() {
		if (minLapTime == 0) {
			try(FileChannel fileMinLapTime = (FileChannel) Files.newByteChannel(Path.of("settings minlaptime.config"), StandardOpenOption.READ)){
				ByteBuffer buffer = ByteBuffer.allocate(16);
				fileMinLapTime.read(buffer);
				buffer.rewind();
				byte[] destination = new byte[16];
				buffer.get(destination);
				String test = new String(destination);
				System.out.println(test);
				minLapTime = Float.valueOf(test);
			} catch(Exception e){
				e.printStackTrace();
				System.out.println("eccezione!!");
			}
		}
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
	
	public static void error(String errorText) {
		Text errore = new Text();
		errore.setText(errorText);
		errore.setLayoutY(500);
		errore.setLayoutX(300);
		errore.setFont(new Font(50));
		Pane pane = (Pane)new MainMenu().getStage().getScene().getRoot();
		if (!pane.getChildren().contains(errore)) {
			pane.getChildren().add(errore);
		}
	}

	public ColorLane getQualifingLane() {
		if (qualifingLane == null) {
			try (FileChannel filequaly = (FileChannel) Files.newByteChannel(Path.of("qualifing.config"), StandardOpenOption.READ)){
				ByteBuffer buffer = ByteBuffer.allocate(8);
				filequaly.read(buffer);
				buffer.rewind();
				int index = buffer.getInt(4);
				qualifingLane = ColorLane.values()[index-1];
				
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
				
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("eccezione!!");
			}
		}
		return mancheDuration;
	}

	public int getCodeQualyLane() {
		return Arrays.asList(colors).indexOf(qualifingLane);
	}
	
	public int[] getRequiredSensor() {
		if (requiredSensor==null) {
			try(FileChannel fileRequiredsensors = (FileChannel) Files.newByteChannel(Path.of("settings Requiredsensors.config"), StandardOpenOption.READ)){
				requiredSensor = new int[getNumCorsie()];
				rele = new int[numLanes];
				ByteBuffer buffer = ByteBuffer.allocate(numLanes*8);
				fileRequiredsensors.read(buffer);
				int dato;
				for (int i = 0; i<numLanes*8;i+=4) {
					dato = (int)buffer.getInt(i);
					if (i%8==0) {
						requiredSensor[i/8] = dato;
					} else {
						rele[i/8] = dato;
					}
				}
			
			} catch(IOException e){
				System.out.println("eccezione!!");
			}
		}
		return requiredSensor;
	}
	
	public int[] getRele() {
		if (rele==null) {
			try(FileChannel fileRequiredsensors = (FileChannel) Files.newByteChannel(Path.of("settings Requiredsensors.config"), StandardOpenOption.READ)){
				requiredSensor = new int[getNumCorsie()];
				rele = new int[numLanes];
				ByteBuffer buffer = ByteBuffer.allocate(numLanes*8);
				fileRequiredsensors.read(buffer);
				int dato;
				for (int i = 0; i<numLanes*8;i++) {
					dato = (int)buffer.get(i);
					if (i%2==0) {
						requiredSensor[i/2] = dato;
					} else {
						rele[i/2] = dato;
					}
				}
			
			} catch(IOException e){
				System.out.println("eccezione!!");
			}
		}
		return rele;
	}
	
	public int[] getAddtionalSensor() {
		if (requiredSensor==null) {
			try(FileChannel fileAdditionalsensors = (FileChannel) Files.newByteChannel(Path.of("settings Additionalsensors.config"), StandardOpenOption.READ)){
				additionalSensors = new int[getNumCorsie()][2];
				ByteBuffer buffer = ByteBuffer.allocate(numLanes*8);
				fileAdditionalsensors.read(buffer);
				int dato;
				for (int i = 0; i<numLanes*8;i++) {
					dato = (int)buffer.get(i);
					if (i%2==0) {
						additionalSensors[i][0] = dato;
					} else {
						additionalSensors[i][1] = dato;
					}
				}
			
			} catch(IOException e){
				System.out.println("eccezione!!");
			}
		}
		return requiredSensor;
	}
	
}
