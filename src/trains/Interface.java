package trains;

public class Interface {
	//native methods
	public native int trInit(int trainsNumber, int wagonLength, int waitNb, int waitTime,
			String callbackCircuitChange, 
			String callbackUtoDeliver);

	public native void trError_at_line(int status, int errnum);//, const char *filename, 
	//		unsigned int linenum, const char *format); 
	
	public native void trPerror(int errnum);
	public native int trTerminate();
	public native int newmsg(int payloadSize);
	public native int utoBroadcast(Message msg);
	private static native void initIDsMessageHeader();
	private static native void initIDsMessage();
	private static native void initIDsCircuitView();	
	
	// Static factory for TrainsInterface
	public static Interface trainsInterface(){
		System.loadLibrary("trains");
		Interface trainsInterface = new Interface();
		initIDs();
		return trainsInterface;
	}
	
	public static void initIDs(){
		initIDsMessageHeader();
		initIDsMessage();
		initIDsCircuitView();
	}
	
	//method to call native method trInit()
	//pass in arguments the names of two callback functions
	public int JtrInit(int trainsNumber, int wagonLength, int waitNb, int waitTime,
			String callbackCircuitChange, 
			String callbackUtoDeliver){
		
		int exitcode = this.trInit(trainsNumber, wagonLength, waitNb, waitTime,
				callbackCircuitChange, callbackUtoDeliver);	
		return exitcode;
	}
	
	public void JtrError_at_line(int status, int errnum){//, const char *filename, 
		//	 unsigned int linenum, const char *format){
		 
		this.trError_at_line(status, errnum);//, const char *filename, 
					//unsigned int linenum, const char *format);
	}
	
	public void JtrPerror(int errnum){
		this.trPerror(errnum);
	}
	
	public int JtrTerminate(){
		int exitcode = this.trTerminate();
		return exitcode;
	}
	
	public int Jnewmsg(int payloadSize){
		int exitcode = this.newmsg(payloadSize);
		return exitcode;
	}
	
	public int JutoBroadcast(Message msg){
		int exitcode = this.utoBroadcast(msg);			
		return exitcode;
	}	
}
	
