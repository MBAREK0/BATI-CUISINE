package ConsoleUi;

public class MainUi {
    public void Ui() {
        // ANSI escape code for blue text
        String blue = "\033[0;34m";
        String reset = "\033[0m";

        System.out.println(blue + "===================================================================="+ reset);
        System.out.println(blue + "|" +reset+"   1. Projects  |   2. Clients    |   3. Quotes   |   0. Exit     "+blue+ "|" +reset);
        System.out.println(blue+"====================================================================" + reset);
    }
    public void logo() {
        // ANSI escape code for blue text
        String blue = "\033[0;34m";
        String reset = "\033[0m";

        System.out.println(blue + "================" +reset);
        System.out.println("  BatiCuisine  "+blue+ "|" +reset);
        System.out.println(blue + "================" +reset);

        System.out.println("\n\n");
    }
    public void printPrompt(String prompt) {
        // ANSI escape code for green text
        String green = "\033[0;32m";
        String reset = "\033[0m";
        String blue = "\033[0;34m";


        System.out.print("\n\n" + green + "i@baticuisine" + reset);
        System.out.print(":");
        if(prompt.isEmpty())      System.out.print(blue+"~" + reset);
        else  System.out.print(blue+"/" + prompt + reset);
        System.out.print("$ ");
    }

}
