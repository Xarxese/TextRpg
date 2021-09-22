package textrpg;

public class Story {
    public static void printIntro() {
        GameLogic.clearConsole();
        GameLogic.printSeparator(30);
        System.out.println("HISTOIRE");
        GameLogic.printSeparator(30);
        System.out.println("HISTOIRE A TROUVER");
        GameLogic.anythingToContinue();
    }
    public static void printFirstActIntro() {
        GameLogic.clearConsole();
        GameLogic.printSeparator(30);
        System.out.println("ACT I - INTRO");
        GameLogic.printSeparator(30);
        System.out.println("HISTOIRE A TROUVER");
        GameLogic.anythingToContinue();
    }
    public static void printFirstActOutro() {
        GameLogic.clearConsole();
        GameLogic.printSeparator(30);
        System.out.println("ACT I - OUTRO");
        GameLogic.printSeparator(30);
        System.out.println("HISTOIRE A TROUVER");
        GameLogic.anythingToContinue();
    }
    public static void printSecondActIntro() {
        GameLogic.clearConsole();
        GameLogic.printSeparator(30);
        System.out.println("ACT II - INTRO");
        GameLogic.printSeparator(30);
        System.out.println("HISTOIRE A TROUVER");
        GameLogic.anythingToContinue();
    }
    public static void printSecondActOutro() {
        GameLogic.clearConsole();
        GameLogic.printSeparator(30);
        System.out.println("ACT II - OUTRO");
        GameLogic.printSeparator(30);
        System.out.println("HISTOIRE A TROUVER");
        GameLogic.anythingToContinue();
    }
    public static void printThirdActIntro() {
        GameLogic.clearConsole();
        GameLogic.printSeparator(30);
        System.out.println("ACT III - INTRO");
        GameLogic.printSeparator(30);
        System.out.println("HISTOIRE A TROUVER");
        GameLogic.anythingToContinue();
    }
    public static void printThirdActOutro() {
        GameLogic.clearConsole();
        GameLogic.printSeparator(30);
        System.out.println("ACT III - OUTRO");
        GameLogic.printSeparator(30);
        System.out.println("HISTOIRE A TROUVER");
        GameLogic.anythingToContinue();
    }
    public static void printFourActIntro() {
        GameLogic.clearConsole();
        GameLogic.printSeparator(30);
        System.out.println("ACT IV - INTRO");
        GameLogic.printSeparator(30);
        System.out.println("HISTOIRE A TROUVER");
        GameLogic.anythingToContinue();
    }
    public static void printEnd(Player player) {
        GameLogic.clearConsole();
        GameLogic.printSeparator(30);
        System.out.println("Félicitation, "+player.name+"| Vous avez tué l'empereur diabolique et sauver le monde!");
        GameLogic.printSeparator(30);
        System.out.println("Ce jeu a été fait pendant un cours de java");
        GameLogic.anythingToContinue();
    }
}
