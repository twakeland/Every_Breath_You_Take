package edu.ycp.cs320.TBAG.model;

public class Player extends Actor{
	private String command;
	  private Inventory playerInventory;
	  private boolean inDialog;


	  // Constructor
	  public Player(int health, int location, String command) {
	    super(health, location);  // Calls Actor constructor
	    this.command = command;
	    playerInventory = new Inventory(10);
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
