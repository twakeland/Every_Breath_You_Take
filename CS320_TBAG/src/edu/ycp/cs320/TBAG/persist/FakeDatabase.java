package edu.ycp.cs320.TBAG.persist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.ycp.cs320.TBAG.model.Room;
import edu.ycp.cs320.TBAG.model.Actor;
import edu.ycp.cs320.TBAG.model.Inventory;
import edu.ycp.cs320.TBAG.model.Item;
import edu.ycp.cs320.TBAG.model.Pair;

public class FakeDatabase implements IDatabase {
	
	private List<Room> roomList;
	private List<Item> itemList;
	
	public FakeDatabase() {
		roomList = new ArrayList<Room>();
		itemList = new ArrayList<Item>();
		
		// Add initial data
		readInitialData();
		
		System.out.println(roomList.size() + " rooms");
		System.out.println(itemList.size() + " items");
	}
	
	public Room findRoomByRoomId(int roomId) {
		for(Room room : roomList) {
			if(room.getRoomId() == roomId) {
				return room;
			}
		}
		return null;
	}
	
	public Item findItemByItemId(int itemId) {
		for(Item item : itemList) {
			if(item.getItemId() == itemId) {
				return item;
			}
		}
		return null;
	}
	
	
	

	public void readInitialData() {
		try {
			roomList.addAll(InitialData.getRooms());
		} catch (IOException e) {
			throw new IllegalStateException("Couldn't read initial data", e);
		}
	}
}
