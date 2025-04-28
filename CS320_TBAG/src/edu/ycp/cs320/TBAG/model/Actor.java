package edu.ycp.cs320.TBAG.model;

public class Actor {
	private int actorID, roomID, inventoryID, health;
	private String actorName;

	// Constructor
	public Actor() {
		
	}

	//Set Methods
	public void setHealth(int health) {
	    this.health = health;
	}
	
	public void setRoomId(int roomID) {
	    this.roomID = roomID;
	}
	
	public void setInventoryId(int inventoryID) {
	    this.inventoryID = inventoryID;
	}
	
	public void setActorId(int actorID) {
	    this.actorID = actorID;
	}
	
	public void setActorName(String actorName) {
	    this.actorName = actorName;
	}

	//Get Methods
	public Integer getHealth() {
	    return health;
	}
	
	public Integer getRoomId() {
	    return roomID;
	}
	
	public Integer getInventoryId() {
	    return inventoryID;
	}
	
	public Integer getActorId() {
	    return actorID;
	}
	
	public String getActorName() {
	    return actorName;
	}
}
