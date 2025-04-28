package edu.ycp.cs320.TBAG.model;

import java.util.ArrayList;

public class Inventory {
	private int inventoryId;
	private ArrayList<Integer> items;

	public Inventory() {
		items = new ArrayList<Integer>();
	}

	public void setInventoryId(int inventoryId) {
	    this.inventoryId = inventoryId;
	}

	public int getInventoryId() {
	    return inventoryId;
	}
	
	public ArrayList<Integer> getItems(){
		return items;
	}

	public void addItem(Integer item) {
	    items.add(item);
	}

	public Integer removeItem(int index) {
	    return items.remove(index);
	}
	
	public Integer getItem(int index) {
		return items.get(index);
	}
	
	public boolean contains(Integer searchItem) {
	    if(items.contains(searchItem)) {
	      return true;
	    }
	    else {
	      return false;
	    }
	}
}
