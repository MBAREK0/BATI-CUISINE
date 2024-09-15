package Remote;

import ConsoleUi.MainUi;
import ConsoleUi.ProjectUi;
import Repository.implementation.ProjectRepositoryImpl;

import java.util.Scanner;

public class ProjectRemote {
    private Scanner scanner = new Scanner(System.in);
    private ProjectUi projectUi = new ProjectUi();
    private MainUi mainUi = new MainUi();
    private ProjectRepositoryImpl projectRepository = new ProjectRepositoryImpl();

    public void main() {

        Boolean isRunning = true;

        while (isRunning) {

            projectUi.Ui();

            mainUi.printPrompt("projects");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 0:
                    return;

                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }



    }
}
