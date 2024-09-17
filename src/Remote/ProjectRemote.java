package Remote;

import ConsoleUi.MainUi;
import ConsoleUi.ProjectUi;
import Entity.Client;
import Entity.Project;
import Repository.implementation.ClientRepositoryImpl;
import Repository.implementation.ProjectRepositoryImpl;
import Service.ProjectService;

import java.util.Optional;
import java.util.Scanner;

public class ProjectRemote {
    private Scanner scanner = new Scanner(System.in);
    private ProjectUi projectUi = new ProjectUi();
    private MainUi mainUi = new MainUi();
    private ProjectRepositoryImpl projectRepository = new ProjectRepositoryImpl();
    private ClientRepositoryImpl clientRepository = new ClientRepositoryImpl();
    private ProjectService projectService = new ProjectService();

    public void main() {

        Boolean isRunning = true;

        while (isRunning) {

            projectUi.Ui();

            mainUi.printPrompt("projects");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    createProject();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }

    public void createProject() {
        System.out.print("Enter the client name: ");
        String clientName = scanner.nextLine();
        Optional<Client> clientOptional = clientRepository.findByName(clientName);
        int clientId ;
        if (clientOptional.isPresent()) {
            System.out.println("Client found");
            System.out.println(clientOptional.get());
             clientId = clientOptional.get().getClient_id();
        } else {
            System.err.println("Client not found");
            return;
        }
        System.out.print("Enter the project name: ");
        String projectName = scanner.nextLine();
        System.out.print("Enter the profit margin (%): ");
        double profitMargin = scanner.nextDouble();
        System.out.print("Enter the surface area of the project (m^2): ");
        double surfaceArea = scanner.nextDouble();
        scanner.nextLine();
        String projectStatus = "InProgress";
        double totalCost = 0.0;

        Project project = new Project(projectName, profitMargin, totalCost, projectStatus, surfaceArea, clientId);
        project =   projectRepository.save(project).get();

        System.out.print("Do you want to add a component to the project? (y/n): ");
        String choice = scanner.nextLine();
        while (!choice.equals("y") && !choice.equals("n")) {
            System.err.println("\033[0;31mInvalid choice\033[0m");
            System.out.print("Do you want to add a component to the project? (y/n): ");
            choice = scanner.nextLine();
        }
        if(choice.equals("y")) {
            MaterialRemote materialRemote = new MaterialRemote();
            materialRemote.main(project.getProject_id());
            LaborRemote laborRemote = new LaborRemote();
            laborRemote.main(project.getProject_id());

            System.out.print("Would you like to apply a profit margin to the project? (y/n): ");
            String  choice1 = scanner.nextLine();
            while (!choice1.equals("y") && !choice1.equals("n")) {
                System.err.println("\033[0;31mInvalid choice\033[0m");
                System.out.print("Would you like to apply a profit margin to the project? (y/n): ");
                choice1 = scanner.nextLine();
            }
            if (choice1.equals("n")) {
                profitMargin = 0.0;
            }

            Double T =  projectService.calculateCost(project.getProject_id(), profitMargin);
            System.out.println();
            System.out.print("Total cost of the project: " );
            System.out.println("\033[0;32m"+ T +"\033[0m");
            System.out.println();



        }

        System.out.println("\033[0;32mProject added successfully\033[0m");
        System.out.println();
        System.out.print("WOuld you like to Create A Quotation? (y/n): ");
        String choice2 = scanner.nextLine();
        while (!choice2.equals("y") && !choice2.equals("n")) {
            System.err.println("\033[0;31mInvalid choice\033[0m");
            System.out.print("WOuld you like to Create A Quotation? (y/n): ");
            choice2 = scanner.nextLine();
        }
        if(choice2.equals("y")){
            QuoteRemote quotationRemote = new QuoteRemote();
            quotationRemote.createQuote(project.getProject_id());
        }


    }
}
