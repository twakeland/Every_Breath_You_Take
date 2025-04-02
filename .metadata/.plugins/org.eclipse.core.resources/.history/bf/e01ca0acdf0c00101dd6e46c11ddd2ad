package edu.ycp.cs320.TBAG.model;

public class ItemWeapons extends Item {
    private Integer damage;

    public ItemWeapons(Integer uses, Integer value, String itemDescription, Integer damage) {
        super(uses, value, itemDescription);
        this.damage = damage;
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
