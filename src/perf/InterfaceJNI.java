package perf;

import trains.Counters;
import trains.Interface;

public class InterfaceJNI {
	
	public native void getrusageBegin();
	public native void getrusageEnd();
	public native void setcountersBegin(byte[] data);
	public native void setcountersEnd(byte[] data);
	public native int getru_utime();
	public native int getru_stime();
	public native int getmessages_delivered();
	public native int getmessages_bytes_delivered();
	public native int gettrains_bytes_received();
	public native int gettrains_received();
	public native int getrecent_trains_received();
	public native int getrecent_trains_bytes_received();
	public native int getwagons_delivered();
	public native int getwait_states();
	public native int getcomm_read();
	public native int getcomm_read_bytes();
	public native int getcomm_readFully();
	public native int getcomm_readFully_bytes();
	public native int getcomm_write();
	public native int getcomm_write_bytes();
	public native int getcomm_writev();
	public native int getcomm_writev_bytes();
	public native int getnewmsg();
	public native int getflowControl();
	
	// Static factory for TrainsInterface
	public static InterfaceJNI perfInterface(){
		System.loadLibrary("perf");
		InterfaceJNI perfInterface = new InterfaceJNI();
		return perfInterface;
	}
	
	public void JgetrusageBegin(){
		this.getrusageBegin();
	}
	
	public void JgetrusageEnd(){
		this.getrusageEnd();
	}
	
	public void JsetcountersBegin(Interface trin){
		//System.out.println("begin");
		Counters countersBegins = new Counters();
		//System.out.println("dump");
		trin.dumpCountersData(countersBegins.getData());
		//System.out.println("set");
		this.setcountersBegin(countersBegins.getData());
		//System.out.println("ok");

	}	
	
	public void JsetcountersEnd(Interface trin){
		Counters countersEnds = new Counters();
		trin.dumpCountersData(countersEnds.getData());
		this.setcountersEnd(countersEnds.getData());
	}
	
	public int Jgetru_utime(){
		return this.getru_utime();
	}
	
	public int Jgetru_stime(){
		return this.getru_stime();
	}
	
	public int Jgetmessages_delivered(){
		return this.getmessages_delivered();
	}
	
	public int Jgetmessages_bytes_delivered(){
		return this.getmessages_bytes_delivered();
	}
	
	public int Jgettrains_bytes_received(){
		return this.gettrains_bytes_received();
	}
	
	public int Jgettrains_received(){
		return this.gettrains_received();
	}
	
	public int Jgetrecent_trains_received(){
		return this.getrecent_trains_received();
	}
	
	public int Jgetrecent_trains_bytes_received(){
		return this.getrecent_trains_bytes_received();
	}
	
	public int Jgetwagons_delivered(){
		return this.getwagons_delivered();
	}
	
	public int Jgetwait_states(){
		return this.getwait_states();
	}
	
	public int Jgetcomm_read(){
		return this.getcomm_read();
	}
	
	public int Jgetcomm_read_bytes(){
		return this.getcomm_read_bytes();
	}
	
	public int Jgetcomm_readFully(){
		return this.getcomm_readFully();
	}
	
	public int Jgetcomm_readFully_bytes(){
		return this.getcomm_readFully_bytes();
	}
	
	public int Jgetcomm_write(){
		return this.getcomm_write();
	}
	
	public int Jgetcomm_write_bytes(){
		return this.getcomm_write_bytes();
	}
	
	public int Jgetcomm_writev(){
		return this.getcomm_writev();
	}
	
	public int Jgetcomm_writev_bytes(){
		return this.getcomm_writev_bytes();
	}
	
	public int Jgetnewmsg(){
		return this.getnewmsg();
	}
	
	public int JgetflowControl(){
		return this.getflowControl();
	}
}