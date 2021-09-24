package textrpg;
import java.util.ArrayList;
import java.util.Scanner;

import textrpg.items.Item;

public class GameLogic {

    static Scanner scanner = new Scanner(System.in);

    static Player player;

    public static boolean isRunning;

    public static int place = 0;

    public static ArrayList<Place> initPlaces(){


        ArrayList<Place> places = new ArrayList<>();

        Place twon = new Place(0, "🏫 Town 🏫",new Enemy[]{
            new Enemy("Lapin", 5),
            new Enemy("Poulet", 5),
            new Enemy("Canard", 5),
        },new String[]{"Shop", "Chest","Chest","Battle"});

        Place forest = new Place(1, "🏞️ Forest 🏞️",new Enemy[]{
            new Enemy("Wolf", 10),
            new Enemy("Bear", 20),
            new Enemy("Squirel", 1),
        },new String[]{"Rest", "Chest","Shop","Battle","Battle","Battle"});

        Place castle = new Place(2, "🏰 Castle 🏰",new Enemy[]{
            new Enemy("Mimic", 25),
            new Enemy("Sword :qn", 25),
            new Enemy("Axe Man", 25),
        },new String[]{"Shop", "Chest","Battle","Battle","Battle","Battle"});
        Place throne = new Place(3,"🧛‍♀️ Throne 🧛‍♀️",new Enemy[]{
            new Enemy("EMPEREUR DIABOLIQUE👿👿👿👿👿👿👿👿👿👿", 300)
        });

        places.add(twon);
        places.add(forest);
        places.add(castle);
        places.add(throne);
        return places;
    }

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
        for (int i = 0; i < 5; i++) {
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

        initPlaces().get(0);

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
    
    public static void printMenu() {
        clearConsole();
        printHeading(initPlaces().get(place).name);
        System.out.println("Choisi une action");
        printSeparator(20);
        System.out.println("(1) Continuer votre périple");
        System.out.println("(2) Informations personnage");
        System.out.println("(3) Quitter le jeu");
    }
    
    public static void finalBattle() {
        initPlaces().get(place).randomBattle(player);
        if (player.isAlive) {
            Story.printEnd(player);
        }
        isRunning = false;
    }

    public static void gameLoop() {
        while (isRunning) {
            printMenu();
            int input = readInt("->", 3);
            switch (input) {
                case 1:
                    continueJourney();
                    break;
                case 2:
                    clearConsole();
                    player.getInformation();
                    anythingToContinue();
                    break;
                default:
                    isRunning = false;
                    break;
            }
        }
    }
    // 
    // TO MOVE
    // 
    public void name() {
        
    }

    public static void shop() {
        Item[] shop = {
            new Item("Potion de vie ⚱️", 0),
            new Item("Bombe 💣", 0),
            // new Armor("Cuirasse 🛡", 0),
            // new Weapons("Sword 🗡", 0),
        };
        clearConsole();

        printHeading("Vous avez rencontré un mystérieux étranger. \nIl vous propose d'acheter quelque chose:");
        String[] res = new String[shop.length+1];
        for (int i = 0; i < shop.length; i++) {
            shop[i].setValue((int) (Math.random()*(10+shop[i].quantity*3)+10+shop[i].quantity));
            System.out.println("-" +shop[i].name+" :"+shop[i].value+" d'or 💰 ");
            res[i] = i+1 +" - "+ shop[i].name;
        }
        
        printSeparator(20);
        res[res.length-1] = res.length+" - Non merci";
        int input = answer("Que voulez vous achetez ?",res);
        if (input == res.length) {
            printHeading("Vous n'achetez rien");
            anythingToContinue();
        } else {
            int price = shop[input-1].value;
            
            if (player.gold >= price) {
                printHeading("Vous avez acheter "+ shop[input-1].name +" pour "+price+ " d'or 💰 ");
                Item itemCurrent = player.getItem(shop[input-1].name);

                if (itemCurrent.quantity == 0) {
                    itemCurrent.quantity = 1;
                    player.items.add(itemCurrent);
                } else {
                    itemCurrent.quantity++;
                }
                player.gold -= price;
                
            }else {
                printHeading("Vous n'avez pas asser d'or pour acheter ça");
            }
        }
        anythingToContinue();
    }
    
    public static int answer(String question, String[] responses) {
        System.out.println(question);
        for (int i = 0; i < responses.length; i++) {
            System.out.println(responses[i]);
        }
        int input = readInt("-> ", responses.length);
        return input;
    }

    public static void checkAct() {
        if (player.xp >= 10 && place == 0) {
            place =1;

            Story.printFirstActOutro();
            // lvl up
            player.chooseTrait();
            Story.printSecondActIntro();

        } else if (player.xp >= 50 && place == 1) {
            place =2;

            Story.printSecondActOutro();
            // lvl up
            player.chooseTrait();

            Story.printThirdActIntro();

            player.hp = player.maxHp;

        } else if (player.xp >= 100 && place == 2) {
            place =3;
            player.chooseTrait();
            player.hp = player.maxHp;
            finalBattle();

        }
    }

    public static void continueJourney() {
    checkAct();
    if (place != 3) {
        initPlaces().get(place).randomEncounter(player);
    }
}
}