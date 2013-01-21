package perf;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;

import trains.Interface;

public class TimeKeeper implements Runnable {
	
	private final int broadcasters;
	private final int number;
	private final int size;
	private final int warmup;
	private final int measurement;
	private final int cooldown;
	
	private long timeLoadInterfaceBegins = -1;
	private long timeLoadInterfaceEnds = -1;
	
	private long timeJtrInitBegins = -1;
	private long timeJtrInitEnds = -1;	
	
	public static class Builder{
		//Required parameters
		private final int broadcasters;
		private final int number;
		private final int size;
	
		//Optional parameters - initialized with default values
		private int warmup = 300;
		private int measurement = 600;
		private int cooldown = 10;
		
		public Builder(int broadcasters, int number, int size){
			this.broadcasters = broadcasters;
			this.number = number;
			this.size = size;
		}
		
		public Builder warmup(int val){
			warmup = val;
			return this;
		}

		public Builder measurement(int val){
			measurement = val;
			return this;
		}
		
		public Builder cooldown(int val){
			cooldown = val;
			return this;
		}		
		public TimeKeeper build(){
			return new TimeKeeper(this);
		}
	}
	
	private TimeKeeper(Builder builder){
		broadcasters = builder.broadcasters;
		number = builder.number;
		size = builder.size;
		warmup = builder.warmup;
		measurement = builder.measurement;
		cooldown = builder.warmup;
	}
	
	public void run(){

		/* What we want: 
		 * time for JtrInit
		 * elapsed time
		 * ru_utime
		 * su-utime
		 * number of messages delivered by the application
		 * number of bytes delivered to the application
		 * number of calls to newmsg() 
		 * and more... */

		long timeBegins = -1;
		long timeEnds = -1;
		
		InterfaceJNI perfin = InterfaceJNI.perfInterface();
		
		try {
			Thread.sleep(warmup * 1000);
			
			timeBegins = System.nanoTime();
			perfin.JgetrusageBegin();
			
			Thread.sleep(measurement * 1000);
			
			timeEnds = System.nanoTime();
			perfin.JgetrusageEnd();
			
			Thread.sleep(cooldown * 1000);
			
			this.setMeasurementDone();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Time for LoadInterface (in sec): " + (this.timeLoadInterfaceEnds - this.timeLoadInterfaceBegins)/1000000000d);
		System.out.println("Time for JtrInit (in sec): " + (this.timeJtrInitEnds - this.timeJtrInitBegins)/1000000000d);
		System.out.println("Elasped time (in sec): " + (timeEnds - timeBegins)/1000000000d);
		
		System.out.println("ru_utime: " + perfin.Jgetru_utime());

	}
	
	public void setTimeLoadInterfaceBegins(long val){
		this.timeLoadInterfaceBegins = val;
	}
	
	public void setTimeLoadInterfaceEnds(long val){
		this.timeLoadInterfaceEnds = val;
	}
	
	public void setTimeJtrInitBegins(long val){
		this.timeJtrInitBegins = val;
	}
	
	public void setTimeJtrInitEnds(long val){
		this.timeJtrInitEnds = val;
	}
	
	public void setMeasurementDone(){
		Perf.measurementDone = true;
	}

}

