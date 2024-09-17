package Remote;

import ConsoleUi.ClientUi;
import ConsoleUi.MainUi;
import Entity.Client;
import Entity.Project;
import Repository.implementation.ClientRepositoryImpl;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static java.lang.Boolean.parseBoolean;

public class ClientRemote {

    private final Scanner scanner = new Scanner(System.in);
    private final ClientUi clientUi = new ClientUi();
    private final MainUi mainUi = new MainUi();
    private ClientRepositoryImpl clientRepository = new ClientRepositoryImpl();

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
        System.out.print("Is client professional? (y/n): ");
        String isProfessional = scanner.nextLine();
        while (!isProfessional.equals("y") && !isProfessional.equals("n")) {
            System.err.println("\033[0;31mInvalid choice\033[0m");
            System.out.print("Is client professional? (y/n): ");
             isProfessional = scanner.nextLine();
        }

        switch (isProfessional) {
            case "y":
                isProfessional =  "true";
                break;
            case "n":
                isProfessional = "false";
                break;
            default:
                System.err.println("\033[0;31mInvalid choice\033[0m");
                return;
        }
        System.out.print("Do you want to add discount? (y/n): ");
        String choice = scanner.nextLine();
        Double discount;
        while (!choice.equals("y") && !choice.equals("n")) {
            System.err.println("\033[0;31mInvalid choice\033[0m");
            System.out.print("Do you want to add discount? (y/n): ");
            choice = scanner.nextLine();
        }
        switch (choice) {
            case "y":
                System.out.print("Enter the discount percentage (%): ");
                 discount = scanner.nextDouble();
                break;
            case "n":
                discount = 0.0;
                break;
            default:
                System.err.println("\033[0;31mInvalid choice\033[0m");
                return;
        }


        Client client = new Client(name, address, phone, parseBoolean(isProfessional),discount);
        clientRepository.save(client);
        System.out.println("\033[0;32mClient created successfully\033[0m");


    }
    public void updateClient(){
        System.out.print("Enter client name: ");
        String name_str= scanner.nextLine();


        Optional<Client> opClient = clientRepository.findByName(name_str);

        Client client = null;

        if (opClient.isPresent()) {
            client = opClient.get();
            System.out.println(client);
        } else {
            System.err.println("\033[0;31mClient not found.\033[0m");

            return;
        }
        int id = client.getClient_id();

        // Update client name -------------------------
        System.out.print("Do you want to update client name? (y/n): ");
        String choice = scanner.nextLine();
        while (!choice.equals("y") && !choice.equals("n")) {
            System.err.println("\033[0;31mInvalid choice\033[0m");
            System.out.print("Do you want to update client name? (y/n): ");
            choice = scanner.nextLine();
        }
        String name ;
        if (choice.equals("y")){
            System.out.print("Enter New client name: ");
             name = scanner.nextLine();
        }else {
            name = client.getName();
        }

        // update client address -------------------------
        System.out.print("Do you want to update client address? (y/n): ");
        choice = scanner.nextLine();
        while (!choice.equals("y") && !choice.equals("n")) {
            System.err.println("\033[0;31mInvalid choice\033[0m");
            System.out.print("Do you want to update client address? (y/n): ");
            choice = scanner.nextLine();
        }
        String address ;
        if (choice.equals("y")){
            System.out.print("Enter New client address: ");
            address = scanner.nextLine();
        }else {
            address = client.getAddress();
        }

        // update client phone -------------------------
        System.out.print("Do you want to update client phone? (y/n): ");
        choice = scanner.nextLine();
        while (!choice.equals("y") && !choice.equals("n")) {
            System.err.println("\033[0;31mInvalid choice\033[0m");
            System.out.print("Do you want to update client phone? (y/n): ");
            choice = scanner.nextLine();
        }
        String phone ;
        if (choice.equals("y")){
            System.out.print("Enter New client phone: ");
            phone = scanner.nextLine();
        }else {
            phone = client.getPhone();
        }

        // update client professional status -------------------------
        System.out.print("Do you want to update client professional status? (y/n): ");
        choice = scanner.nextLine();
        while (!choice.equals("y") && !choice.equals("n")) {
            System.err.println("\033[0;31mInvalid choice\033[0m");
            System.out.print("Do you want to update client professional status? (y/n): ");
            choice = scanner.nextLine();
        }
        String isProfessional ;
        if (choice.equals("y")){
            System.out.print("Is client professional? (y/n): ");
            isProfessional = scanner.nextLine();
            while (!isProfessional.equals("y") && !isProfessional.equals("n")) {
                System.err.println("\033[0;31mInvalid choice\033[0m");
                System.out.print("Is client professional? (y/n): ");
                isProfessional = scanner.nextLine();
            }
            isProfessional = isProfessional.equals("y") ? "true" : "false";

        }else {
            isProfessional = String.valueOf(client.isIs_professional());
        }

        // update client discount -------------------------
        System.out.print("Do you want to update client discount? (y/n): ");
        choice = scanner.nextLine();
        Double discount;
        while (!choice.equals("y") && !choice.equals("n")) {
            System.err.println("\033[0;31mInvalid choice\033[0m");
            System.out.print("Do you want to update client discount? (y/n): ");
            choice = scanner.nextLine();
        }
        if (choice.equals("y")){
            System.out.print("Enter the New discount percentage: ");
            discount = scanner.nextDouble();
        }else {
            discount = client.getDiscount_percentage();
        }


        // update client in database
        Client c = new Client(name, address, phone, parseBoolean(isProfessional),discount);
        c.setClient_id(client.getClient_id());
        clientRepository.update(c);

        System.out.println("\033[0;32mClient updated successfully\033[0m");
    }

    public void deleteClient(){
        System.out.print("Enter client name you want to delete: ");
        String name = scanner.nextLine();
        Optional<Client> opClient = clientRepository.findByName(name);

        Client client = null;

        if (opClient.isPresent()) {
            client = opClient.get();
        } else {
            System.err.println("\033[0;31mClient not found.\033[0m");
            return;
        }
        clientRepository.delete(client.getClient_id());
        System.out.println("\033[0;32mClient deleted successfully\033[0m");

    }
    public void showClient(){
        System.out.print("Enter client name you want to show: ");
        String name = scanner.nextLine();
        Optional<Client> opClient = clientRepository.findByName(name);

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
        Optional<Client> opClient = clientRepository.findByName(name);

        Client client = null;
        if (opClient.isPresent()) {
            client = opClient.get();
        } else {
            System.err.println("\033[0;31mClient not found.\033[0m");
            return;
        }

        List<Project>  project =   clientRepository.findProjectsByClientId(client.getClient_id());
        if (project.isEmpty()) {
            System.out.println("No projects found for client " + client.getName());
        } else {
            System.out.println("Projects for client " + client.getName() + ":");
            project.forEach(System.out::println);

        }

    }
}
