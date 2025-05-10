package edu.ycp.cs320.TBAG.model;

public class Item {
	private Integer itemID, uses, value;
	private String itemDescription, itemName,itemType;

	public Item() {
		
	}
	
	public void setItemId(int itemID) {
		this.itemID = itemID;
	}
	
	public Integer getItemId() {
		return itemID;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public String getItemName() {
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
	
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	
	public String getItemType() {
		return itemType;
	}

	public void setDescription(String itemDescription) {
	    this.itemDescription = itemDescription;
	}
	
	public String getDescription() {
	    return itemDescription;
	}
}
