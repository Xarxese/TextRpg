package textrpg;
import java.util.Scanner;

public class GameLogic {

    static Scanner scanner = new Scanner(System.in);

    static Player player;

    public static boolean isRunning;
    
    public static String[] encounters ={"Battle","Battle","Battle","Rest","Rest"};
    
    public static String[] ennemies ={"Ogre","Ogre","Gobelin","Gobelin","Elementaire de pierre"};

    public static int place = 0, act = 1;

    public static String[] places = {"Everlasting Mountains", "Haunted Landlines","Chateau de l'empereur diabolique", "Salle du trône"};


    public static int readInt(String prompt, int userChoices) {
        int input;
        do{
            System.out.println(prompt);
            try {
                input = Integer.parseInt(scanner.next());
            } catch (Exception e) {
                input = -1;
                System.out.println("Please enter an integer!");
            }
        }while(input < 1 || input > userChoices);
        return input;
    }
    
    public static void clearConsole() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }
    
    public static void printSeparator(int n) {
        for (int i = 0; i < n ; i++) {
            System.out.print("➖");
        }
        System.out.println();
    }
    
    public static void printHeading(String title) {
        printSeparator(20);
        System.out.println(title);
        printSeparator(20);
    }
    
    public static void anythingToContinue() {
        System.out.println("\nEnter anything to continue...");
        scanner.next();
    }
    
    public static void startGame() {
        boolean nameSet = false;
        String name;

        clearConsole();
        printSeparator(40);
        printSeparator(30);
        System.out.println(" 😈😈😈 L'AGE DE L'EMPEREUR DIABOLIQUE 😈😈😈 ");
        System.out.println(" 😁 CE TEXT RPG A ETE FAIT PENDANT UN COURS DE JAVA 😁 ");
        printSeparator(30);
        printSeparator(40);
        anythingToContinue();


        do {
            clearConsole();
            printHeading("Quel est ton nom?");
            name = scanner.next();
            clearConsole();
            printHeading("Ton nom est "+name+ ".\n C'est correcte?");
            System.out.println("(1) Oui!😀 ");
            System.out.println("(2) Non, je veux changé mon nom🤔 ");
            int input = readInt("->", 2);
            if (input == 1) {
                nameSet = true;
            }
        } while (!nameSet);
        Story.printIntro();

        player = new Player(name);

        Story.printFirstActIntro();

        isRunning = true;

        gameLoop();
    }
    
    public static void checkAct() {
        if (player.xp >= 10 && act == 1) {
            act = 2;
            place = 1;
            Story.printFirstActOutro();
            // lvl up
            player.chooseTrait();
            Story.printSecondActIntro();
            ennemies[0] = "Evil Mercenary 👿 ";
            ennemies[1] = "Gobelin 👿 ";
            ennemies[2] = "Wolve Pack 👿 ";
            ennemies[3] = "Henchman of the Evil Emperor 👿 ";
            ennemies[4] = "Scary Stranger 👿 ";

            encounters[0] = "Battle";
            encounters[1] = "Battle";
            encounters[2] = "Battle";
            encounters[3] = "Rest";
            encounters[4] = "Shop";

        } else if (player.xp >= 50 && act == 2) {
            act = 3;
            place = 2 ;
            Story.printSecondActOutro();
            // lvl up
            player.chooseTrait();

            Story.printThirdActIntro();
            ennemies[0] = "Evil Mercenary 👿 ";
            ennemies[1] = "Evil Mercenary 👿 ";
            ennemies[2] = "Henchman of the Evil Emperor 👿👿 ";
            ennemies[3] = "Henchman of the Evil Emperor 👿👿 ";
            ennemies[4] = "Henchman of the Evil Emperor 👿👿 ";

            encounters[0] = "Battle";
            encounters[1] = "Battle";
            encounters[2] = "Battle";
            encounters[3] = "Battle";
            encounters[4] = "Shop";

            player.hp = player.maxHp;

        } else if (player.xp >= 100 && act == 3) {
            act = 4;
            place = 3;
            Story.printEnd(player);

            player.hp = player.maxHp;
            finalBattle();
        }
    }

    public static void randomEncounter() {
        int encounter = (int) (Math.random()* encounters.length);
        if (encounters[encounter].equals("Battle")) {
            randomBattle();
        }else if (encounters[encounter].equals("Rest")){
            takeRest();
        }else{
            shop();
        }
    }

    public static void continueJourney() {
        checkAct();
        if (act != 4) {
            randomEncounter();
        }
    }

    public static void characterInfos() {
        clearConsole();
        printHeading(" 📜📜📜 INFORMATION PERSONNAGE 📜📜📜 "); 
        System.out.println(player.name+"\tHP: "+player.hp+"/"+player.maxHp+" ❤️ ");
        printSeparator(20);
        System.out.println("XP: "+ player.xp+ "\tGold: "+player.gold+" 💰 ");
        printSeparator(20);
        System.out.println("# de Potions: "+ player.pots+" ⚱️ ");
        printSeparator(20);
        System.out.println("# de Repos: "+ player.restsLeft+" 🛌 ");
        printSeparator(20);

        if (player.numAtkUpgrades > 0) {
            System.out.println("Trait offensif 🗡️ : "+player.atkUpgrades[player.numAtkUpgrades - 1]);
            printSeparator(20);
        }
        if (player.numDefUpgrades > 0) {
            System.out.println("Trait défensif 🛡️ : "+player.defUpgrades[player.numDefUpgrades - 1]);
            printSeparator(20);
        }
        anythingToContinue();

    }

    public static void shop() {
        clearConsole();
        printHeading("Vous avez rencontré un mystérieux étranger. \nIl vous offre quelque chose:");
        int price = (int) (Math.random()*(10+player.pots*3)+10+player.pots);
        System.out.println("- Potion de vie ⚱️ :"+price+" d'or 💰 ");
        printSeparator(20);

        System.out.println("Voulez vous en acheter une ?\n(1) Oui!\n(2) Non merci.");
        int input = readInt("->", 2);
        if (input == 1) {
            clearConsole();
            if (player.gold >= price) {
                printHeading("Vous avez acheter une potion de vie ⚱️ pour "+price+ "d'or 💰 ");
                player.pots++;
                player.gold -= price;
            }
        }else {
            printHeading("Vous n'avez pas asser d'or pour acheter ça");
            anythingToContinue();
        }
    }
    
    public static void takeRest() {
        clearConsole();
        if (player.restsLeft >= 1) {
            printHeading("Voulez-vous vous reposez 😴😴 ?("+player.restsLeft+" repos restant).");
            System.out.println("(1) Oui\n(2) Non, pas maintenant.");

            int input = readInt("->", 2);
            if (input == 1) {
                clearConsole();
                if (player.hp < player.maxHp){
                    int hpRestored = (int) (Math.random()*(player.xp/4 +1) +10);
                    player.hp += hpRestored;
                    if (player.hp > player.maxHp) {
                        player.hp = player.maxHp;
                    }
                    System.out.println("Vous vous reposez et votre santé est restauré de "+hpRestored+" de points de vie ❤️ ");
                    System.out.println("Vous avez maintenant "+player.hp+"/"+player.maxHp+" de point de vie ❤️ ");
                    player.restsLeft--;
                }else{
                    System.out.println("Votre vie est pleine. Vous vous reposerez plus tard !");
                }
                anythingToContinue();
            }
        }
    }

    public static void randomBattle() {
        clearConsole();
        printHeading(" ⚔️ Vous avez rencontré une créature du mal 😨😨 Préparez vous à vous battre! ⚔️⚔️ ");
        anythingToContinue();

        battle(new Enemy(ennemies[(int)(Math.random()*ennemies.length)], player.xp));
    }

    public static void battle(Enemy enemy) {
        while (true) {
            clearConsole(); 
            printHeading(enemy.name+ "\nHP: " +enemy.hp + "/" + enemy.maxHp+"❤️ ");
            printHeading(player.name+ "\nHP: " +player.hp + "/" + player.maxHp+"❤️ ");
            System.out.println("Choissisez votre action");
            printSeparator(20);
            System.out.println("(1) Combattre ⚔️\n(2) Use Potion ⚱️\n(3) Fuir 💨");
            int input = readInt("->", 3);
            
            if (input == 1) {
                
                int dmg = player.attack() - enemy.defend();
                int dmgTook = enemy.attack() - player.defend();
                if (dmgTook < 0) {
                    dmg -= dmgTook/2;
                    dmgTook = 0;
                }
                if (dmg < 0) {
                    dmg = 0;
                }
                player.hp -= dmgTook;
                enemy.hp -= dmg;
                clearConsole();
                printHeading("Vous avez asséner "+ dmg + " 🎯 de dégats au " +enemy.name+ ".");
                printSeparator(15);
                System.out.println("L'ennemi " + enemy.name + " vous a assénez " +dmgTook+" 🎯 de dégats");
                anythingToContinue();

                if (player.hp <= 0) {
                    playerDie();
                    break;
                } else if (enemy.hp <= 0){
                    clearConsole();
                    printHeading("Vous avez vaincu "+enemy.name+"! 🥳🥳 ");
                    player.xp += enemy.xp;
                    System.out.println("Vous avez gagner "+enemy.xp+" d'XP! 🤓🤓 ");

                    Boolean addRest = (Math.random()*5 +1 <= 2.25);
                    int goldEarned = (int) (Math.random()*enemy.xp);

                    if (addRest) {
                        player.restsLeft++;
                        System.out.println("Vous avez gagné la chance d'obtenir un repos supplémentaire 🛌🛌 ");
                    }
                    if (goldEarned > 0) {
                        player.gold += goldEarned;
                        System.out.println("Vous avez collecter "+goldEarned+" 💰 d'or sur l'ennemi "+enemy.name);
                    }

                    anythingToContinue();
                    break;
                }

            } else if (input == 2) {
                clearConsole();
                if (player.pots > 0 && player.hp < player.maxHp) {
                    printHeading("Voulez vous boire une potion ⚱️ ? ("+player.pots+" left).");
                    System.out.println("(1) Oui\n(2) non peut-etre plus tard");
                    input = readInt("->", 2);
                    if (input == 1) {
                        player.hp = player.maxHp;
                        clearConsole();
                        printHeading("Vous avez bu une potion ⚱️ qui restaure votre vie à " +player.maxHp +"❤️ ");
                        anythingToContinue();
                    }
                } else {
                    printHeading("Vous n'avez pas de potion ⚱️ sur vous ou votre vie est pleine");
                }
            } else {
                clearConsole();
                if (act != 4) {
                        // Chance de 35% de fuir
                    if (Math.random()*10+1<=3.5) {
                        printHeading("Vous fuyer devant "+enemy.name+"❗");
                        anythingToContinue();
                        break;
                    }else{
                        printHeading("Vous n'avez pas réussi a vous enfuire");
                        int dmgTook = enemy.attack();
                        System.out.println("L'ennemi a reussi a vous assénez " +dmgTook+ " de dégats ❗");
                        anythingToContinue();
                        if (player.hp <= 0) {
                            playerDie();
                        }
                    }
                } else{
                    printHeading("❗❗❗ VOUS NE POUVEZ FUIR DEVANT L'EMPEREUR ❗❗❗");
                    anythingToContinue();
                }
            }
        }
    }
    
    public static void printMenu() {
        clearConsole();
        printHeading(places[place]);
        System.out.println("Choisi une action");
        printSeparator(20);
        System.out.println("(1) Continuer votre périple");
        System.out.println("(2) Informations personnage");
        System.out.println("(3) Quitter le jeu");
    }
    
    public static void finalBattle() {
        battle(new Enemy("L'EMPEREUR DIABOLIQUE👿👿👿👿👿👿👿👿👿👿",300));
        Story.printEnd(player);
        isRunning = false;
    }

    private static void playerDie() {
        clearConsole();
        printHeading("Vous êtes mort 💀💀💀 ");
        printHeading("Vous avez gagner" +player.xp + "d'Xp dans votre périple. Essayer d'en obtenir plus la prochaine fois !");
        System.out.println("Merci d'avoir jouer !");
    }

    public static void gameLoop() {
        while (isRunning) {
            printMenu();
            int input = readInt("->", 3);
            if (input == 1) {
                continueJourney();
            } else if (input == 2) {
                characterInfos();
            }else{
                isRunning = false;
            }
        }
    }
    
}
