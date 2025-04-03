package edu.ycp.cs320.TBAG.model;

public class Item {
	private Integer uses, value;
	private String itemDescription, itemName;

	public Item(String itemName, Integer uses, Integer value, String itemDescription) {
		this.uses = uses;
		this.value = value;
		this.itemDescription = itemDescription;
	}
	
	public void setName(String itemName) {
		this.itemName = itemName;
	}
	
	public String getName() {
		return itemName;
	}
	
	public void setUses(int uses) {
	    this.uses = uses;
	}
	
	public Integer getUses() {
	    return uses;
	}

	public void setValue(int value) {
	    this.value = value;
	}
	
	public Integer getValue() {
	    return value;
	}

	public void setUses(String itemDescription) {
	    this.itemDescription = itemDescription;
	}
	
	public String getDescription() {
	    return itemDescription;
	}
}
