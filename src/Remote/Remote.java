package Remote;

import ConsoleUi.ClientUi;
import ConsoleUi.MainUi;

import java.util.Scanner;

public class Remote {

    private Scanner scanner = new Scanner(System.in);
    private MainUi mainUi = new MainUi();
    private ProjectRemote projectRemote = new ProjectRemote();
    private ClientRemote clientRemote = new ClientRemote();
    private QuoteRemote quoteRemote = new QuoteRemote();

    public void main() {
        Boolean isRunning = true;

        while (isRunning) {

            mainUi.Ui();

            System.out.print("\n\ni@baticuisine:~$ ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    projectRemote.main();
                    break;
                case 2:
                    clientRemote.main();
                    break;
                case 3:
                    quoteRemote.main();
                    break;
                case 0:
                    System.out.println("\nExiting...");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }

        }
    }
}
