package edu.ycp.cs320.TBAG.model;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> items;

    public Inventory() {
        items = new ArrayList<>();
    }
    
    public boolean isEmpty() {
    	return items.isEmpty();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public Item removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.remove(index);
        }
        return null;
    }

    public boolean contains(Item searchItem) {
        return items.contains(searchItem);
    }
    
    public Item getItem(int index) {
    	return items.get(index);
    }
    
    public int getItemIndex(Item item) {
    	return items.indexOf(item);
    }
    
    public ArrayList<Item> getItems() {
    	return items;
    }
    
    public void setItems(ArrayList<Item> items) {
    	this.items = items;
    }
    
    
    // New method to get an item by description
    public Item getItemByString(String description) {
        for (Item item : items) {
            if (item.getDescription().equalsIgnoreCase(description)) {
                return item;
            }
        }
        return null;
    }
}
