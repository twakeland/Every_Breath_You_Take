package edu.ycp.cs320.TBAG.model;

public class Actor {
	private int actorID, roomID, inventoryID, health;
	private String actorName;
	private Inventory inventory;

	// Constructor
	public Actor(int actorID, int roomID, int inventoryID, int health, String actorName) {
		this.actorID = actorID;
		this.roomID = roomID;
		this.inventoryID = inventoryID;
		this.health = health;
		this.actorName = actorName;
		this.inventory = new Inventory();
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
	
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
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
	
	public Inventory getInventory() {
		return inventory;
	}
}
