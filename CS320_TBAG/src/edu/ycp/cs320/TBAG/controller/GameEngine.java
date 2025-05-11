package edu.ycp.cs320.TBAG.controller;

import java.util.List;
import java.util.Map;

import edu.ycp.cs320.TBAG.model.Room;
import edu.ycp.cs320.TBAG.model.Actor;
import edu.ycp.cs320.TBAG.model.Item;
import edu.ycp.cs320.TBAG.model.ItemWeapons;
import edu.ycp.cs320.TBAG.model.ItemConsumables;
import edu.ycp.cs320.TBAG.model.Player;
import edu.ycp.cs320.TBAG.model.NPC;
import edu.ycp.cs320.TBAG.model.Pair;
import edu.ycp.cs320.TBAG.persist.DatabaseProvider;
import edu.ycp.cs320.TBAG.persist.IDatabase;
import edu.ycp.cs320.TBAG.persist.FakeDatabase;
import edu.ycp.cs320.TBAG.persist.DerbyDatabase;

public class GameEngine {
	private Room start, hallway, lab, basement;
	private Item axe, healthKit, oxygenTank;
	private Player user;
	private NPC tempNPC;
	
	
	public String setData() {
		
		DatabaseProvider.setInstance(new DerbyDatabase());
		IDatabase db = DatabaseProvider.getInstance();
		
		//Temporary addNPC to hallway
		Item tempQuestItem = new Item();
		lab.getInventory().addItem(tempQuestItem);
		tempNPC = new NPC(2, 2, 7, 50, "Stranger", true, "You punch the stranger in the throat for no reason. \n\"Have at thee!\" he yells, readying his weapon.", "You leave the stranger to his shenanigans.", "You wouldn't happen to have a shiny rock on you?", "You remove the shiny rock you found and hand it to the stranger. His eyes shine with happiness.", tempQuestItem);
		hallway.addNPC(tempNPC);	
		
		db.findRoomByRoomId(2).getInventory().addItem(axe);
		db.findRoomByRoomId(3).getInventory().addItem(healthKit);
		db.findRoomByRoomId(4).getInventory().addItem(oxygenTank);
		
		user = new Player(1, 1, 1, 100, "player", null);
		db.findRoomByRoomId(1).setHasVisited(true);
		return db.findRoomByRoomId(1).getLongDesc();
	}
	
	public String response(String command) {
		DatabaseProvider.setInstance(new DerbyDatabase());
		IDatabase db = DatabaseProvider.getInstance();
		
		Room currentRoom = db.findRoomByRoomId(user.getRoomId());
		
		 // DIALOG MODE
	    if (user.isInDialog()) {
	        List<Pair<Room, Actor>> actorPairs = db.findActorsInRoom(currentRoom);
	        if (actorPairs.isEmpty()) {
	            return "There's no one to talk to.";
	        }

	        NPC target = (NPC) actorPairs.get(0).getRight(); // Assuming safe cast

	        if (command.equalsIgnoreCase("talk")) {
	            return "Talk WIP";
	        } 
	        else if (command.equalsIgnoreCase("give item")) {
	            Item questItem = target.getQuestItem();
	            if (questItem != null && db.findItemInInventory(user.getInventory()).contains(questItem)) {
	                int tempIndex = user.getInventory().getItemIndex(questItem);
	                user.getInventory().removeItem(tempIndex);
	                return target.getQuestFinQuip();
	            } 
	            else {
	                return "They have no need for anything you have.";
	            }
	        } 
	        else if (command.equalsIgnoreCase("attack")) {
	            if (target.getAttackable()) {
	                user.setDialog(false);
	                System.out.println("Player begins combat with NPC");
	                return target.getAttackQuip();
	            } 
	            else {
	                return "This character cannot be attacked!";
	            }
	        } 
	        else if (command.equalsIgnoreCase("leave")) {
	            user.setDialog(false);
	            return target.getLeaveQuip();
	        } 
	        else {
	            return "Error, command unknown";
	        }
	    }
		
		//MOVEMENT MODE
		if(command.equalsIgnoreCase("north") || command.equalsIgnoreCase("south") || command.equalsIgnoreCase("west") || command.equalsIgnoreCase("east") || command.equalsIgnoreCase("up") || command.equalsIgnoreCase("down")) {
			if(db.findRoomByRoomId(user.getRoomId()).getConnection(command) != null) {
				moveActor(db.findRoomByRoomId(user.getRoomId()).getConnection(command));
				
				if(db.findRoomByRoomId(user.getRoomId()).getHasVisited()) {
					return db.findRoomByRoomId(user.getRoomId()).getShortDesc();
				}
				else {
					db.findRoomByRoomId(user.getRoomId()).setHasVisited(true);
					
					System.out.println(db.findRoomByRoomId(user.getRoomId()).containsNPCS());
					
					return db.findRoomByRoomId(user.getRoomId()).getLongDesc();
				}
			}
			else {
				return "You can't go that way";
			}
			
		}
		
		if(command.equalsIgnoreCase("approach") && currentRoom.containsNPCS()) {
			user.setDialog(true);
			return "!!WIP!! You walk up to the NPC";
		}
		else if (command.equalsIgnoreCase("approach")) {
			return "There is no one in the room to talk to.";
		}
		
		if(command.equalsIgnoreCase("pick up")) {
			if(db.findRoomByRoomId(user.getRoomId()).getInventory().getItems().size() != 0) {
				user.getInventory().addItem(db.findRoomByRoomId(user.getRoomId()).getInventory().removeItem(0));
				Integer size = user.getInventory().getItems().size();
				return "You picked up the " + user.getInventory().getItem(size - 1).getItemName();
			}
			
			return "There is nothing to pick up";
		}
		
		if(command.equalsIgnoreCase("search")) {
			return db.findRoomByRoomId(user.getRoomId()).getLongDesc();
		}
		
		if(command.equalsIgnoreCase("check inventory")) {
			Integer size = user.getInventory().getItems().size();
			String items = "Your inventory:\n"; 
			if(size == 0) {
				return "There's nothing in your inventory";
			}
			for(int i = 0; i < size; i++) {
				items += user.getInventory().getItem(i).getItemName() + "\n";
			}
			return items;
		}
		
		return "I do not recognize that command";
	}
	
	public void moveActor(Integer roomID) {
		user.setRoomId(roomID);
	}
}
