package Remote;

import ConsoleUi.ClientUi;
import ConsoleUi.MainUi;
import Entity.Client;
import Entity.Project;
import Repository.implementation.ClientRepositoryImpl;
import Service.ClientService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static java.lang.Boolean.parseBoolean;

public class ClientRemote {

    private final Scanner scanner = new Scanner(System.in);
    private final ClientUi clientUi = new ClientUi();
    private final MainUi mainUi = new MainUi();
    private ClientRepositoryImpl clientRepository = new ClientRepositoryImpl();
    private ClientService clientService = new ClientService();

    public void main() {
        Boolean isRunning = true;

        while (isRunning) {

            clientUi.Ui();

            mainUi.printPrompt("clients");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createClient();
                    break;
                case 2:
                    updateClient();
                    break;
                case 3:
                    deleteClient();
                    break;
                case 4:
                    showClient();
                    break;
                case 5:
                    clientProjects();
                    break;

                case 0:
                    return;

                default:
                    System.err.println("\033[0;31mInvalid choice\033[0m");
                    break;


            }

        }
    }


    public void createClient() {
        System.out.print("Enter client name: ");
        String name = scanner.nextLine();
        System.out.print("Enter client address: ");
        String address = scanner.nextLine();
        System.out.print("Enter client phone: ");
        String phone = scanner.nextLine();

        String isProfessional;
       do {
           System.out.print("Is client professional? (y/n): ");
           isProfessional = scanner.nextLine();
       }while (!isProfessional.equals("y") && !isProfessional.equals("n")) ;

        isProfessional = isProfessional.equals("y") ? "true" : "false";
        String choice;
       do {
           System.out.print("Do you want to add discount? (y/n): ");
           choice = scanner.nextLine();
       }while (!choice.equals("y") && !choice.equals("n"));

        Double discount = 0.0;
        if (choice.equals("y")) {
            System.out.print("Enter the discount percentage: ");
            discount = scanner.nextDouble();
            scanner.nextLine();
        }

        Client client = new Client(name, address, phone, parseBoolean(isProfessional),discount);
        if(clientService.save(client).isPresent())
            System.out.println("\033[0;32mClient created successfully\033[0m");
        else
            System.err.println("\033[0;31mClient creation failed\033[0m");
    }

    public void updateClient(){
        System.out.print("Enter client name: ");
        String name_str= scanner.nextLine();


        Optional<Client> opClient = clientService.findByName(name_str);

        Client client = null;

        if (opClient.isPresent()) {
            client = opClient.get();
            System.out.println(client);
        } else {
            System.err.println("\033[0;31mClient not found.\033[0m");
            return;
        }

        int id = client.getClient_id();

        String choice;

        // Update client name -------------------------
       do {
           System.out.print("Do you want to update client name? (y/n): ");
           choice = scanner.nextLine();
       } while (!choice.equals("y") && !choice.equals("n"));

        String name ;
        if (choice.equals("y")){
            System.out.print("Enter New client name: ");
             name = scanner.nextLine();
        }else {
            name = client.getName();
        }

        // update client address -------------------------
        do {
            System.out.print("Do you want to update client address? (y/n): ");
            choice = scanner.nextLine();
        }
        while (!choice.equals("y") && !choice.equals("n")) ;

        String address ;
        if (choice.equals("y")){
            System.out.print("Enter New client address: ");
            address = scanner.nextLine();
        }else {
            address = client.getAddress();
        }

        // update client phone -------------------------
       do {
           System.out.print("Do you want to update client phone? (y/n): ");
           choice = scanner.nextLine();
       }
        while (!choice.equals("y") && !choice.equals("n"));

        String phone ;
        if (choice.equals("y")){
            System.out.print("Enter New client phone: ");
            phone = scanner.nextLine();
        }else {
            phone = client.getPhone();
        }

        // update client professional status -------------------------
        do {
            System.out.print("Do you want to update client professional status? (y/n): ");
            choice = scanner.nextLine();
        }  while (!choice.equals("y") && !choice.equals("n")) ;

        String isProfessional ;
        if (choice.equals("y")){
            do {
                System.out.print("Is client professional? (y/n): ");
                isProfessional = scanner.nextLine();
            }
            while (!isProfessional.equals("y") && !isProfessional.equals("n"));

            isProfessional = isProfessional.equals("y") ? "true" : "false";

        }else {
            isProfessional = String.valueOf(client.isIs_professional());
        }

        // update client discount -------------------------
        do {
            System.out.print("Do you want to update client discount? (y/n): ");
            choice = scanner.nextLine();
        } while (!choice.equals("y") && !choice.equals("n"));

        Double discount;

        if (choice.equals("y")){
            System.out.print("Enter the New discount percentage: ");
            discount = scanner.nextDouble();
        }else {
            discount = client.getDiscount_percentage();
        }


        // update client in database
        Client c = new Client(name, address, phone, parseBoolean(isProfessional),discount);
        c.setClient_id(client.getClient_id());
        if (clientService.update(c).isPresent()){
            System.out.println("\033[0;32mClient updated successfully\033[0m");
        }else
            System.err.println("\033[0;31mFailed to update client \033[0m");

    }

    public void deleteClient(){
        System.out.print("Enter client name you want to delete: ");
        String name = scanner.nextLine();
        Optional<Client> opClient = clientService.findByName(name);

        Client client = null;

        if (opClient.isPresent()) {
            client = opClient.get();
        } else {
            System.err.println("\033[0;31mClient not found.\033[0m");
            return;
        }
        clientService.delete(client.getClient_id());
        System.out.println("\033[0;32mClient deleted successfully\033[0m");

    }
    public void showClient(){
        System.out.print("Enter client name you want to show: ");
        String name = scanner.nextLine();
        Optional<Client> opClient = clientService.findByName(name);

        Client client = null;

        if (opClient.isPresent()) {
            client = opClient.get();
            System.out.println(client);
        } else {
            System.err.println("\033[0;31mClient not found.\033[0m");
        }
    }

    public void clientProjects(){
        System.out.print("Enter client name you want to show his projects: ");
        String name = scanner.nextLine();
        Optional<Client> opClient = clientService.findByName(name);

        Client client = null;
        if (opClient.isPresent()) {
            client = opClient.get();
        } else {
            System.err.println("\033[0;31mClient not found.\033[0m");
            return;
        }

        List<Project>  projects =   clientService.findProjects(client.getClient_id());
        if (projects.isEmpty()) {
            System.out.println("No projects found for client " + client.getName());
        } else {
            System.out.println("Projects for client " + client.getName() + ":");

            System.out.print(printTableHeader());
            projects.forEach(project -> System.out.print(printProjectRow(project)));
            System.out.print(printTableFooter());

        }

    }

    // Method to print the table header horizontally
    public String printTableHeader() {
        // ANSI escape code for yellow text
        String yellow = "\033[0;33m";
        String reset = "\033[0m";

        // Calculate the maximum widths for each column dynamically
        int projectNameWidth = Math.max("Project Name".length(), 15)*2; // Arbitrary widths for headers
        int profitMarginWidth = Math.max("Profit Margin".length(), 15);
        int totalCostWidth = Math.max("Total Cost".length(), 15)*2;
        int projectStatusWidth = Math.max("Project Status".length(), 15);
        int surfaceAreaWidth = Math.max("Surface Area".length(), 15);

        // Build the table header
        StringBuilder sb = new StringBuilder();
        sb.append(yellow); // Start with yellow color

        // Top border
        sb.append("+").append("-".repeat(projectNameWidth + 2))
                .append("+").append("-".repeat(profitMarginWidth + 2))
                .append("+").append("-".repeat(totalCostWidth + 2))
                .append("+").append("-".repeat(projectStatusWidth + 2))
                .append("+").append("-".repeat(surfaceAreaWidth + 2))
                .append("+\n");

        // Header row
        sb.append(String.format("| %-"+projectNameWidth+"s | %-"+profitMarginWidth+"s | %-"+totalCostWidth+"s | %-"+projectStatusWidth+"s | %-"+surfaceAreaWidth+"s |\n",
                "Project Name", "Profit Margin", "Total Cost", "Project Status", "Surface Area"));

        // Middle border
        sb.append("+").append("-".repeat(projectNameWidth + 2))
                .append("+").append("-".repeat(profitMarginWidth + 2))
                .append("+").append("-".repeat(totalCostWidth + 2))
                .append("+").append("-".repeat(projectStatusWidth + 2))
                .append("+").append("-".repeat(surfaceAreaWidth + 2))
                .append("+\n");

        sb.append(reset); // Reset the color
        return sb.toString();
    }

    // Method to print a single project row horizontally
    public String printProjectRow(Project project) {
        // ANSI escape code for yellow text
        String yellow = "\033[0;33m";
        String reset = "\033[0m";

        // Calculate the maximum widths for each column dynamically
        int projectNameWidth = Math.max("Project Name".length(), 15)*2;
        int profitMarginWidth = Math.max("Profit Margin".length(), 15);
        int totalCostWidth = Math.max("Total Cost".length(), 15)*2;
        int projectStatusWidth = Math.max("Project Status".length(), 15);
        int surfaceAreaWidth = Math.max("Surface Area".length(), 15);

        // Build the table row for the project
        StringBuilder sb = new StringBuilder();
        sb.append(yellow); // Start with yellow color

        // Data row
        sb.append(String.format("| %-"+projectNameWidth+"s | %-"+profitMarginWidth+"s | %-"+totalCostWidth+"s | %-"+projectStatusWidth+"s | %-"+surfaceAreaWidth+"s |\n",
                project.getProject_name(),
                project.getProfit_margin(),
                project.getTotal_cost(),
                project.getProject_status(),
                project.getSurface_area()));

        sb.append(reset);
        return sb.toString();
    }

    // Method to print the bottom border
    public String printTableFooter() {
        // ANSI escape code for yellow text
        String yellow = "\033[0;33m";
        String reset = "\033[0m";

        // Calculate the maximum widths for each column dynamically
        int projectNameWidth = Math.max("Project Name".length(), 15)*2;
        int profitMarginWidth = Math.max("Profit Margin".length(), 15);
        int totalCostWidth = Math.max("Total Cost".length(), 15)*2;
        int projectStatusWidth = Math.max("Project Status".length(), 15);
        int surfaceAreaWidth = Math.max("Surface Area".length(), 15);

        // Bottom border
        StringBuilder sb = new StringBuilder();
        sb.append(yellow); // Start with yellow color
        sb.append("+").append("-".repeat(projectNameWidth + 2))
                .append("+").append("-".repeat(profitMarginWidth + 2))
                .append("+").append("-".repeat(totalCostWidth + 2))
                .append("+").append("-".repeat(projectStatusWidth + 2))
                .append("+").append("-".repeat(surfaceAreaWidth + 2))
                .append("+\n");

        sb.append(reset); // Reset the color
        return sb.toString();
    }


}
