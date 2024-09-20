package View;


import Entity.Material;
import Service.MaterialService;


import java.util.Optional;
import java.util.Scanner;

public class MaterialView {
    private final Scanner scanner = new Scanner(System.in);
    
    private MaterialService materialService = new MaterialService();

    public void main(int pid){
        String choice1;
        do{
            System.out.print("\033[0;35mDo you want to add materials to the project? (y/n): \033[0m");
             choice1 = scanner.nextLine();
        } while (!choice1.equals("y") && !choice1.equals("n"));


        if(choice1.equals("y")){
            createMaterial(pid);
            main(pid);
        }

        return;

    }

    public void createMaterial(int pid) {
        System.out.print("Enter the name of the material: ");
        String name = scanner.nextLine();
        System.out.print("Enter the unit cost of the material (Dh): ");
        double unitCost = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter the quantity of the material: ");
        double quantity = scanner.nextDouble();
        scanner.nextLine();

        String componentType = "Material";
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

       do {
           System.out.print("Do you want to add a transport cost? (y/n): ");
           choice = scanner.nextLine();
       } while (!choice.equals("y") && !choice.equals("n"));

        double transportCost = 0.0;

        if (choice.equals("y")) {
            System.out.print("Enter the transport cost (Dh): ");
            transportCost = scanner.nextDouble();
            scanner.nextLine();
        }

        double qualityCoefficient;

        do {
            System.out.print("Enter the material quality coefficient (1.0 = standard, > 1.0 = high quality): ");
            qualityCoefficient = scanner.nextDouble();
            scanner.nextLine();
        } while (qualityCoefficient < 1.0);


        Material material = new Material(name, unitCost, quantity, vatRate, pid, transportCost, qualityCoefficient);

        Optional<Material> op_material =  materialService.save(material);
        if (op_material.isPresent()) {
            System.out.println("\033[0;32mMaterial created successfully\033[0m");
        } else {
            System.err.println("\033[0;31mMaterial creation failed\033[0m");
        }


    }

    public Optional<Material> updateMaterial(int pid, String materialName){
        Material material = materialService.findByProjectId(pid).stream().filter(m -> m.getName().equals(materialName)).findFirst().orElse(null);

        if (material == null) {
            System.err.println("\033[0;31mMaterial not found\033[0m");
            return Optional.empty();
        }

        String choice;

        do {
            System.out.print("what do you want to update? (unit cost, quantity, vat rate, transport cost, quality coefficient): ");
            choice = scanner.nextLine();
        } while (!choice.equals("unit cost") && !choice.equals("quantity") && !choice.equals("vat rate") && !choice.equals("transport cost") && !choice.equals("quality coefficient"));

        switch (choice){
            case "unit cost":
                System.out.print("Enter the new unit cost: ");
                double unitCost = scanner.nextDouble();
                scanner.nextLine();
                material.setUnit_cost(unitCost);
                break;
            case "quantity":
                System.out.print("Enter the new quantity: ");
                double quantity = scanner.nextDouble();
                scanner.nextLine();
                material.setQuantity(quantity);
                break;
            case "vat rate":
                System.out.print("Enter the new vat rate: ");
                double vatRate = scanner.nextDouble();
                scanner.nextLine();
                material.setVat_rate(vatRate);
                break;
            case "transport cost":
                System.out.print("Enter the new transport cost: ");
                double transportCost = scanner.nextDouble();
                scanner.nextLine();
                material.setTransport_cost(transportCost);
                break;
            case "quality coefficient":
                System.out.print("Enter the new quality coefficient: ");
                double qualityCoefficient = scanner.nextDouble();
                scanner.nextLine();
                material.setQuality_coefficient(qualityCoefficient);
                break;
            default:
                return Optional.empty();
        }

        return materialService.update(material);
    }


}
