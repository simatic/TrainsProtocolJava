package examples;

/* Utility class to register parameters used by the program 
 * launching the trains protocol.
 * The user can add or remove parameters according to the program
 * to be writed.
 * It uses a Builder to instantiate a new object of the class UserParameters.
 * */

public class UserParameters {
	private final boolean sender;
	private final int nbMemberMin;
	private final int delayBetweenTwoUtoBroadcast;
	private final int nbRecMsgBeforeStop;
	
	public static class Builder{
		//Required parameters
		private final boolean sender;
		private final int nbMemberMin;
		
		//Optional parameters initialized to default value
		private int delayBetweenTwoUtoBroadcast = 0;
		private int nbRecMsgBeforeStop = 0;
		
		public Builder(boolean sender, int nbMemberMin){
			this.sender = sender;
			this.nbMemberMin = nbMemberMin;
		}
		
		public Builder delayBetweenTwoUtoBroadcast(int val)
			{ delayBetweenTwoUtoBroadcast = val; return this; }
		
		public Builder nbRecMsgBeforeStop(int val)
			{ nbRecMsgBeforeStop = val; return this; }
		
		public UserParameters build(){
			return new UserParameters(this);
		}
	}
	
	private UserParameters(Builder builder) {
		sender = builder.sender;
		nbMemberMin = builder.nbMemberMin;
		delayBetweenTwoUtoBroadcast = builder.delayBetweenTwoUtoBroadcast;
		nbRecMsgBeforeStop = builder.nbRecMsgBeforeStop;
	}
}
