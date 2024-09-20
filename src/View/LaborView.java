package View;

import Entity.Labor;
import Service.LaborService;

import java.util.Optional;
import java.util.Scanner;

public class LaborView {

    private final Scanner scanner = new Scanner(System.in);
    private LaborService laborService = new LaborService();

    public void main(int pid){

        String choice;
        do {
            System.out.print("\033[0;36mDo you want to add Labors to the project? (y/n): \033[0m");
            choice  = scanner.nextLine();
        }while (!choice.equals("y") && !choice.equals("n"));


        if(choice.equals("y")){
            createLabor(pid);
            main(pid);
        }else {
            return;
        }

    }

    public void createLabor(int pid){

        System.out.print("Enter the type of labor (e.g., General Worker, Specialist): ");
        String type = scanner.nextLine();

        System.out.print("Enter the number of workers: ");
        double quantity = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter the hourly rate of the labor (Dh): ");
        double hourlyRate = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter the hours worked by the worker (hours): ");
        double hoursWorked = scanner.nextDouble();
        scanner.nextLine();

        double workerProductivity;
        do {
            System.out.print("Enter the productivity factor (1.0 = standard, > 1.0 = high productivity): ");
            workerProductivity = scanner.nextDouble();
            scanner.nextLine();
        } while (workerProductivity < 1.0);


        String componentType = "Labor";

        String choice;
        do {
            System.out.print("Do you want to add a VAT rate? (y/n): ");
            choice = scanner.nextLine();
        } while (!choice.equals("y") && !choice.equals("n"));

        double vatRate = 0.0;
        if (choice.equals("y")) {
            System.out.print("Enter the VAT rate (%): ");
            vatRate = scanner.nextDouble();
            scanner.nextLine();
        }

        Double unitCost = hourlyRate * hoursWorked * workerProductivity;

        Labor labor = new Labor(type, unitCost, quantity, vatRate, pid, hourlyRate, hoursWorked, workerProductivity);

        Optional<Labor> laborOptional = laborService.save(labor);
        if (laborOptional.isPresent()) {
            System.out.println("\033[0;32mLabor added successfully\033[0m");
        } else {
            System.err.println("\033[0;31mFailed to add labor\033[0m");
        }


    }

    public Optional<Labor> updateLabor(int pid,String laborName) {
        Labor labor = laborService.findLaborsByProjectId(pid).stream().filter(l -> l.getName().equals(laborName)).findFirst().orElse(null);

        if (labor == null) {
            System.err.println("\033[0;31mLabor not found\033[0m");
            return Optional.empty();
        }

        String choice;
        do {
            System.out.print("what do you want to update? (type,hourly rate, hours worked, productivity factor): ");
            choice = scanner.nextLine();
        } while (!choice.equals("type") && !choice.equals("hourly rate") && !choice.equals("hours worked") && !choice.equals("productivity factor"));

        switch (choice) {
            case "type":
                System.out.print("Enter the new type of labor: ");
                String type = scanner.nextLine();
                labor.setName(type);
                break;
            case "hourly rate":
                System.out.print("Enter the new hourly rate of the labor: ");
                double hourlyRate = scanner.nextDouble();
                scanner.nextLine();
                labor.setHourly_rate(hourlyRate);
                break;
            case "hours worked":
                System.out.print("Enter the new hours worked by the worker: ");
                double hoursWorked = scanner.nextDouble();
                scanner.nextLine();
                labor.setHours_worked(hoursWorked);
                break;
            case "productivity factor":
                System.out.print("Enter the new productivity factor (1.0 = standard, > 1.0 = high productivity): ");
                double productivityFactor = scanner.nextDouble();
                scanner.nextLine();
                labor.setProductivity_factor(productivityFactor);
                break;
        }

        return laborService.updateLabor(labor);

    }
}
