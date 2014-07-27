package perf;

import java.util.Vector;

import trains.Interface;

public class TimeKeeper implements Runnable {
	
	private final Interface trin;
	
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
	    private final Interface trin;
		private final int broadcasters;
		private final int number;
		private final int size;
	
		//Optional parameters - initialized with default values
		private int warmup = 300;
		private int measurement = 600;
		private int cooldown = 10;
		
		public Builder(Interface trin, int broadcasters, int number, int size){
			this.trin = trin;
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
		trin = builder.trin;
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
			//System.out.println("warmup");
			Thread.sleep(this.warmup * 1000);
			//System.out.println("timeBegins");

			timeBegins = System.nanoTime();
			//System.out.println("getrusageBegin");
			perfin.JgetrusageBegin();
			//System.out.println("JsetcountersBegin");
			perfin.JsetcountersBegin(trin);
			//System.out.println("measurment");
			
			Thread.sleep(this.measurement * 1000);
			
			timeEnds = System.nanoTime();
			perfin.JgetrusageEnd();
			perfin.JsetcountersEnd(trin);
			
			Thread.sleep(this.cooldown * 1000);
			
			this.setMeasurementDone();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Time for LoadInterface (in sec): " + (this.timeLoadInterfaceEnds - this.timeLoadInterfaceBegins)/1000000000d);
		System.out.println("Time for JtrInit (in sec): " + (this.timeJtrInitEnds - this.timeJtrInitBegins)/1000000000d);
		System.out.println("Elasped time (in sec): " + (timeEnds - timeBegins)/1000000000d);
		
		System.out.println("ru_utime: " + perfin.Jgetru_utime()/1000000d);
		System.out.println("su_utime: " + perfin.Jgetru_stime()/1000000d);

		System.out.println("number of messages delivered to the application: " + perfin.Jgetmessages_delivered());
		System.out.println("number of bytes delivered to the application: " + perfin.Jgetmessages_bytes_delivered());
		System.out.println("number of bytes of trains received from the network: " + perfin.Jgettrains_bytes_received());
		System.out.println("number of trains received from the network: " + perfin.Jgettrains_received());
		System.out.println("number of bytes of recent trains received from the network: " + perfin.Jgetrecent_trains_bytes_received());
		System.out.println("number of recent trains received from the network: " + perfin.Jgetrecent_trains_received());
		System.out.println("number of wagons delivered to the application: " + perfin.Jgetwagons_delivered());
		System.out.println("number of times automaton has been in state WAIT: " + perfin.Jgetwait_states());
		System.out.println("number of calls to commRead() : " + perfin.Jgetcomm_read());
		System.out.println("number of bytes read by commRead() calls: " + perfin.Jgetcomm_read_bytes());
		System.out.println("number of calls to commReadFully(): " + perfin.Jgetcomm_readFully());
		System.out.println("number of bytes read by commReadFully() calls: " + perfin.Jgetcomm_readFully_bytes());
		System.out.println("number of calls to commWrite(): " + perfin.Jgetcomm_write());
		System.out.println("number of bytes written by commWrite() calls: " + perfin.Jgetcomm_write_bytes());
		System.out.println("number of calls to commWritev(): " + perfin.Jgetcomm_writev());
		System.out.println("number of bytes written by commWritev() calls: " + perfin.Jgetcomm_writev_bytes());
		System.out.println("number of calls to newmsg(): " + perfin.Jgetnewmsg());
		System.out.println("number of times there was flow control when calling newmsg(): " + perfin.JgetflowControl());
		
		System.out.println("\nBroadcasters / number / size / ntr / Average number of delivered wagons per recent train received / Average number of msg per wagon / Throughput of uto-broadcasts in Mbps ; "+broadcasters+" ; "+number+" ; "+size+" ; ?ntr ; "+perfin.Jgetwagons_delivered()/perfin.Jgetrecent_trains_received()+" ; "+perfin.Jgetmessages_delivered()/perfin.Jgetwagons_delivered()+" ; "+perfin.Jgetmessages_bytes_delivered()/((timeEnds - timeBegins)/1000000000d)+"\n");

		System.exit(0);
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

