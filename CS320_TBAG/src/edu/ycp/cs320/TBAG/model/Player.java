package edu.ycp.cs320.TBAG.model;

public class Player extends Actor{
	private String command;
	  private Inventory playerInventory;


	  // Constructor
	  public Player(int health, int location, String command) {
	    super(health, location);  // Calls Actor constructor
	    this.command = command;
	    playerInventory = new Inventory(10);
	  }

	  public String getCommand() {
	    return command;
	  }
	  
	  public void setCommand(String command) {
	    this.command = command;
	  }
	  
	  public Inventory getInventory() {
		  return playerInventory;
	  }
}
