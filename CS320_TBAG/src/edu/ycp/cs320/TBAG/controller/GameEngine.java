package edu.ycp.cs320.TBAG.controller;

import edu.ycp.cs320.TBAG.model.Room;
import edu.ycp.cs320.TBAG.model.Item;
import edu.ycp.cs320.TBAG.model.ItemWeapons;
import edu.ycp.cs320.TBAG.model.ItemConsumables;
import edu.ycp.cs320.TBAG.model.Player;
import edu.ycp.cs320.TBAG.model.NPC;

public class GameEngine {
	private Room start, hallway, lab, basement;
	private Item /*axe, healthKit,*/ oxygenTank;
	private Player user;
	private NPC tempNPC;
	
	
	public String setData() {
		start = new Room(1, "You're in the starting area", "Welcome to the starting area");
		hallway = new Room(2, "You're in a long hallway. The stranger is still in the corner.", "The hallway is long and dark. You can see a light in the distance. There is an axe leaning on the wall. A stranger stands in the corner.");
		lab = new Room(3, "You're in the lab", "The lab is filled with tons of scientific equipment you don't recognize. There is a shiny rock on the floor.");
		basement = new Room(4, "You're in the basement", "The basement is cold and damp, you shouldn't be here. You can see an oxygen tank hidden in the dark");
		
		//Temporary addNPC to hallway
		Item tempQuestItem = new Item("Shiny Rock", 1, 1, "Its a cool lookin rock");
		lab.getInventory().addItem(tempQuestItem);
		tempNPC = new NPC(5, 2, true, "You punch the stranger in the throat for no reason. \n\"Have at thee!\" he yells, readying his weapon.", "You leave the stranger to his shenanigans.", "You wouldn't happen to have a shiny rock on you?", "You remove the shiny rock you found and hand it to the stranger. His eyes shine with happiness.", tempQuestItem);
		hallway.addNPC(tempNPC);
		
		
		
		//axe = new ItemWeapons("Axe", 5, 12, "A worn axe used to break down wooden barricades", 12);
		//healthKit = new ItemConsumables("Health Kit", 5, 20, "A packet filled with single-use health stims", 20);
		oxygenTank = new Item("Oxygen Tank", 0, 35, "A sizeable oxygen tank. Great for longer trips underwater");		
		
		start.makeConnection("west", 2);
		hallway.makeConnection("north", 3);
		hallway.makeConnection("east", 1);
		lab.makeConnection("down", 4);
		lab.makeConnection("south", 2);
		basement.makeConnection("up", 3);
		
		//hallway.getInventory().addItem(axe);
		//lab.getInventory().addItem(healthKit);
		basement.getInventory().addItem(oxygenTank);
		
		user = new Player(100, 1, null);
		
		start.setHasVisited(true);
		return start.getLongDesc();
	}
	
	public String response(String command) {
		Room currentRoom = getRoom(user.getLocation());
		//PlayerDialog controller
		if(user.isInDialog() == true) {
			NPC target = currentRoom.getNPC(0);
			//Talk dialog choice
			if(command.equalsIgnoreCase("talk")) {
				return target.getQuestStartQuip();
			}
			
			//Give Item dialog choice
			if(command.equalsIgnoreCase("give item")) {
				if (user.getInventory().contains(target.getQuestItem()) && target.getQuestItem() != null) {
					int tempIndex = user.getInventory().getItemIndex((target.getQuestItem()));
					user.getInventory().removeItem(tempIndex);
					return target.getQuestFinQuip();
				}
				else {
					return "They have no need for anything you have.";
				}
			}
			
			//Attack Dialog choice
			if(command.equalsIgnoreCase("attack")) {
				if(target.getAttackable() == true) {
					user.setDialog(false);
					System.out.println("Player begins combat with NPC");
					return target.getAttackQuip();
					//This is where the call to combat would be
					
				}
				else {
					return ("This character cannot be attacked!");
				}
			}	
			
			//Leave Dialog choice
			if(command.equalsIgnoreCase("leave")) {
				user.setDialog(false);
				return target.getLeaveQuip();
			}
			
			return "Error, command unknown";
		}
		
		//PlayerMovement controller
		else if(command.equalsIgnoreCase("north") || command.equalsIgnoreCase("south") || command.equalsIgnoreCase("west") || command.equalsIgnoreCase("east") || command.equalsIgnoreCase("up") || command.equalsIgnoreCase("down")) {
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
		
		if(command.equalsIgnoreCase("approach") && currentRoom.containsNPCS()) {
			user.setDialog(true);
			return "You approach the stranger.";
		}
		else if (command.equalsIgnoreCase("talk")) {
			return "There is no one in the room to talk to.";
		}
		
		if(command.equalsIgnoreCase("pick up")) {
			if(currentRoom.getInventory().isEmpty() != true) {
				Item tempItem = currentRoom.getInventory().getItem(0);
				currentRoom.getInventory().removeItem(0);
				user.getInventory().addItem(tempItem);
				
				Integer size = user.getInventory().getItemIndex(tempItem);
				return "You picked up the " + user.getInventory().getItem(size).getName();
			}
			
			return "There is nothing to pick up";
		}
		
		if(command.equalsIgnoreCase("search")) {
			return currentRoom.getLongDesc();
		}
		
		if(command.equalsIgnoreCase("check inventory")) {
			Integer size = user.getInventory().getInvSize();
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
