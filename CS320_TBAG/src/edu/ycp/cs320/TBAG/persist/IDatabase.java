package edu.ycp.cs320.TBAG.persist;

import java.util.List;
import java.util.Map;

import edu.ycp.cs320.TBAG.model.Room;
import edu.ycp.cs320.TBAG.model.Actor;
import edu.ycp.cs320.TBAG.model.Inventory;
import edu.ycp.cs320.TBAG.model.Item;
import edu.ycp.cs320.TBAG.model.Pair;

public interface IDatabase {
	public Room findRoomByRoomId(int roomId);
	//public Item findItemByItemId(int itemID);
	//public List<Pair<Room, Actor>> findActorsInRoom(Room Room);
	//public List<Pair<Room, Inventory>> findRoomInventory(Room Room);
	//public List<Pair<Actor, Inventory>> findActorInventory(Actor actor);
	//public List<Pair<Inventory, Item>> findItemInInventory(Inventory inventory);
	public Map<String, Integer> findConnectionsByRoomId(int roomId);
}
