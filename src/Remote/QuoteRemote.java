package Remote;

import ConsoleUi.MainUi;
import ConsoleUi.QuoteUi;
import Entity.*;
import Repository.implementation.QuoteRepositoryImpl;
import Service.ProjectService;
import Service.QuoteService;
import Utils.DateChecker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class QuoteRemote {

    private Scanner scanner = new Scanner(System.in);
    private ProjectService projectService = new ProjectService();
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private QuoteRepositoryImpl quoteRepository = new QuoteRepositoryImpl();
    private QuoteService quoteService = new QuoteService();


    // Define colors
    private static final String WHITE_TEXT_ON_YELLOW_BG = "\033[97;43m";
    private static final String RESET = "\033[0m";

    // Define table borders and separators
    private static final String BORDER = "+";
    private static final String SEPARATOR = "-";
    private static final int TABLE_WIDTH = 120;
    private static final String BRIGHT_MAGENTA_BG = "\033[105m";
    private static final String GREEN_BG = "\033[42m";
    private static final String BLACK_BG = "\033[40m";
    private final QuoteUi quoteUi = new QuoteUi();
    private final MainUi mainUi = new MainUi();


    public void main() {

        Boolean isRunning = true;

        while (isRunning) {

            quoteUi.Ui();

            mainUi.printPrompt("quote");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    generateQuote();
                    break;
                case 2:
                    updateQuote();
                    break;
                case 3:
                    deleteQuote();
                    break;
                case 4:
                    viewQuote();
                    break;
                case 0:
                    isRunning = false;
                    break;
                default:
                    System.err.println("\033[0;31mInvalid choice\033[0m");
            }
        }
    }

    public void generateQuote(){
        System.out.print("Enter the project name: ");
        String project_name = scanner.nextLine();
        System.out.print("Enter the client name: ");
        String client_name = scanner.nextLine();
       Optional<Project> p = new ProjectService().findByNameAndClient(project_name,client_name);
       if (!p.isPresent()){
           System.err.println("\033[0;31mProject not found\033[0m");
           return;
       }
         Project project = p.get();
        createQuote(project.getProject_id());
    }

    public void viewQuote(){
        System.out.print("Enter the project name: ");
        String project_name = scanner.nextLine();
        System.out.print("Enter the client name: ");
        String client_name = scanner.nextLine();
        Optional<Project> p = projectService.findByNameAndClient(project_name,client_name);
        if (!p.isPresent()){
            System.err.println("\033[0;31mProject not found\033[0m");
            return;
        }
        Project project = p.get();
        showQuote(project.getProject_id());
    }
    public void createQuote(Integer pid) {

        Optional<Project> OpProject = projectService.findById(pid);
        if (!OpProject.isPresent()) {
            System.err.println("\033[0;31mProject not found\033[0m");
            return;
        }
        Project project = OpProject.get();

        if (project.getTotal_cost() == 0.0) {
            System.err.println("\033[1;33mThe project has no cost yet. Please add the materials and " +
                    "labors and other costs to the project before creating a quote.\033[0m");
            return;
        }

        Double estimated_amount = project.getTotal_cost();

        LocalDate issue_date;
        LocalDate validity_date;
        Boolean check_date;
       do {
           System.out.print("Enter the issue date (yyyy-mm-dd): ");
           issue_date = LocalDate.parse(scanner.nextLine(), dateFormatter);

           System.out.print("Enter the validity date (yyyy-mm-dd): ");
           validity_date = LocalDate.parse(scanner.nextLine(), dateFormatter);

           check_date = DateChecker.isValidPeriod(issue_date, validity_date);

       } while (!check_date);

        String accepted;
        do {
            System.out.print("Is the quote accepted? (y/n): ");
            accepted = scanner.nextLine();
        } while (!accepted.equals("y") && !accepted.equals("n"));

        String save;
        do {
            System.out.print("Would you like to save the quote? (y/n): ");
            save = scanner.nextLine();
        } while (!save.equals("y") && !save.equals("n")) ;

        if (save.equals("n"))  return;

        Quote quote = new Quote(pid, estimated_amount, issue_date, validity_date, accepted.equals("y"));

        if (quoteService.save(quote).isPresent()){
            System.out.println();
            System.out.println("\033[0;32mQuote created successfully\033[0m");
            System.out.println();
        }else
            System.err.println("\033[0;31mFailed to create the quote\033[0m");

        showQuote(pid);
    }

    public void showQuote(int pid) {
        Optional<Project> OpProject = projectService.findById(pid);
        if (!OpProject.isPresent()) {
            System.err.println("\033[0;31mProject not found\033[0m");
            return;
        }
        Project project =  OpProject.get();

        Optional<Client> OpClient = projectService.findClientByProjectId(pid);
        if (!OpClient.isPresent()) {
            System.err.println("\033[0;31mClient not found\033[0m");
            return;
        }
        Client client = OpClient.get();


        Optional<Quote> OpQuote = projectService.findQuoteByProjectId(pid);
        if (!OpQuote.isPresent()) {
            System.err.println("\033[0;31mQuote not found\033[0m");
            return;
        }
        Quote quote = OpQuote.get();

        List<Material> materials = projectService.findMaterials(pid);
        List<Labor> labors = projectService.findLabors(pid);

        printQuoteDetails(project, client, materials, labors, quote);

        return;

    }

    public void updateQuote(){
        System.out.print("Enter the project name: ");
        String project_name = scanner.nextLine();
        System.out.print("Enter the client name: ");
        String client_name = scanner.nextLine();
        Optional<Project> p = new ProjectService().findByNameAndClient(project_name,client_name);
        if (!p.isPresent()){
            System.err.println("\033[0;31mProject not found\033[0m");
            return;
        }
        Project project = p.get();

        Optional<Quote> OpQuote = projectService.findQuoteByProjectId(project.getProject_id());

        if (!OpQuote.isPresent()) {
            System.err.println("\033[0;31mQuote not found\033[0m");
            return;
        }
        Quote quote = OpQuote.get();
        System.out.print("would you like to update the issue date? (y/n): ");
        String choice = scanner.nextLine();
        while (!choice.equals("y") && !choice.equals("n")) {
            System.err.println("\033[0;31mInvalid choice\033[0m");
            System.out.print("would you like to update the issue date? (y/n): ");
            choice = scanner.nextLine();
        }
        if (choice.equals("y")) {
            System.out.print("Enter the new issue date (yyyy-mm-dd): ");
            LocalDate issue_date = LocalDate.parse(scanner.nextLine(), dateFormatter);
            quote.setIssueDate(issue_date);
        }

        System.out.print("would you like to update the validity date? (y/n): ");
        choice = scanner.nextLine();
        while (!choice.equals("y") && !choice.equals("n")) {
            System.err.println("\033[0;31mInvalid choice\033[0m");
            System.out.print("would you like to update the validity date? (y/n): ");
            choice = scanner.nextLine();
        }

        if (choice.equals("y")) {
            System.out.print("Enter the new validity date (yyyy-mm-dd): ");
            LocalDate validity_date = LocalDate.parse(scanner.nextLine(), dateFormatter);
            quote.setValidityDate(validity_date);
        }

        System.out.print("would you like to update the accepted status? (y/n): ");
        choice = scanner.nextLine();
        while (!choice.equals("y") && !choice.equals("n")) {
            System.err.println("\033[0;31mInvalid choice\033[0m");
            System.out.print("would you like to update the accepted status? (y/n): ");
            choice = scanner.nextLine();
        }
        if (choice.equals("y")) {
            System.out.print("Is the quote accepted? (y/n): ");
            String accepted = scanner.nextLine();
            while (!accepted.equals("y") && !accepted.equals("n")) {
                System.err.println("\033[0;31mInvalid choice\033[0m");
                System.out.print("Is the quote accepted? (y/n): ");
                accepted = scanner.nextLine();
            }
            quote.setAccepted(accepted.equals("y"));
        }

        System.out.print("Would you like to save the quote? (y/n): ");
        String save = scanner.nextLine();
        while (!save.equals("y") && !save.equals("n")) {
            System.err.println("\033[0;31mInvalid choice\033[0m");
            System.out.print("Would you like to save the quote? (y/n): ");
            save = scanner.nextLine();
        }
        if (save.equals("n")) {
            return;
        }
        quoteRepository.update(quote);
        System.out.println();
        System.out.println("\033[0;32mQuote updated successfully\033[0m");

    }

    public void deleteQuote(){
        System.out.print("Enter the project name: ");
        String project_name = scanner.nextLine();
        System.out.print("Enter the client name: ");
        String client_name = scanner.nextLine();
        Optional<Project> p = new ProjectService().findByNameAndClient(project_name,client_name);
        if (!p.isPresent()){
            System.err.println("\033[0;31mProject not found\033[0m");
            return;
        }
        Project project = p.get();
        Optional<Quote> OpQuote = projectService.findQuoteByProjectId(project.getProject_id());
        if (!OpQuote.isPresent()) {
            System.err.println("\033[0;31mQuote not found\033[0m");
            return;
        }
        Quote quote = OpQuote.get();
        System.out.print("Would you like to delete the quote? (y/n): ");
        String choice = scanner.nextLine();
        while (!choice.equals("y") && !choice.equals("n")) {
            System.err.println("\033[0;31mInvalid choice\033[0m");
            System.out.print("Would you like to delete the quote? (y/n): ");
            choice = scanner.nextLine();
        }
        if (choice.equals("n")) {
            return;
        }
        quoteRepository.delete(quote.getQuoteId());
        System.out.println();
        System.out.println("\033[0;32mQuote deleted successfully\033[0m");
    }
    public void printQuoteDetails(Project project, Client client, List<Material> materials, List<Labor> labors, Quote quote) {
        // Print table header
        printTableHeader();

        // Print project information
        printTableRow("    ",BLACK_BG);
        printTableRow("  Quote for project: " + project.getProject_name(),BLACK_BG);
        printTableRow("    ",BLACK_BG);


        printTableRow("  Project information",BRIGHT_MAGENTA_BG);
        printTableRow("  Client: " + client.getName(),WHITE_TEXT_ON_YELLOW_BG);
        printTableRow("  Surface area: " + project.getSurface_area() + " m^2",WHITE_TEXT_ON_YELLOW_BG);
        printTableRow("  Profit margin: " + project.getProfit_margin() + " %",WHITE_TEXT_ON_YELLOW_BG);
        printTableRow("  Project status: " + project.getProject_status(),WHITE_TEXT_ON_YELLOW_BG);

        // Print materials

        printTableRow("  Project materials",BRIGHT_MAGENTA_BG);
        if (materials.isEmpty())
            printTableRow("  No materials added to the project",WHITE_TEXT_ON_YELLOW_BG);
        else
            materials.forEach(material -> printTableRow(material.toString(),WHITE_TEXT_ON_YELLOW_BG));


        // Print labors
        printTableRow("  Project labors",BRIGHT_MAGENTA_BG);
        if (labors.isEmpty())
            printTableRow("  No labors added to the project",WHITE_TEXT_ON_YELLOW_BG);
        else
        labors.forEach(labor -> printTableRow(labor.toString(),WHITE_TEXT_ON_YELLOW_BG));

        // Print quote information
        printTableRow("  Quote information",BRIGHT_MAGENTA_BG);
        printTableRow("  Issue date: " + quote.getIssueDate(),WHITE_TEXT_ON_YELLOW_BG);
        printTableRow("  Validity date: " + quote.getValidityDate(),WHITE_TEXT_ON_YELLOW_BG);
        printTableRow("  Accepted: " + quote.isAccepted(),WHITE_TEXT_ON_YELLOW_BG);
        printTableRow("  Estimated amount: " +  String.format("%.2f", quote.getEstimatedAmount() ) + " Dh",GREEN_BG);



        // Print table footer
        printTableFooter();
    }
    private void printTableHeader() {
        System.out.println(WHITE_TEXT_ON_YELLOW_BG + BORDER + SEPARATOR.repeat(TABLE_WIDTH - 2) + BORDER + RESET);
    }
    private void printTableFooter() {
        System.out.println(WHITE_TEXT_ON_YELLOW_BG + BORDER + SEPARATOR.repeat(TABLE_WIDTH - 2) + BORDER + RESET);
    }
    private void printTableRow(String text,String COLOR) {
        String formattedText = String.format("%-" + (TABLE_WIDTH - 4) + "s", text);
        System.out.println(COLOR + BORDER + formattedText + BORDER + RESET);
    }




}


