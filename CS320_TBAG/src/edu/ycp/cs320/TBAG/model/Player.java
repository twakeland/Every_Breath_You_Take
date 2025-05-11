package edu.ycp.cs320.TBAG.model;

public class Player extends Actor{
	private String command;
	  private Inventory playerInventory;
	  private boolean inDialog;


	  // Constructor
	  public Player(int actorID, int roomID, int inventoryID, int health, String actorName, String command) {
	    super(actorID, roomID, inventoryID, health, actorName);  // Calls Actor constructor
	    this.command = command;
	    playerInventory = new Inventory();
	    inDialog = false;
	  }

	  public String getCommand() {
	    return command;
	  }
	  
	  public void setCommand(String command) {
	    this.command = command;
	  }
	  
	  public boolean isInDialog() {
		  return inDialog;
	  }
		  
	  public void setDialog(boolean inDialog) {
		  this.inDialog = inDialog;
	  }
	  
	  public Inventory getInventory() {
		  return playerInventory;
	  }
	  
}
