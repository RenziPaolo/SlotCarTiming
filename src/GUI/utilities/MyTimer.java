package GUI.utilities;

import java.util.Timer;
import java.util.TimerTask;

import GUI.Event;
import javafx.scene.text.Text;

public class MyTimer extends TimerTask{

	private int hours;
	private int minutes;
	private int seconds;
	private Text label;
	private Timer timer = new Timer(true);
	private Event event;

	public Text getLabel() {
		return label;
	}
	
	public MyTimer(int seconds, int minutes,int hours,Text label, Event event) {
		this.hours = hours;
		this.minutes = minutes;
	    this.seconds = seconds;
		this.label = label;
		this.event = event;
	}
	public MyTimer(int seconds,Text text, Event event) {
		this.hours = seconds/3600;
		this.minutes = (seconds/60)%60;
	    this.seconds = seconds%60;;
		this.label = text;
		this.event = event;
	}
	
	public void setTime(int seconds) {
		this.hours = seconds/3600;
		this.minutes = (seconds/60)%60;
	    this.seconds = seconds%60;;
	}
	
	public void setHours(int hours) {
		this.hours = hours;
	}
	
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	
	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
	
	public int getHours() {
	    return hours;
	}
	
	public int getMinutes() {
	    return minutes;
	}
	
	public int getSeconds() {
	    return seconds;
	}

	public void start() {
		timer.scheduleAtFixedRate(new MyTimer(seconds,minutes,hours,label,event) , 0, 1000);
	}
	
	public void restart() {
		timer = new Timer(true);
		timer.schedule(new MyTimer(seconds,minutes,hours,label,event) , 0, 1000);
	}
	
	public void stop() {
		timer.cancel();
	}
        
	@Override
    public void run() {
		label.setText(toString());
		
		if (seconds >0) {
        	seconds--;
        } else {
        	if (minutes >0) {
        		seconds = 59;
       			minutes--; 
       		} else {
       			if (hours>0) {
       				minutes = 59;
       				hours--;
       			}
       		}
       	}
        if (seconds == 0 && minutes == 0 && hours == 0) {
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}        	
    		label.setText(toString());
    		try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}  
        	event.exit();
        	this.cancel();
       	}
    }
		
	@Override
	public String toString() {
	    String hours = "";
	    String minutes;
	    String seconds;
		if (this.hours>0 && this.hours < 10) {
	        hours = "0" + this.hours;
	    } else {
	    	if (this.hours>0)
	        hours = this.hours + "";
	    }
	
		if (this.minutes < 10) {
		    minutes = "0" + this.minutes;
		} else {
		    minutes = this.minutes + "";
		}
	    
		if (this.seconds < 10) {
	        seconds = "0" + this.seconds;
	    } else {
	        seconds = this.seconds + "";
	    }
		if (this.hours>0)
			return hours + ":" + minutes + ":" + seconds;
		return minutes + ":" + seconds;
	}

	public void resetSchedule() {
		stop();
		timer = new Timer(true);
	}

}