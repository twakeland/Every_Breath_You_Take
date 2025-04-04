package edu.ycp.cs320.TBAG.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


public class Room {
	private Integer roomID;
	private String shortDesc, longDesc;
	private Inventory inventory;
	private Boolean hasVisited;
	private Map<String, Integer> connections;
	public ArrayList<NPC> NPCS;
	
	public Room(Integer roomID, String shortDesc, String longDesc) {
		this.roomID = roomID;
		this.shortDesc = shortDesc;
		this.longDesc = longDesc;
		this.hasVisited = false;
		inventory = new Inventory(10);
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
	
	public void setRoomID(Integer roomID) {
		this.roomID = roomID;
	}
	
	public int getRoomID() {
		return roomID;
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
	
	public void makeConnection(String direction, Integer connectionID) {
		connections.put(direction, connectionID);
	}
	
	public Integer getConnection(String direction) {
		return connections.get(direction);
	}
	
	public void addNPC(NPC newNPC) {
		NPCS.add(newNPC);
	}
	
	public void removeNPC(int index) {
		NPCS.remove(index);
	}
}
