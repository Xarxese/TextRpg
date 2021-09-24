package textrpg;

import java.util.ArrayList;

import textrpg.items.Armor;
import textrpg.items.Consommable;
import textrpg.items.Equipement;
import textrpg.items.Item;
import textrpg.items.Weapon;

public class Player extends Character {

    public int numAtkUpgrades, numDefUpgrades;

    int gold, restsLeft;

    ArrayList<Item> items = new ArrayList<>();

    Equipement equipement;

    public String[] atkUpgrades = {"Force 🔰","Force puissante 🔰🔰","Force Ultime 🔰🔰🔰","Force divine 🌀"};
    public String[] defUpgrades = {"Os lourds 🧬","Peau de pierre 🧬🧬","Écaille d'armure 🧬🧬🧬","Aura sacrée🌌"};
    
    public Player(String name) {
        super(name, 100, 0);

        this.numAtkUpgrades = 0;
        this.numDefUpgrades = 0;

        this.gold = 5;
        this.restsLeft = 1;
        // this.items.add(new Consommable("Potion de vie", new Heal(), 1));

        chooseTrait();
    }
    
    @Override
    public int attack() {

        return (int)(Math.random()*(xp/4 + numAtkUpgrades*3 +3) + xp/10 +numAtkUpgrades*2 + numDefUpgrades + 1);
    }
    
    @Override
    public int defend() {

        return (int)(Math.random()*(xp/4 + numDefUpgrades*3 +3) + xp/10 +numDefUpgrades*2 + numAtkUpgrades + 1);
    }

    @Override
    public void setAlive(boolean isAlive) {

        super.setAlive(isAlive);
        if (!this.isAlive) {
            GameLogic.clearConsole();
            GameLogic.printHeading("Vous êtes mort 💀💀💀 ");
            GameLogic.printHeading("Vous avez gagner" + xp + "d'Xp dans votre périple. Essayer d'en obtenir plus la prochaine fois !");
            System.out.println("Merci d'avoir jouer !");
        }
    }

    public Item getItem(String name) {
        return items.stream().filter(i -> name.equals(i.name))
                .findAny().orElse(new Item(name, 0));
    }
    public void setWeapon(Weapon weapons) {
        if (equipement.getWeapons()!= null) {
            items.add(equipement.getWeapons());
        }
        equipement.setWeapons(weapons);
    }
    public void setArmor(Armor armors) {
        if (equipement.getArmor()!= null) {
            items.add(equipement.getArmor());
        }
        equipement.setArmor(armors);
    }
    
    public void getInformation() {
        GameLogic.clearConsole();
        GameLogic.printHeading(" 📜📜📜 INFORMATION PERSONNAGE 📜📜📜 "); 
        System.out.println(name+"\tHP: "+hp+"/"+maxHp+" ❤️ ");
        GameLogic.printSeparator(20);
        System.out.println("XP: "+ xp+ "\tGold: "+gold+" 💰 ");
        GameLogic.printSeparator(20);

        for (Item i : items) {
            if (i instanceof Consommable) {
                System.out.println("# de " +i.name+ " : "+i.quantity);
            GameLogic.printSeparator(20);
            }
        }
        System.out.println("# de Repos: "+ restsLeft+" 🛌 ");
        GameLogic.printSeparator(20);

        if (numAtkUpgrades > 0) {
            System.out.println("Trait offensif 🗡️ : "+atkUpgrades[numAtkUpgrades - 1]);
            GameLogic.printSeparator(20);
        }
        if (numDefUpgrades > 0) {
            System.out.println("Trait défensif 🛡️ : "+defUpgrades[numDefUpgrades - 1]);
            GameLogic.printSeparator(20);
        }

        int input = GameLogic.answer("Changer d'équipement ", new String[]{"1 - Armes., 2 - Armure., 3 - Non."});
        GameLogic.printHeading("Equipement");
        GameLogic.printSeparator(15);
        if (input == 1) {
            System.out.println(equipement.getWeapons()+" : équipé");
        } else if (input == 2){

        }

        GameLogic.anythingToContinue();

    }
    
    public void chooseTrait() {
        GameLogic.clearConsole();
        GameLogic.printHeading("Choisi un trait");
        System.out.println("(1) "+atkUpgrades[numAtkUpgrades]);
        System.out.println("(2) "+defUpgrades[numDefUpgrades]);

        int input = GameLogic.readInt("-> ", 2);
        GameLogic.clearConsole();
        if (input == 1) {
            GameLogic.printHeading("Ton choix est "+atkUpgrades[numAtkUpgrades]+" !");
            numAtkUpgrades++;
        } else{
            GameLogic.printHeading("Ton choix est "+defUpgrades[numDefUpgrades]+" !");
            numDefUpgrades++;
        }
        GameLogic.anythingToContinue();
    }

    public void takeRest() {
        GameLogic.clearConsole();
        if (restsLeft >= 1) {
            GameLogic.printHeading("Voulez-vous vous reposez 😴😴 ?("+restsLeft+" repos restant).");
            System.out.println("(1) Oui\n(2) Non, pas maintenant.");

            int input = GameLogic.readInt("->", 2);
            if (input == 1) {
                GameLogic.clearConsole();
                if (hp < maxHp){
                    int hpRestored = (int) (Math.random()*(xp/4 +1) +10);
                    hp += hpRestored;
                    if (hp > maxHp) {
                        hp = maxHp;
                    }
                    System.out.println("Vous vous reposez et votre santé est restauré de "+hpRestored+" de points de vie ❤️ ");
                    System.out.println("Vous avez maintenant "+hp+"/"+maxHp+" de point de vie ❤️ ");
                    restsLeft--;
                }else{
                    System.out.println("Votre vie est pleine. Vous vous reposerez plus tard !");
                }
                GameLogic.anythingToContinue();
            }
        }
    }
}
