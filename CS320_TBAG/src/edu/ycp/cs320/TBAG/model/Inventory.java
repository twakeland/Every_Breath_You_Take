package edu.ycp.cs320.TBAG.model;

import java.util.ArrayList;

public class Inventory {
	private int inventoryId;
    private ArrayList<Integer> items;

    public Inventory() {
        items = new ArrayList<>();
    }
    
    public boolean isEmpty() {
    	return items.isEmpty();
    }

    public void addItem(int itemId) {
        items.add(itemId);
    }

    public int removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.remove(index);
        }
        return 0;
    }

    public boolean contains(int itemId) {
        return items.contains(itemId);
    }
    
    public int getItem(int index) {
    	return items.get(index);
    }
    
    public int getItemIndex(int itemId) {
    	return items.indexOf(itemId);
    }
    
    public ArrayList<Integer> getItems() {
    	return items;
    }
    
    public void setItems(ArrayList<Integer> items) {
    	this.items = items;
    }
    
    public int getInventoryId() {
    	return inventoryId;
    }
    
    public void setInventoryId(int inventoryId) {
    	this.inventoryId = inventoryId;
    }
}
