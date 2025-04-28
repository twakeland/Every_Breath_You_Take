package edu.ycp.cs320.TBAG.model;

public class ItemConsumables extends Item {
    private Integer healingAmount;

    public ItemConsumables() {
        super();
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
