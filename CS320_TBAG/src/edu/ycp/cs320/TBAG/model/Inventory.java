package edu.ycp.cs320.TBAG.model;

import java.util.ArrayList;

public class Inventory {
	private int inventoryId;
	private ArrayList<Item> items;

	public Inventory() {
		items = new ArrayList<Item>();
	}

	public void setInventoryId(int inventoryId) {
	    this.inventoryId = inventoryId;
	}

	public int getInventoryId() {
	    return inventoryId;
	}
	
	public ArrayList<Item> getItems(){
		return items;
	}

	public void addItem(Item item) {
	    items.add(item);
	}

	public Item removeItem(int index) {
	    return items.remove(index);
	}
	
	public Item getItem(int index) {
		return items.get(index);
	}
	
	public boolean contains(Item searchItem) {
	    if(items.contains(searchItem)) {
	      return true;
	    }
	    else {
	      return false;
	    }
	}
}
