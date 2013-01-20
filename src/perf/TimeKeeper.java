package perf;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;

public class TimeKeeper implements Runnable {
	
	private long timeLoadInterfaceBegins = -1;
	private long timeLoadInterfaceEnds = -1;
	
	private long timeJtrInitBegins = -1;
	private long timeJtrInitEnds = -1;
	
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



		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		if (threadMXBean == null){
			throw new NullPointerException("Unable to collect " +
					"thread metrics, jmx bean is null"); }

		OperatingSystemMXBean osMXBean = ManagementFactory.getOperatingSystemMXBean();
		if (osMXBean == null){
			throw new NullPointerException("Unable to collect " +
					"operating system metrics, jmx bean is null"); }

		// Returns the system load average for the last minute.
		// Java 6 only
		
		//measurementDone = true;
		
		System.out.println("System load average for the last minute : " + osMXBean.getSystemLoadAverage());
		
		
	}
	
	public void setTimeLoadInterfaceBegins(long val){
		this.timeLoadInterfaceBegins = val;
	}
	
	public void setTimeLoadInterfaceEnds(long val){
		this.timeLoadInterfaceBegins = val;
	}
	
	public void setTimeJtrInitBegins(long val){
		this.timeJtrInitBegins = val;
	}
	
	public void setTimeJtrInitEnds(long val){
		this.timeJtrInitEnds = val;
	}
	
}

