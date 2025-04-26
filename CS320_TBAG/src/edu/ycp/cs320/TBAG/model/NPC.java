package edu.ycp.cs320.TBAG.model;

public class NPC extends Actor{
	private boolean isAttackable;
	private String attackQuip;
	private String leaveQuip;
	//private String 
	
	public NPC(int health, int location, boolean isAttackable, String attackQuip, String leaveQuip) {
	    super(health, location);  // Calls Actor constructor
	    this.isAttackable = isAttackable;
	    this.attackQuip = attackQuip;
	    this.leaveQuip = leaveQuip;
	    
	    }
	
	public boolean getAttackable() {
	    return isAttackable;
	 	}
	  
	public void setAttackable(boolean isAttackable) {
		this.isAttackable = isAttackable;
	    }
	
	public String getAttackQuip() {
	    return attackQuip;
	 	}
	  
	public void setAttackQuip(String attackQuip) {
		this.attackQuip = attackQuip;
	    }
	
	public String getLeaveQuip() {
	    return leaveQuip;
	 	}
	  
	public void setLeaveQuip(String leaveQuip) {
		this.leaveQuip = leaveQuip;
	    }
	
}
