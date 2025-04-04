package edu.ycp.cs320.TBAG.model;

import java.util.ArrayList;

public class Inventory {
	private int inventorySize;
	private ArrayList<Item> items;

	public Inventory(int inventorySize) {
		this.inventorySize = inventorySize;
		items = new ArrayList<Item>();
	}

	public void setSize(int inventorySize) {
	    this.inventorySize = inventorySize;
	}

	public int getSize() {
	    return inventorySize;
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
