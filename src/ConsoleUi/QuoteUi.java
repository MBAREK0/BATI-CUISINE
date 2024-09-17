package ConsoleUi;

public class QuoteUi {
    public void Ui() {
        String blue = "\033[0;34m";
        String reset = "\033[0m";

        System.out.println();
        System.out.println(blue + "==========================================================================================="+ reset);
        System.out.println(blue +"|"+ reset + "  1. Create Quote  |  2. Update Quote  |  3. Delete Quote  |  4. Show Quote  |   0. <--  "+blue + "|" + reset);
        System.out.println(blue + "==========================================================================================="+ reset);
        System.out.println();

    }
}
