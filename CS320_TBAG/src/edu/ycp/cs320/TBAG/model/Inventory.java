package edu.ycp.cs320.TBAG.model;

import java.util.ArrayList;

public class Inventory {
    private int inventorySize;
    private ArrayList<Item> items;

    public Inventory(int inventorySize) {
        this.inventorySize = inventorySize;
        items = new ArrayList<>();
    }

    public void setSize(int inventorySize) {
        this.inventorySize = inventorySize;
    }

    public int getSize() {
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

    // New method to get an item by description
    public Item getItem(String description) {
        for (Item item : items) {
            if (item.getDescription().equalsIgnoreCase(description)) {
                return item;
            }
        }
        return null;
    }
}
