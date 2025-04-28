package edu.ycp.cs320.TBAG.model;

import java.util.ArrayList;

public class Inventory {
    private int inventorySize;
    private ArrayList<Item> items;

    public Inventory(int inventorySize) {
        this.inventorySize = inventorySize;
        items = new ArrayList<>();
    }
    
    public boolean isEmpty() {
    	return items.isEmpty();
    }

    public void setInvSize(int inventorySize) {
        this.inventorySize = inventorySize;
    }

    public int getInvSize() {
        return inventorySize;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
        }
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
