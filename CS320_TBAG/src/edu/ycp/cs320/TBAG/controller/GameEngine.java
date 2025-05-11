package edu.ycp.cs320.TBAG.controller;

import edu.ycp.cs320.TBAG.model.Room;
import edu.ycp.cs320.TBAG.model.Item;
import edu.ycp.cs320.TBAG.model.ItemWeapons;
import edu.ycp.cs320.TBAG.model.ItemConsumables;
import edu.ycp.cs320.TBAG.model.Player;
import edu.ycp.cs320.TBAG.model.NPC;
import edu.ycp.cs320.TBAG.persist.DatabaseProvider;
import edu.ycp.cs320.TBAG.persist.IDatabase;
import edu.ycp.cs320.TBAG.persist.FakeDatabase;
import edu.ycp.cs320.TBAG.persist.DerbyDatabase;

public class GameEngine {
	private Player user;
	private NPC tempNPC;
	
	
	public String setData() {
		
		DatabaseProvider.setInstance(new DerbyDatabase());
		IDatabase db = DatabaseProvider.getInstance();
		
		//Temporary addNPC to hallway
		Item tempQuestItem = new Item();
		//lab.getInventory().addItem(tempQuestItem);
		tempNPC = new NPC(2, 2, 7, 50, "Stranger", true, "You punch the stranger in the throat for no reason. \n\"Have at thee!\" he yells, readying his weapon.", "You leave the stranger to his shenanigans.", "You wouldn't happen to have a shiny rock on you?", "You remove the shiny rock you found and hand it to the stranger. His eyes shine with happiness.", tempQuestItem);
		//hallway.addNPC(tempNPC);	
		
		user = new Player(1, 1, 1, 100, "player", null);
		db.findRoomByRoomId(1).setHasVisited(true);
		return db.findRoomByRoomId(1).getLongDesc();
	}
	
	public String response(String command) {
		DatabaseProvider.setInstance(new DerbyDatabase());
		IDatabase db = DatabaseProvider.getInstance();
		
		if(command.equalsIgnoreCase("north") || command.equalsIgnoreCase("south") || command.equalsIgnoreCase("west") || command.equalsIgnoreCase("east") || command.equalsIgnoreCase("up") || command.equalsIgnoreCase("down")) {
			if(db.findRoomByRoomId(user.getRoomId()).getConnection(command) != null) {
				moveActor(db.findRoomByRoomId(user.getRoomId()).getConnection(command));
				
				if(db.findRoomByRoomId(user.getRoomId()).getHasVisited()) {
					return db.findRoomByRoomId(user.getRoomId()).getShortDesc();
				}
				else {
					db.findRoomByRoomId(user.getRoomId()).setHasVisited(true);
					//Good lord please ignore how barebones this convo system is so far-->
					System.out.println(db.findRoomByRoomId(user.getRoomId()).containsNPCS());
					if(db.findRoomByRoomId(user.getRoomId()).containsNPCS()){
						String tempString = db.findRoomByRoomId(user.getRoomId()).getLongDesc();
						//tempString += "\n" + db.findRoomByRoomId(user.getRoomId()).NPCS.get(0).getTempConvo();
						
						return tempString;
					}
					
					System.out.println("No NPCS detected, initial descr overridden");
					return db.findRoomByRoomId(user.getRoomId()).getLongDesc();
					
				}
			}
			else {
				return "You can't go that way";
			}
			
		}
		
		if(command.equalsIgnoreCase("pick up")) {
			if(db.findInventoryByInventoryId(db.findRoomByRoomId(user.getRoomId()).getInventoryId()).getItems().size() != 0) {
				int itemId = db.findInventoryByInventoryId(db.findRoomByRoomId(user.getRoomId()).getInventoryId()).removeItem(0);
				user.getInventory().addItem(itemId);
				//Integer size = user.getInventory().getItems().size();
				return "You picked up the " + db.findItemByItemId(itemId).getItemName();
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
				items += db.findItemByItemId(user.getInventory().getItem(i)).getItemName() + "\n";
			}
			return items;
		}
		
		return "I do not recognize that command";
	}
	
	public void moveActor(Integer roomID) {
		user.setRoomId(roomID);
	}
}
