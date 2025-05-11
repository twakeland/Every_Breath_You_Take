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
		
		db.findRoomByRoomId(1).setHasVisited(true);
		return db.findRoomByRoomId(1).getLongDesc();
	}
	
	public String response(String command) {
		DatabaseProvider.setInstance(new DerbyDatabase());
		IDatabase db = DatabaseProvider.getInstance();
		
		if(command.equalsIgnoreCase("north") || command.equalsIgnoreCase("south") || command.equalsIgnoreCase("west") || command.equalsIgnoreCase("east") || command.equalsIgnoreCase("up") || command.equalsIgnoreCase("down")) {
			if(db.findRoomByRoomId(db.findActorByActorId(1).getRoomId()).getConnection(command) != null) {
				db.updateActorRoomId(1, db.findRoomByRoomId(db.findActorByActorId(1).getRoomId()).getConnection(command));
				
				if(db.findRoomByRoomId(db.findActorByActorId(1).getRoomId()).getHasVisited()) {
					return db.findRoomByRoomId(db.findActorByActorId(1).getRoomId()).getShortDesc();
				}
				else {
					db.findRoomByRoomId(db.findActorByActorId(1).getRoomId()).setHasVisited(true);
					//Good lord please ignore how barebones this convo system is so far-->
					System.out.println(db.findRoomByRoomId(db.findActorByActorId(1).getRoomId()).containsNPCS());
					if(db.findRoomByRoomId(db.findActorByActorId(1).getRoomId()).containsNPCS()){
						String tempString = db.findRoomByRoomId(db.findActorByActorId(1).getRoomId()).getLongDesc();
						//tempString += "\n" + db.findRoomByRoomId(user.getRoomId()).NPCS.get(0).getTempConvo();
						
						return tempString;
					}
					
					System.out.println("No NPCS detected, initial descr overridden");
					return db.findRoomByRoomId(db.findActorByActorId(1).getRoomId()).getLongDesc();
					
				}
			}
			else {
				return "You can't go that way";
			}
			
		}
		
		if(command.equalsIgnoreCase("pick up")) {
			if(db.findInventoryByInventoryId(db.findRoomByRoomId(db.findActorByActorId(1).getRoomId()).getInventoryId()).getItems().size() != 0) {
				int itemId = db.findInventoryByInventoryId(db.findRoomByRoomId(db.findActorByActorId(1).getRoomId()).getInventoryId()).removeItem(0);
				db.findInventoryByInventoryId(db.findActorByActorId(1).getInventoryId()).addItem(itemId);
				//Integer size = user.getInventory().getItems().size();
				return "You picked up the " + db.findItemByItemId(itemId).getItemName();
			}
			
			return "There is nothing to pick up";
		}
		
		if(command.equalsIgnoreCase("search")) {
			return db.findRoomByRoomId(db.findActorByActorId(1).getRoomId()).getLongDesc();
		}
		
		if(command.equalsIgnoreCase("check inventory")) {
			Integer size = db.findActorByActorId(1).getInventory().getItems().size();
			String items = "Your inventory:\n"; 
			if(size == 0) {
				return "There's nothing in your inventory";
			}
			for(int i = 0; i < size; i++) {
				items += db.findItemByItemId(db.findActorByActorId(1).getInventory().getItem(i)).getItemName() + "\n";
			}
			return items;
		}
		
		return "I do not recognize that command";
	}
}
