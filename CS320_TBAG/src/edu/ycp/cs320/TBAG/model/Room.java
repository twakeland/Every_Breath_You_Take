package edu.ycp.cs320.TBAG.model;

import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class Room {
	private Integer roomID;
	private String shortDesc, longDesc;
	private Inventory inventory;
	private Boolean hasVisited;
	private Map<String, Integer> connections;
	
	public Room(Integer roomID, String shortDesc, String longDesc) {
		this.roomID = roomID;
		this.shortDesc = shortDesc;
		this.longDesc = longDesc;
		this.hasVisited = false;
		inventory = new Inventory(10);
		connections = new TreeMap<>();
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
		direction = direction.toLowerCase(Locale.ENGLISH);
		return connections.get(direction);
	}
}
