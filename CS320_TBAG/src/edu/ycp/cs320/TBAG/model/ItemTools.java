package edu.ycp.cs320.TBAG.model;

public class ItemTools extends Item {
    private Integer repairProgress;

    public ItemTools( Integer repairProgress) {
        super();
        this.repairProgress = repairProgress;
    }

    public void setDamage(int repairProgress) {
        this.repairProgress = repairProgress;
    }

    public Integer getrepairProgress() {
        return repairProgress;
    }

    public void use() {
        if (getUses() > 0 && repairProgress <= 100 ) {
            System.out.println("You are fixing this panel, the repair progress is at " + repairProgress + "%.");
            setUses(getUses() - 1);
            repairProgress += 15;
        } 
        
        else {
            System.out.println("The tool has no more uses left!");
        }
    }
}
