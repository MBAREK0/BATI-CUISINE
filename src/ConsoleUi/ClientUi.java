package ConsoleUi;

public class ClientUi {

    public void Ui() {
        // ANSI escape code for blue text
        String blue = "\033[0;34m";
        String reset = "\033[0m";

        System.out.println(blue + "===========================================+========================================================================================" +reset);
        System.out.println(blue + "|" +reset+"  1. Create Client   |  2. Update Client   |  3. Delete Client   |  4. Show Client Info  |  5. List Of Client Project   |  0. <--  "+blue+ "|" +reset);
        System.out.println(blue + "===========================================+========================================================================================" +reset);
    }


}
