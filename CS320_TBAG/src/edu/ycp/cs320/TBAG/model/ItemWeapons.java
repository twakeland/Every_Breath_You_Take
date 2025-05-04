package edu.ycp.cs320.TBAG.model;

public class ItemWeapons extends Item {
    private int damage;

    public ItemWeapons(String itemName, int uses, int value, String itemDescription, int damage) {
        super(itemName, uses, value, itemDescription);
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Integer getDamage() {
        return damage;
    }

    public void use() {
        if (getUses() > 0) {
            System.out.println("You attack with the weapon, dealing " + damage + " damage.");
            setUses(getUses() - 1);
        } else {
            System.out.println("The weapon has no more uses left!");
        }
    }
}
