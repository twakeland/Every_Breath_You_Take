package edu.ycp.cs320.TBAG.model;

public class NPC extends Actor{
	private boolean isAttackable;
	private String firstDesc;
	private String tempConversation;
	//private String 
	
	public NPC() {
	    super();  // Calls Actor constructor
	    this.isAttackable = isAttackable;
	    this.firstDesc = firstDesc;
	    //completley temporary conversation 
	    tempConversation = "You attempt to talk to the stranger, he promptly tells you to go away";
	    }
	
	public boolean getAttackable() {
	    return isAttackable;
	 	}
	  
	public void setAttackable(boolean isAttackable) {
		this.isAttackable = isAttackable;
	    }
	
	public String getFirstDesc() {
	    return firstDesc;
	 	}
	  
	public void setFirstDesc(String firstDesc) {
		this.firstDesc = firstDesc;
	    }
	
	public String getTempConvo() {
	    return tempConversation;
	 	}
	
}
