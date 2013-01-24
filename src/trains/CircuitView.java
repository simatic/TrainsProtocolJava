package trains;

import java.util.Vector;

/**  
 * Stores the view (members, who joined, who departed) of the circuit.
 *
 * Notice that if {@link #joined joined} (respectively {@link #departed departed}) is false, a new
 * process has joined (respectively left) the list of processes members of the circuit (thus participating 
 * to the trains protocol). In this case, the address found in {@link #joined joined} (respectively {@link #departed departed}
 * cannot be found in {@link #members members}.
 * 
 * @author Stephanie Ouillon
 */

public class CircuitView {

	/**
	 * CircuitView singleton
	 */
	private static final CircuitView CIRCUITVIEW = new CircuitView(0, 0, 0);
	
	// address type in C is unsigned short -> we deal with it in java with the int type
	
	/**
	 * Number of members currently in the protocol
	 */
	private int nmemb;
	
	
	/**
	 * List of members
	 * 
	 * Notice the newest connected process is at the first position in the array.
	 */
	private Vector<Integer> members;
	
	/**
	 * New member, if any
	 */
	private int joined;
	
	/**
	 * Departed member, if any
	 */
	private int departed;
	
	
	/**
	 * Maximum number of members in the protocol.
	 * The value is gotten from the native code.
	 */
	private static final int MAX_MEMB = Interface.getMax_Memb();
	
	
	/**
	 * Initializes the singleton.
	 * @param nmemb number of members in the protocol (set to 0)
	 * @param joined new member if any (set to 0)
	 * @param departed departed member if any (set to 0)
	 */
	private CircuitView(int nmemb, int joined, int departed){
		this.nmemb = nmemb;
		this.members = new Vector<Integer>(MAX_MEMB);
		this.joined = joined;
		this.departed = departed;
	}
	
	
	/**
	 * Returns the CircuitView singleton instance.
	 * @return the CIRCUIT_VIEW singleton
	 */
	public static CircuitView getInstance(){
		return CIRCUITVIEW;
	}
	
	
	/**
	 * Sets the {@link #nmemb nmemb} attribute.
	 * @param nmemb - number of members in the circuit
	 */
	public void setNMemb(int nmemb){
		this.nmemb = nmemb;
	}

	
	/**
	 * Sets the member address at the given rank in {@link #members members}. This method is used by the native code.
	 * @param rank last connected machine is at the first row.
	 * @param addr address of the connected machine
	 */
	public void setMembersAddress(int rank, int addr){
		this.members.add(rank, addr);
	}

	
	/**
	 * Sets the {@link #joined joined} attribute.
	 * @param joined address of the joining machine
	 */
	public void setJoined(int joined){
		this.joined = joined;
	}

	
	/**
	 * Sets the {@link #departed departed} attribute.
	 * @param departed address of the departing machine
	 */
	public void setDeparted(int departed){
		this.departed = departed;
	}
	
	
	/**
	 * Returns the {@link #nmemb nmemb} attribute.
	 * @return {@link #nmemb nmemb}
	 */
	public int getMemb(){
		return this.nmemb;
	}

	
	/**
	 * Returns the address of the machine at the given rank in the {@link #members members} array.
	 * @return the address of a machine
	 */
	public int getMembersAddress(int rank){
		return this.members.elementAt(rank);
	}

	
	/**
	 * Returns the address of the machine that is joining.
	 * @return the {@link #joined joined} attribute
	 */
	public int getJoined(){
		return this.joined;
	}

	
	/**
	 * Returns the address of the machine that is departing.
	 * @return the {@link #departed departed} attribute
	 */
	public int getDeparted(){
		return this.departed;
	}
}
