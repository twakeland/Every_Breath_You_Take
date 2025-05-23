package edu.ycp.cs320.TBAG.persist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.ycp.cs320.TBAG.model.*;

public class InitialData {
	public static List<Room> getRooms() throws IOException {
		List<Room> roomList = new ArrayList<Room>();
		ReadCSV readRooms = new ReadCSV("rooms.csv");
		ReadCSV readConnections = new ReadCSV("connections.csv");
		try {
			// auto-generated primary key for rooms table
			Integer roomId = 1;
			while (true) {
				List<String> tuple = readRooms.next();
				List<String> tuple2 = readConnections.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Iterator<String> i2 = tuple2.iterator();
				Room room = new Room();
				room.setRoomId(roomId++);	
				room.setInventoryId(Integer.parseInt(i.next()));
				room.setRoomName(i.next());
				room.setShortDesc(i.next());
				room.setLongDesc(i.next());
				while (true) {
					if(i2.hasNext() == false) {
						break;
					}
					room.makeConnection(i2.next(), Integer.parseInt(i2.next()));
				}
				roomList.add(room);
			}
			return roomList;
		} finally {
			readRooms.close();
			readConnections.close();
		}
	}
	
	public static List<Item> getItems() throws IOException {
		List<Item> itemList = new ArrayList<Item>();
		ReadCSV readItems = new ReadCSV("items.csv");
		try {
			// auto-generated primary key for items table
			Integer itemId = 1;
			while (true) {
				List<String> tuple = readItems.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Item item = new Item();
				item.setItemId(itemId++);	
				item.setItemName(i.next());
				item.setUses(Integer.parseInt(i.next()));
				item.setValue(Integer.parseInt(i.next()));
				item.setItemType(i.next());
				item.setDescription(i.next());
				itemList.add(item);
			}
			return itemList;
		} finally {
			readItems.close();
		}
	}
	
	public static List<Inventory> getInventories() throws IOException {
		List<Inventory> inventoryList = new ArrayList<Inventory>();
		ReadCSV readInventories = new ReadCSV("inventories.csv");
		try {
			// auto-generated primary key for inventories table
			Integer inventoryId = 1;
			while (true) {
				List<String> tuple = readInventories.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Inventory inventory = new Inventory();
				inventory.setInventoryId(inventoryId++);
				while (true) {
					if(i.hasNext() == false) {
						break;
					}
					inventory.addItem(Integer.parseInt(i.next()));
				}
				inventoryList.add(inventory);
			}
			return inventoryList;
		} finally {
			readInventories.close();
		}
	}
	
	public static List<Actor> getActors() throws IOException {
		List<Actor> actorList = new ArrayList<Actor>();
		ReadCSV readActors = new ReadCSV("actors.csv");
		try {
			// auto-generated primary key for items table
			Integer actorId = 1;
			while (true) {
				List<String> tuple = readActors.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Actor actor = new Actor();
				actor.setActorId(actorId++);
				actor.setRoomId(Integer.parseInt(i.next()));
				actor.setInventoryId(Integer.parseInt(i.next()));
				actor.setHealth(Integer.parseInt(i.next()));
				actor.setActorName(i.next());
				actorList.add(actor);
			}
			return actorList;
		} finally {
			readActors.close();
		}
	}
}
