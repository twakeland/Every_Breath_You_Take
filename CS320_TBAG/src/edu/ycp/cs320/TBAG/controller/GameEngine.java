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
	private Room start, hallway, lab, basement;
	private Item axe, healthKit, oxygenTank;
	private Player user;
	private NPC tempNPC;
	
	
	public String setData() {
		
		DatabaseProvider.setInstance(new DerbyDatabase());
		IDatabase db = DatabaseProvider.getInstance();
		
		//Temp addNPC to hallway
		tempNPC = new NPC(2, 2, 2, 50, "temp", false, "A mysterious stranger stands in the corner, his face masked by shadows.");
		db.findRoomByRoomId(2).addNPC(tempNPC);
		
		axe = new Item("Axe", 5, 12, "A worn axe used to break down wooden barricades");
		healthKit = new Item("Health Kit", 5, 20, "A packet filled with single-use health stims");
		oxygenTank = new Item("Oxygen Tank", 0, 35, "A sizeable oxygen tank. Great for longer trips underwater");		
		
		db.findRoomByRoomId(2).getInventory().addItem(axe);
		db.findRoomByRoomId(3).getInventory().addItem(healthKit);
		db.findRoomByRoomId(4).getInventory().addItem(oxygenTank);
		
		user = new Player(1, 1, 1, 100, "player");
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
						tempString += "\n" + db.findRoomByRoomId(user.getRoomId()).NPCS.get(0).getTempConvo();
						
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
