package examples;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

import trains.Interface;
import trains.CallbackCircuitChange;
import trains.CallbackUtoDeliver;


public class Example {
	
	@Test
	public void TestInterface() {
		
		int exitcode;
		
		//Parameters set by user
		boolean sender;
		int nbMemberMin;
		int delay;
		int nbRecMsg;
		
		//trInit parameters
		int trainsNumber = 2;
		int wagonLength = 3;
		int waitNb = 4;
		int waitTime = 2;
		
		//Error check
		int status = 0;
		int errnum = 0;
	
		
		
		Interface trin = Interface.trainsInterface();
		
		//on appelle la jvm que dans ./src/applicationMessage.c
		
		exitcode = trin.JtrInit(trainsNumber, wagonLength, waitNb, waitTime,
				(new myCallbackCircuitChange()).getClass().getName(), 
				(new myCallbackUtoDeliver()).getClass().getName());
		assertEquals(exitcode, 0);		

		trin.JtrError_at_line(status, errnum);//, const char *filename, 
			//	 unsigned int linenum, const char *format){
		trin.JtrPerror(errnum);
		exitcode = 1;
		exitcode = trin.JtrTerminate();
		assertEquals(exitcode, 0);
	}
}
