package edu.ycp.cs320.TBAG.model;

public class ItemConsumables extends Item {
    private int healingAmount;

    public ItemConsumables(String itemName, int uses, int value, String itemDescription, int healingAmount) {
        super(itemName, uses, value, itemDescription);
    }

    public void setHealingAmount(int healingAmount) {
        this.healingAmount = healingAmount;
    }

    public Integer getHealingAmount() {
        return healingAmount;
    }

    public void use() {
        if (getUses() > 0) {
            System.out.println("You consume the item and restore " + healingAmount + " HP.");
            setUses(getUses() - 1);
        } else {
            System.out.println("The consumable is empty!");
        }
    }
}
