package edu.ycp.cs320.TBAG.model;

public class Player extends Actor{

	  // Constructor
	  public Player(int actorID, int roomID, int inventoryID, int health, String actorName) {
	    super(actorID, roomID, inventoryID, health, actorName);  // Calls Actor constructor
	  }
}
