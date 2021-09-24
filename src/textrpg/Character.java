package textrpg;

public abstract class Character {
    public String name;
    public int maxHp, hp, xp;
    boolean isAlive = true;

    

    public Character(String name, int maxHp, int xp) {
        this.name = name;
        this.maxHp = maxHp;
        this.xp = xp;
        this.hp = maxHp;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public int receive(int dmg) {
        if (dmg < 0) {
            dmg = 0;
        }
        this.hp -= dmg;
        if (this.hp <= 0) {
            this.isAlive = false;
        }
        return dmg;
    }


    public abstract int attack();
    public abstract int defend();




}
