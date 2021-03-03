package timing;

import java.nio.ByteBuffer;
import java.util.Arrays;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class CComunication {
	private boolean go;
	private Sensor sensor;
	
	public static double convertToFloat(byte[] array) {
		byte tmp;
        for (int i = 0; i<(array.length)/2;i++) {
            tmp = array[i];
            array[i] = array[(array.length-1)-i];
            array[(array.length-1)-i] = tmp;
        }
		
	    ByteBuffer buffer = ByteBuffer.wrap(array);
	    return buffer.getDouble();
	}
	public static int convertToInt(byte[] array) {
		byte tmp;
        for (int i = 0; i<(array.length)/2;i++) {
            tmp = array[i];
            array[i] = array[(array.length-1)-i];
            array[(array.length-1)-i] = tmp;
        }
		
	    ByteBuffer buffer = ByteBuffer.wrap(array);
	    return buffer.getInt();
	}
	
	public CComunication(Sensor sensor) {
		this.sensor = sensor;
	}
	
public void start() {
	try (ZContext context = new ZContext()) {
		//  Socket to talk to server
		System.out.println("Connecting to hello world server");
		
		ZMQ.Socket socket = context.createSocket(SocketType.REQ);
		socket.connect("tcp://localhost:5555");
		while(go) {
			socket.send("".getBytes(ZMQ.CHARSET), ZMQ.DONTWAIT);
			byte[] reply = socket.recv(0);
			            
			sensor.setTime(convertToFloat(Arrays.copyOfRange(reply, 4, 12)),convertToInt(Arrays.copyOfRange(reply, 0, 4)));
		}
	}
}
	
public void stop() {
	go = false;
}
public void go() {
	go = true;
}
	
}
