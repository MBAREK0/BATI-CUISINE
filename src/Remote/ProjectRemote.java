package Remote;

import ConsoleUi.MainUi;
import ConsoleUi.ProjectUi;
import Entity.Client;
import Entity.Project;
import Entity.Quote;
import Repository.implementation.ClientRepositoryImpl;
import Repository.implementation.ProjectRepositoryImpl;
import Service.ProjectService;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ProjectRemote {
    private Scanner scanner = new Scanner(System.in);
    private ProjectUi projectUi = new ProjectUi();
    private MainUi mainUi = new MainUi();
    private ProjectRepositoryImpl projectRepository = new ProjectRepositoryImpl();
    private ProjectService projectService = new ProjectService();
    private ComponentRemote componentRemote = new ComponentRemote();

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
                case 2:
                    updateProject();
                    break;
                case 3:
                    deleteProject();
                    break;
                case 4:
                    ShowProject();
                    break;
                case 5:
                    allProjects();
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
        Optional<Client> clientOptional = projectService.findClientByName(clientName);
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
        Optional<Project> OpProject = projectService.save(project);
        if (!OpProject.isPresent()) {
            System.err.println("Project not saved");
            return;
        }

        project = OpProject.get();

        String choice;
        do {
            System.out.print("Do you want to add a component to the project? (y/n): ");
            choice = scanner.nextLine();
        } while (!choice.equals("y") && !choice.equals("n"));


        if(choice.equals("y")) {
            MaterialRemote materialRemote = new MaterialRemote();
            materialRemote.main(project.getProject_id());
            LaborRemote laborRemote = new LaborRemote();
            laborRemote.main(project.getProject_id());

            do {
                System.out.print("Would you like to apply a profit margin to the project? (y/n): ");
                choice = scanner.nextLine();
            } while (!choice.equals("y") && !choice.equals("n"));

            if (choice.equals("n")) {
                profitMargin = 0.0;
            }

            Double T =  projectService.calculateCost(project.getProject_id(), profitMargin);
            System.out.println();
            System.out.print("Total cost of the project: " );
            System.out.println("\033[0;32m"+ String.format("%.2f", T) + " Dh" +"\033[0m");
            System.out.println();


        }

        System.out.println("\033[0;32mProject added successfully\033[0m");
        System.out.println();

        do {
            System.out.print("WOuld you like to Create A Quotation? (y/n): ");
            choice = scanner.nextLine();
        } while (!choice.equals("y") && !choice.equals("n"));

        if(choice.equals("y")){
            QuoteRemote quotationRemote = new QuoteRemote();
            quotationRemote.createQuote(project.getProject_id());
        }


    }

    public void updateProject(){
        System.out.print("Enter the project name: ");
        String projectName = scanner.nextLine();
        System.out.print("Enter the Client name: ");
        String clientName = scanner.nextLine();
        Optional<Project> OpProject = projectService.findByNameAndClient(projectName, clientName);

        if (!OpProject.isPresent()) {
            System.err.println("Project not found");
            return;
        }

        Project project = OpProject.get();

        String choice;
        do {
            System.out.print("Would you like to update the project information? (y/n): ");
            choice = scanner.nextLine();
        } while (!choice.equals("y") && !choice.equals("n")) ;

        if (choice.equals("y")) {
            updateProjectInfo(project);
        }

        do {
            System.out.print("Would you like to mange component for this project? (y/n): ");
            choice = scanner.nextLine();
        } while (!choice.equals("y") && !choice.equals("n")) ;

        if (choice.equals("y")) {
            updateProjectComponents(project);
            Double T  =  projectService.calculateCost(project.getProject_id(), project.getProfit_margin());
            project.setTotal_cost(T);
            projectService.update(project);

            Optional<Quote> quote = projectService.findQuoteByProjectId(project.getProject_id());
            if (quote.isPresent()) {
                Quote q = quote.get();
                q.setEstimatedAmount(T);
                projectService.updateQuote(q);
            }
        }



        System.out.println("\033[0;32mProject updated successfully\033[0m");

    }
    public void updateProjectInfo(Project project){

        System.out.print("Would you like to update the project name? (y/n): ");
        String choice = scanner.nextLine();
        while (!choice.equals("y") && !choice.equals("n")) {
            System.err.println("\033[0;31mInvalid choice\033[0m");
            System.out.print("Would you like to update the project name? (y/n): ");
            choice = scanner.nextLine();
        }

        if (choice.equals("y")) {
            System.out.print("Enter the new project name: ");
             String projectName = scanner.nextLine();
            project.setProject_name(projectName);
        }

        System.out.print("Would you like to update the profit margin? (y/n): ");
        choice = scanner.nextLine();
        while (!choice.equals("y") && !choice.equals("n")) {
            System.err.println("\033[0;31mInvalid choice\033[0m");
            System.out.print("Would you like to update the profit margin? (y/n): ");
            choice = scanner.nextLine();
        }

        if (choice.equals("y")) {
            System.out.print("Enter the new profit margin: ");
            double profitMargin = scanner.nextDouble();
            project.setProfit_margin(profitMargin);
        }

        System.out.print("Would you like to update the surface area? (y/n): ");
        choice = scanner.nextLine();
        while (!choice.equals("y") && !choice.equals("n")) {
            System.err.println("\033[0;31mInvalid choice\033[0m");
            System.out.print("Would you like to update the surface area? (y/n): ");
            choice = scanner.nextLine();
        }

        if (choice.equals("y")) {
            System.out.print("Enter the new surface area: ");
            double surfaceArea = scanner.nextDouble();
            project.setSurface_area(surfaceArea);
        }

        System.out.print("Would you like to update the project status? (y/n): ");
        choice = scanner.nextLine();
        while (!choice.equals("y") && !choice.equals("n")) {
            System.err.println("\033[0;31mInvalid choice\033[0m");
            System.out.print("Would you like to update the project status? (y/n): ");
            choice = scanner.nextLine();
        }

        if (choice.equals("y")) {
            System.out.print("Enter the new project status: ");
            String projectStatus = scanner.nextLine();
            project.setProject_status(projectStatus);
        }


        projectRepository.update(project);
        System.out.println("\033[0;32mProject updated successfully\033[0m");
        System.out.println();
    }

    public void updateProjectComponents(Project project){

        String yellow = "\033[0;33m";
        String reset = "\033[0m";

        // ====View=========================================================
        System.out.println();
        System.out.println(yellow + "==== View Component ====================================" + reset);
        System.out.println();
        String choice;

        do{
            System.out.print("would you like to view the components associated with the project? (y/n): ");
            choice = scanner.nextLine();
        } while (!choice.equals("y") && !choice.equals("n"));

        if (choice.equals("y")) {
            componentRemote.viewComponent(project);
        }


        // =====Add=========================================================
        System.out.println();
        System.out.println(yellow+"==== Add Component ===================================="+reset);
        System.out.println();

        do {
            System.out.print("Would you like to add a component to the project? (y/n): ");
            choice = scanner.nextLine();
        } while (!choice.equals("y") && !choice.equals("n")) ;

        if (choice.equals("y")) {
            MaterialRemote materialRemote = new MaterialRemote();
            materialRemote.main(project.getProject_id());
            LaborRemote laborRemote = new LaborRemote();
            laborRemote.main(project.getProject_id());
        }

        // ====Delete==========================================================
        System.out.println();
        System.out.println(yellow+"==== Delete Component ================================="+reset);
        System.out.println();

        do {
            System.out.print("Would you like to delete a component from the project? (y/n): ");
            choice = scanner.nextLine();
        } while (!choice.equals("y") && !choice.equals("n")) ;

        if (choice.equals("y")) {
            componentRemote.deleteComponent(project);
        }

        // =====Update=========================================================
        System.out.println();
        System.out.println(yellow+"==== Update Component ================================="+reset);
        System.out.println();

        do {
            System.out.print("Would you like to update a component from the project? (y/n): ");
            choice = scanner.nextLine();
        } while (!choice.equals("y") && !choice.equals("n"));

        if (choice.equals("y")) {
            componentRemote.updateComponent(project);
        }

    }

    public void deleteProject(){
        System.out.print("Enter the project name: ");
        String projectName = scanner.nextLine();
        System.out.print("Enter the Client name: ");
        String clientName = scanner.nextLine();
        Optional<Project> OpProject = projectService.findByNameAndClient(projectName, clientName);

        if (!OpProject.isPresent()) {
            System.err.println("Project not found");
            return;
        }

        Project project = OpProject.get();

        String choice;
        do {
            System.out.print("\033[0;31mWould you like to delete the project? (y/n): \033[0m");
            choice = scanner.nextLine();
        } while (!choice.equals("y") && !choice.equals("n"));

        if (choice.equals("y")) {
            projectRepository.delete(project.getProject_id());
            System.out.println("\033[0;32mProject deleted successfully\033[0m");
        }
    }

    public void ShowProject(){
        System.out.print("Enter the project name: ");
        String projectName = scanner.nextLine();
        System.out.print("Enter the Client name: ");
        String clientName = scanner.nextLine();
        Optional<Project> project = projectService.findByNameAndClient(projectName, clientName);

        if (!project.isPresent()) {
            System.err.println("Project not found");
            return;
        }

        System.out.println(project.get());

        System.out.println("=== Project components ==============================");
        System.out.println();
        componentRemote.viewComponent(project.get());

    }


    // Method to print the table header with an additional column for client name
    public String printTableHeader() {
        // ANSI escape code for yellow text
        String yellow = "\033[0;33m";
        String reset = "\033[0m";

        // Calculate the maximum widths for each column dynamically
        int projectNameWidth = Math.max("Project Name".length(), 15) * 2; // Arbitrary widths for headers
        int profitMarginWidth = Math.max("Profit Margin".length(), 15);
        int totalCostWidth = Math.max("1681393.4279999998".length(), 15) ;
        int projectStatusWidth = Math.max("Project Status".length(), 15);
        int surfaceAreaWidth = Math.max("Surface Area".length(), 15);
        int clientNameWidth = Math.max("Client Name".length(), 15) ; // New width for client name

        // Build the table header
        StringBuilder sb = new StringBuilder();
        sb.append(yellow); // Start with yellow color

        // Top border
        sb.append("+").append("-".repeat(projectNameWidth + 2))
                .append("+").append("-".repeat(profitMarginWidth + 2))
                .append("+").append("-".repeat(totalCostWidth + 2))
                .append("+").append("-".repeat(projectStatusWidth + 2))
                .append("+").append("-".repeat(surfaceAreaWidth + 2))
                .append("+").append("-".repeat(clientNameWidth + 2)) // New border
                .append("+\n");

        // Header row
        sb.append(String.format("| %-"+projectNameWidth+"s | %-"+profitMarginWidth+"s | %-"+totalCostWidth+"s | %-"+projectStatusWidth+"s | %-"+surfaceAreaWidth+"s | %-"+clientNameWidth+"s |\n",
                "Project Name", "Profit Margin %", "Total Cost (Dh)", "Project Status", "Surface (m^2)", "Client Name"));

        // Middle border
        sb.append("+").append("-".repeat(projectNameWidth + 2))
                .append("+").append("-".repeat(profitMarginWidth + 2))
                .append("+").append("-".repeat(totalCostWidth + 2))
                .append("+").append("-".repeat(projectStatusWidth + 2))
                .append("+").append("-".repeat(surfaceAreaWidth + 2))
                .append("+").append("-".repeat(clientNameWidth + 2)) // New border
                .append("+\n");

        sb.append(reset); // Reset the color
        return sb.toString();
    }

    // Method to print a single project row horizontally with client name
    public String printProjectRow(String projectName, double profitMargin, double totalCost, String projectStatus, double surfaceArea, String clientName) {
        // ANSI escape code for yellow text
        String yellow = "\033[0;33m";
        String reset = "\033[0m";

        // Calculate the maximum widths for each column dynamically
        int projectNameWidth = Math.max("Project Name".length(), 15) * 2;
        int profitMarginWidth = Math.max("Profit Margin".length(), 15);
        int totalCostWidth = Math.max("1681393.4279999998".length(), 15) ;
        int projectStatusWidth = Math.max("Project Status".length(), 15);
        int surfaceAreaWidth = Math.max("Surface Area".length(), 15);
        int clientNameWidth = Math.max("Client Name".length(), 15) ; // New width for client name

        // Build the table row for the project
        StringBuilder sb = new StringBuilder();
        sb.append(yellow); // Start with yellow color

        // Data row
        sb.append(String.format("| %-"+projectNameWidth+"s | %-"+profitMarginWidth+"s | %-"+totalCostWidth+"s | %-"+projectStatusWidth+"s | %-"+surfaceAreaWidth+"s | %-"+clientNameWidth+"s |\n",
                projectName,
                profitMargin,
                totalCost,
                projectStatus,
                surfaceArea,
                clientName)); // Including client name

        sb.append(reset);
        return sb.toString();
    }

    // Method to print the bottom border of the table
    public String printTableFooter() {
        // ANSI escape code for yellow text
        String yellow = "\033[0;33m";
        String reset = "\033[0m";

        // Calculate the maximum widths for each column dynamically
        int projectNameWidth = Math.max("Project Name".length(), 15) * 2;
        int profitMarginWidth = Math.max("Profit Margin".length(), 15);
        int totalCostWidth = Math.max("1681393.4279999998".length(), 15) ;
        int projectStatusWidth = Math.max("Project Status".length(), 15);
        int surfaceAreaWidth = Math.max("Surface Area".length(), 15);
        int clientNameWidth = Math.max("Client Name".length(), 15) ; // New width for client name

        // Bottom border
        StringBuilder sb = new StringBuilder();
        sb.append(yellow); // Start with yellow color
        sb.append("+").append("-".repeat(projectNameWidth + 2))
                .append("+").append("-".repeat(profitMarginWidth + 2))
                .append("+").append("-".repeat(totalCostWidth + 2))
                .append("+").append("-".repeat(projectStatusWidth + 2))
                .append("+").append("-".repeat(surfaceAreaWidth + 2))
                .append("+").append("-".repeat(clientNameWidth + 2)) // New border
                .append("+\n");

        sb.append(reset); // Reset the color
        return sb.toString();
    }

    // Updated allProjects method to include client names
    public void allProjects() {

        System.out.print("Choose the status of the project you want to view ('InProgress', 'Completed', 'Cancelled'): ");
        String status = scanner.nextLine();
        status = status.toLowerCase();
        while (!status.equals("inprogress") && !status.equals("completed") && !status.equals("cancelled")) {
            System.err.println("\033[0;31mInvalid choice\033[0m");
            System.out.print("Choose the status of the project you want to view ('In Progress', 'Completed', 'Cancelled'): ");
            status = scanner.nextLine();
            status = status.toLowerCase(); // Ensure status remains lowercase
        }

        List<Project> projects = projectService.findByStatus(status);
        if (projects.isEmpty()) {
            System.err.println("No project found");
            return;
        }

        List<Client> clients = projectService.findClientByProjectStatus(status);

        HashMap<Integer, String> clientMap = new HashMap<>();
        for (Client client : clients) {
            clientMap.put(client.getClient_id(), client.getName());
        }

        System.out.print(printTableHeader());
        projects.forEach(project -> {
            String clientName = clientMap.getOrDefault(project.getClient_id(), "Unknown"); // Handle missing client names
            System.out.print(printProjectRow(project.getProject_name(), project.getProfit_margin(), project.getTotal_cost(), project.getProject_status(), project.getSurface_area(), clientName));
        });
        System.out.print(printTableFooter());
    }

}

