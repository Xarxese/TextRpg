package textrpg.items;

import textrpg.Player;

public class Equipement {
    Weapon weapons;
    Armor armor;
    public Equipement() {
        
    }

    public Weapon getWeapons() {
        return this.weapons;
    }

    public Weapon setWeapons(Weapon weapon) {
        return this.weapons;
    }

    public Armor getArmor() {
        return this.armor;
    }

    public void setArmor(Armor armor) {
        
        this.armor = armor;
    }

}
