package ConsoleUi;

public class ComponentUi {
    public void Ui() {
        // ANSI escape code for blue text
        String blue = "\033[0;34m";
        String reset = "\033[0m";

        System.out.println(blue + "==================================================================" +reset);
        System.out.println(blue + "|" +reset+"  1. Create Material   |  2. Create Labor   |  0. <--  "+blue+ "|" +reset);
        System.out.println(blue + "==================================================================" +reset);
    }
}
