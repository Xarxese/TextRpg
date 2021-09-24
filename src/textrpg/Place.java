package textrpg;

import textrpg.items.Item;

public class Place {
    int index;
    String name;
    Enemy[] bestiaire;
    String[] encounters = {"Battle","Rest","Shop","Battle"};
    Place[] places;

    public Place(int index, String Name, Enemy[] bestiaire, String[] rencontres) {
        this.name = Name;
        this.index = index;
        this.bestiaire = bestiaire;
        this.encounters = rencontres;
    }
    public Place(int index, String Name, Enemy[] bestiaire) {
        this.name = Name;
        this.index = index;
        this.bestiaire = bestiaire;
    }

    public void randomEncounter(Player player) {
        int encounter = (int) (Math.random() * encounters.length);
        switch (encounters[encounter]) {
            case "Battle":
                this.randomBattle(player);
                break;
            case "Rest":
                player.takeRest();
                break;
            case "Chest":
                int gold =(int) (Math.random()* (5 - -1 + 1) + 1);
                System.out.println("Vous avez trouvez un coffre qui contient " +gold+"💰 d'or !");
                player.gold += gold;
                break;
            default:
                GameLogic.shop();
                break;
        }
}

    public void randomBattle(Player player) {

        Enemy e = bestiaire[(int) (Math.random()* bestiaire.length)];
        GameLogic.clearConsole();
        GameLogic.printHeading(" ⚔️ Vous avez rencontré une créature du mal 😨😨 Préparez vous à vous battre! ⚔️⚔️ ");
        GameLogic.anythingToContinue();

        while (player.isAlive) {
            GameLogic.clearConsole(); 
            GameLogic.printHeading(e.name+ "\nHP: " +e.hp + "/" + e.maxHp+"❤️ ");
            GameLogic.printHeading(player.name+ "\nHP: " +player.hp + "/" + player.maxHp+"❤️ ");

            System.out.println("Choissisez votre action");
            GameLogic.printSeparator(20);
            System.out.println("(1) Combattre ⚔️\n(2) Use Potion ⚱️\n(3) Fuir 💨");
            int input = GameLogic.readInt("->", 3);
            
            if (input == 1) {
                
                int playerDmg = player.attack() - e.defend();
                int enemyDmg = e.attack() - player.defend();


                GameLogic.clearConsole();
                GameLogic.printHeading("Vous avez asséner "+ e.receive(playerDmg) + " 🎯 de dégats au " +e.name+ ".");
                GameLogic.printSeparator(15);
                System.out.println("L'ennemi " + e.name + " vous a assénez " +player.receive(enemyDmg)+ "🎯 de dégats");
                GameLogic.anythingToContinue();
                
                if (!e.isAlive){
                    GameLogic.clearConsole();
                    GameLogic.printHeading("Vous avez vaincu "+e.name+"! 🥳🥳 ");
                    player.xp += e.xp;
                    System.out.println("Vous avez gagner "+e.xp+" d'XP! 🤓🤓 ");

                    Boolean addRest = (Math.random()*5 +1 <= 2.25);
                    int goldEarned = (int) (Math.random()*e.xp);

                    if (addRest) {
                        player.restsLeft++;
                        System.out.println("Vous avez gagné la chance d'obtenir un repos supplémentaire 🛌🛌 ");
                    }
                    if (goldEarned > 0) {
                        player.gold += goldEarned;
                        System.out.println("Vous avez collecter "+goldEarned+" 💰 d'or sur l'ennemi "+e.name);
                    }

                    GameLogic.anythingToContinue();
                    break;
                }

                } else if (input == 2) {
                    /*
                    *
                    *PRPOSER UNE SELECTION DES ITEMS DISPONIBLE SUR PLAYER
                    */
                    GameLogic.clearConsole();
                    Item potion = player.getItem("Potion de vie");

                    if (potion.quantity > 0 && player.hp < player.maxHp) {
                        
                        GameLogic.printHeading("Voulez vous boire une potion ⚱️ ? ("+potion.quantity+" left).");
                        System.out.println("(1) Oui\n(2) non peut-etre plus tard");
                        input = GameLogic.readInt("->", 2);
                        if (input == 1) {
                            player.hp = player.maxHp;
                            GameLogic.clearConsole();
                            potion.quantity--;
                            if (potion.quantity == 0) {
                                player.items.remove(potion);
                            }
                            GameLogic.printHeading("Vous avez bu une "+potion.name+" ⚱️ qui restaure votre vie à " +player.maxHp +"❤️ ");
                            GameLogic.anythingToContinue();
                        }
                    } else {
                        GameLogic.printHeading("Vous n'avez pas de potion ⚱️ sur vous ou votre vie est pleine");
                    }
                } else {
                    GameLogic.clearConsole();
                    if (index != 4) {
                            // Chance de 35% de fuir
                        if (Math.random()*10+1<=3.5) {
                            GameLogic.printHeading("Vous fuyer devant "+e.name+"❗");
                            GameLogic.anythingToContinue();
                            break;
                        }else{
                            GameLogic.printHeading("Vous n'avez pas réussi a vous enfuire");
                            int enemyDmg = e.attack();
                            System.out.println("L'ennemi a reussi a vous assénez " +(enemyDmg < 0 ? 0 : enemyDmg)+ " de dégats ❗");
                            player.receive(enemyDmg);
                            GameLogic.anythingToContinue();
                            
                        }
                    } else{
                        GameLogic.printHeading("❗❗❗ VOUS NE POUVEZ FUIR DEVANT L'EMPEREUR ❗❗❗");
                        int enemyDmg = e.attack();
                        System.out.println("L'empereur a reussi a vous assénez " +(enemyDmg < 0 ? 0 : enemyDmg)+ " de dégats ❗");
                        player.receive(enemyDmg);
                        GameLogic.anythingToContinue();
                    }
            }
        }
    }

}
