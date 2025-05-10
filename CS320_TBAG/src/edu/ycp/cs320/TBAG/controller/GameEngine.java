package edu.ycp.cs320.TBAG.controller;

import edu.ycp.cs320.TBAG.model.Room;
import edu.ycp.cs320.TBAG.model.Item;
import edu.ycp.cs320.TBAG.model.ItemWeapons;
import edu.ycp.cs320.TBAG.model.ItemConsumables;
import edu.ycp.cs320.TBAG.model.Player;
import edu.ycp.cs320.TBAG.model.NPC;

public class GameEngine {
	private Room start, hallway, lab, basement;
	private Item axe, healthKit, oxygenTank;
	private Player user;
	private NPC tempNPC;
	
	
	public String setData() {
		
		//Temp addNPC to hallway
		tempNPC = new NPC(5, 2, false, "A mysterious stranger stands in the corner, his face masked by shadows.");
		hallway.addNPC(tempNPC);
		
		axe = new Item("Axe", 5, 12, "A worn axe used to break down wooden barricades");
		healthKit = new Item("Health Kit", 5, 20, "A packet filled with single-use health stims");
		oxygenTank = new Item("Oxygen Tank", 0, 35, "A sizeable oxygen tank. Great for longer trips underwater");		
		
		start.makeConnection("west", 2);
		hallway.makeConnection("north", 3);
		hallway.makeConnection("east", 1);
		lab.makeConnection("down", 4);
		lab.makeConnection("south", 2);
		basement.makeConnection("up", 3);
		
		hallway.getInventory().addItem(axe);
		lab.getInventory().addItem(healthKit);
		basement.getInventory().addItem(oxygenTank);
		
		user = new Player(100, 1, null);
		start.setHasVisited(true);
		return start.getLongDesc();
	}
	
	public String response(String command) {
		Room currentRoom = getRoom(user.getRoomId());
		if(command.equalsIgnoreCase("north") || command.equalsIgnoreCase("south") || command.equalsIgnoreCase("west") || command.equalsIgnoreCase("east") || command.equalsIgnoreCase("up") || command.equalsIgnoreCase("down")) {
			if(currentRoom.getConnection(command) != null) {
				moveActor(currentRoom.getConnection(command));
				currentRoom = getRoom(user.getRoomId());
				if(currentRoom.getHasVisited()) {
					return currentRoom.getShortDesc();
				}
				else {
					currentRoom.setHasVisited(true);
					//Good lord please ignore how barebones this convo system is so far-->
					System.out.println(currentRoom.containsNPCS());
					if(currentRoom.containsNPCS()){
						String tempString = currentRoom.getLongDesc();
						tempString += "\n" + currentRoom.NPCS.get(0).getTempConvo();
						
						return tempString;
					}
					
					System.out.println("No NPCS detected, initial descr overridden");
					return currentRoom.getLongDesc();
					
				}
			}
			else {
				return "You can't go that way";
			}
			
		}
		
		if(command.equalsIgnoreCase("pick up")) {
			if(currentRoom.getInventory().getItems().size() != 0) {
				user.getInventory().addItem(currentRoom.getInventory().removeItem(0));
				Integer size = user.getInventory().getItems().size();
				return "You picked up the " + user.getInventory().getItem(size - 1).getName();
			}
			
			return "There is nothing to pick up";
		}
		
		if(command.equalsIgnoreCase("search")) {
			return currentRoom.getLongDesc();
		}
		
		if(command.equalsIgnoreCase("check inventory")) {
			Integer size = user.getInventory().getItems().size();
			String items = "Your inventory:\n"; 
			if(size == 0) {
				return "There's nothing in your inventory";
			}
			for(int i = 0; i < size; i++) {
				items += user.getInventory().getItem(i).getName() + "\n";
			}
			return items;
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
