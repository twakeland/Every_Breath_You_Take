package edu.ycp.cs320.TBAG.model;

public class Actor {
	private int health;
	private Integer location;

	// Constructor
	public Actor(int health, Integer location) {
		this.health = health;
		this.location = location;
	}

	//Set Methods
	public void setHealth(int health) {
	    this.health = health;
	}
	public void setLocation(Integer location) {
	    this.location = location;
	}

	//Get Methods
	public int getHealth() {
	    return health;
	}
	
	public Integer getLocation() {
	    return location;
	}
}
