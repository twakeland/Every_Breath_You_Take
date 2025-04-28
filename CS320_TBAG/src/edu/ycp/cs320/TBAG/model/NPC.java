package edu.ycp.cs320.TBAG.model;

public class NPC extends Actor{
	private boolean isAttackable;
	private String attackQuip;
	private String leaveQuip;
	
	//Relegated to questNPCS
	private String questStartQuip;
	private String questFinQuip;
	private Item questItem;
	
	
	
	public NPC(int health, int location, boolean isAttackable, String attackQuip, String leaveQuip) {
	    super(health, location);  // Calls Actor constructor
	    this.isAttackable = isAttackable;
	    this.attackQuip = attackQuip;
	    this.leaveQuip = leaveQuip;
	    this.questItem = null;
	    this.questStartQuip = null;
	    this.questFinQuip = null;
	    
	    }
	
	public NPC(int health, int location, boolean isAttackable, String attackQuip, String leaveQuip, String questStartQuip, String questFinQuip, Item questItem) {
	    super(health, location);  // Calls Actor constructor
	    this.isAttackable = isAttackable;
	    this.attackQuip = attackQuip;
	    this.leaveQuip = leaveQuip;
	    this.questStartQuip = questStartQuip;
	    this.questFinQuip = questFinQuip;
	    this.questItem = questItem;
	    
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
	
	public void setQuestItem(Item questItem) {
		this.questItem = questItem;
		}
	
	public Item getQuestItem() {
		return questItem;
		}
	
	public String getQuestStartQuip() {
	    return questStartQuip;
	 	}
	  
	public void setQuestStartQuip(String questQuip) {
		this.questStartQuip = questQuip;
	    }
	
	public String getQuestFinQuip() {
	    return questFinQuip;
	 	}
	  
	public void setQuestFinQuip(String questQuip) {
		this.questFinQuip = questQuip;
	    }
}
