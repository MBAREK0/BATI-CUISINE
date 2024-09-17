package Remote;


import Entity.Material;
import Repository.implementation.MaterialRepositoryImpl;


import java.util.Optional;
import java.util.Scanner;

public class MaterialRemote {
    private final Scanner scanner = new Scanner(System.in);
    
    private MaterialRepositoryImpl materialRepository = new MaterialRepositoryImpl();

    public void main(int pid){
        System.out.print("\033[0;35mDo you want to add materials to the project? (y/n): \033[0m");
        String choice1 = scanner.nextLine();
        while (!choice1.equals("y") && !choice1.equals("n")) {
            System.err.println("\033[0;31mInvalid choice\033[0m");
            System.out.print("\033[0;35mDo you want to add materials to the project? (y/n): \033[0m");
            choice1 = scanner.nextLine();
        }

        if(choice1.equals("y")){
            createMaterial(pid);
            main(pid);
        }else {
            return;
        }
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
        System.out.print("Do you want to add a VAT rate? (y/n): ");
        String choice = scanner.nextLine();
        double vatRate = 0.0;
        while (!choice.equals("y") && !choice.equals("n")) {
            System.err.println("\033[0;31mInvalid choice\033[0m");
            System.out.print("Do you want to add a VAT rate? (y/n): ");
            choice = scanner.nextLine();
        }
        if (choice.equals("y")) {
            System.out.print("Enter the VAT rate (%): ");
            vatRate = scanner.nextDouble();
            scanner.nextLine();
        }

        System.out.print("Do you want to add a transport cost? (y/n): ");
        choice = scanner.nextLine();

        double transportCost = 0.0;
        while (!choice.equals("y") && !choice.equals("n")) {
            System.err.println("\033[0;31mInvalid choice\033[0m");
            System.out.print("Do you want to add a transport cost? (y/n): ");
            choice = scanner.nextLine();
        }
        if (choice.equals("y")) {
            System.out.print("Enter the transport cost (Dh): ");
            transportCost = scanner.nextDouble();
            scanner.nextLine();
        }

        System.out.print("Enter the material quality coefficient (1.0 = standard, > 1.0 = high quality): ");
        double qualityCoefficient = scanner.nextDouble();

        while (qualityCoefficient < 1.0) {
            System.err.println("\033[0;31mInvalid choice\033[0m");
            System.out.print("Enter the material quality coefficient (1.0 = standard, > 1.0 = high quality): ");
            qualityCoefficient = scanner.nextDouble();
        }
        scanner.nextLine();
        // Material(String name, double unit_cost, double quantity, double vat_rate, int project_id, double transport_cost, double quality_coefficient)
        Material material = new Material(name, unitCost, quantity, vatRate, pid, transportCost, qualityCoefficient);

      Optional<Material> material1 =  materialRepository.save(material);
        if (material1.isPresent()) {
            System.out.println("\033[0;32mMaterial created successfully\033[0m");
        } else {
            System.err.println("\033[0;31mMaterial creation failed\033[0m");
        }




    }
    public Optional<Material> updateMaterial(int pid, String materialName){
        Material material = materialRepository.findByProjectId(pid).stream().filter(m -> m.getName().equals(materialName)).findFirst().orElse(null);

        if (material == null) {
            System.err.println("\033[0;31mMaterial not found\033[0m");
            return Optional.empty();
        }

        System.out.print("what do you want to update? (unit cost, quantity, vat rate, transport cost, quality coefficient): ");
        String choice = scanner.nextLine();
        while (!choice.equals("unit cost") && !choice.equals("quantity") && !choice.equals("vat rate") && !choice.equals("transport cost") && !choice.equals("quality coefficient")) {
            System.err.println("\033[0;31mInvalid choice\033[0m");
            System.out.print("what do you want to update? (unit cost, quantity, vat rate, transport cost, quality coefficient): ");
            choice = scanner.nextLine();
        }

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

        return materialRepository.updateMaterial(material);
    }
    public void deleteMaterial() {

    }
    public void showMaterial() {

    }

}
