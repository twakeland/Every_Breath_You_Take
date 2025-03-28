package edu.ycp.cs320.TBAG.controller;

import edu.ycp.cs320.TBAG.model.Room;
import edu.ycp.cs320.TBAG.model.Item;
import edu.ycp.cs320.TBAG.model.Player;

public class GameEngine {
	private Room start, hallway, lab, basement;
	private Item axe, healthKit, oxigenTank;
	private Player user;
	
	
	public String setData() {
		start = new Room(1, "You're in starting area", "Welcome to the starting area");
		hallway = new Room(2, "You're in a long hallway", "The hallway is long and dark. You can see a light in the distance. There is an axe leaning on the wall");
		lab = new Room(3, "You're in the lab", "The lab is filled with tons of scientific equipment you don't recognize. There is a medkit on the desk");
		basement = new Room(4, "You're in the basement", "The basement is cold and damp, you shouldn't be here. You can see an oxigen tank hidden in the dark");
		
		axe = new Item(5, 12, "A worn axe used to break down wooden barricades");
		healthKit = new Item(5, 20, "A packet filled with single-use health stims");
		oxigenTank = new Item(null, 35, "A sizeable oxigen tank. Great for longer trips underwater");
		
		start.makeConnection("west", 2);
		hallway.makeConnection("north", 3);
		hallway.makeConnection("east", 1);
		lab.makeConnection("down", 4);
		lab.makeConnection("south", 2);
		basement.makeConnection("up", 3);
		
		hallway.getInventory().addItem(axe);
		lab.getInventory().addItem(healthKit);
		basement.getInventory().addItem(oxigenTank);
		
		user = new Player(100, 1, null);
		start.setHasVisited(true);
		return start.getLongDesc();
	}
	
	public String response(String command) {
		Room currentRoom = getRoom(user.getLocation());
		if(command.equals("north") || command.equals("south") || command.equals("west") || command.equals("east") || command.equals("up") || command.equals("down")) {
			if(currentRoom.getConnection(command) != null) {
				moveActor(currentRoom.getConnection(command));
				currentRoom = getRoom(user.getLocation());
				if(currentRoom.getHasVisited()) {
					return currentRoom.getShortDesc();
				}
				else {
					currentRoom.setHasVisited(true);
					return currentRoom.getLongDesc();
				}
			}
			else {
				return "You can't go that way";
			}
			
		}
		
		return "I do not recognize that command";
	}
	
	public void moveActor(Integer roomID) {
		user.setLocation(roomID);
	}
	
	public Room getRoom(Integer roomID) {
		if (roomID == 1) {
			return start;
		}
		
		else if (roomID == 2) {
			return hallway;
		}
		
		else if (roomID == 3) {
			return lab;
		}
		
		return basement;
	}
}
