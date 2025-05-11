package edu.ycp.cs320.TBAG.model;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;


public class Room {
	private Integer roomID;
	private Integer inventoryID;
	private String roomName, shortDesc, longDesc;
	private Inventory inventory;
	private Boolean hasVisited;
	private Map<String, Integer> connections;
	public ArrayList<NPC> NPCS;
	
	public Room() {
		this.hasVisited = false;
		inventory = new Inventory();
		connections = new TreeMap<>();
		NPCS = new ArrayList<NPC>();
	}
	
	public boolean containsNPCS(){
		if (NPCS.isEmpty()) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public void setRoomId(Integer roomID) {
		this.roomID = roomID;
	}
	
	public int getRoomId() {
		return roomID;
	}
	
	public void setInventoryId(Integer inventoryID) {
		this.inventoryID = inventoryID;
	}
	
	public int getInventoryId() {
		return inventoryID;
	}
	
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	public String getRoomName() {
		return roomName;
	}
	
	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}
	
	public String getShortDesc() {
		return shortDesc;
	}
	
	public void setLongDesc(String longDesc) {
		this.longDesc = longDesc;
	}
	
	public String getLongDesc() {
		return longDesc;
	}
	
	public void setHasVisited(Boolean hasVisited) {
		this.hasVisited = hasVisited;
	}
	
	public Boolean getHasVisited() {
		return hasVisited;
	}
	
	public Inventory getInventory() {
		return this.inventory;
	}
	
	public void setConnections(Map<String, Integer> connections) {
		this.connections = connections;
	}
	
	public Map<String, Integer> getConnections() {
		return this.connections;
	}
	
	public void makeConnection(String direction, Integer connectionID) {
		connections.put(direction, connectionID);
	}
	
	public Integer getConnection(String direction) {
		direction = direction.toLowerCase(Locale.ENGLISH);
		return connections.get(direction);
	}
	
	public void addNPC(NPC newNPC) {
		NPCS.add(newNPC);
	}
	
	public void removeNPC(int index) {
		NPCS.remove(index);
	}
	public NPC getNPC(int index) {
		return NPCS.get(index);
	}
}