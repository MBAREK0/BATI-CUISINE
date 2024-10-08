package View;

import ConsoleUi.MainUi;

import java.util.Scanner;

public class MainView {

    private Scanner scanner = new Scanner(System.in);
    private MainUi mainUi = new MainUi();
    private ProjectView projectRemote = new ProjectView();
    private ClientView clientRemote = new ClientView();
    private QuoteView quoteRemote = new QuoteView();

    public void main() {

        mainUi.logo();
        Boolean isRunning = true;

        while (isRunning) {

            mainUi.Ui();

            mainUi.printPrompt("");
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
                    System.err.println("\033[0;31mInvalid choice\033[0m");
                    break;
            }

        }
    }
}
