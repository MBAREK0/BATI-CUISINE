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
        System.out.println("Enter the discount percentage");
        Double discount = scanner.nextDouble();

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

        System.out.print("Enter New client name: ");
        String name = scanner.nextLine();
        System.out.print("Enter New client address: ");
        String address = scanner.nextLine();
        System.out.print("Enter New client phone: ");
        String phone = scanner.nextLine();
        System.out.print("Is client professional? (y/n): ");
        String isProfessional = scanner.nextLine();

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
        System.out.println("Enter the discount percentage");
        Double discount = scanner.nextDouble();

        Client c = new Client(name, address, phone, parseBoolean(isProfessional),discount);
        c.setClient_id(client.getClient_id());
        clientRepository.update(c);

        System.out.println("\033[0;32mClient updated successfully\033[0m");
    }

    public void deleteClient(){
        System.out.print("Enter client name: ");
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
        System.out.print("Enter client name: ");
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
        System.out.print("Enter client name: ");
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
