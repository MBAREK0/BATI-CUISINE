package Service;

import Entity.Labor;
import Entity.Material;
import Entity.Project;
import Remote.LaborRemote;
import Remote.MaterialRemote;
import Repository.implementation.ProjectRepositoryImpl;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ProjectService {

    private ProjectRepositoryImpl projectRepository = new ProjectRepositoryImpl();
    private MaterialService materialService = new MaterialService();
    private LaborService laborService = new LaborService();
    private Scanner scanner = new Scanner(System.in);

   public Double calculateCost(int pid, Double profitMargin){

       System.out.print("Would you like to apply VAT to the project? (y/n): ");
       String choice1 = scanner.nextLine();
       while (!choice1.equals("y") && !choice1.equals("n")) {
           System.err.println("\033[0;31mInvalid choice\033[0m");
           System.out.print("Would you like to apply VAT to the project? (y/n): ");
           choice1 = scanner.nextLine();
       }
       double vatRate = 0.0;
       if (choice1.equals("y")) {
           System.out.print("Enter the VAT percentage (%): ");
           vatRate = scanner.nextDouble();
           scanner.nextLine();
       }



       double total_cost = 0;
        Optional<Project> OpProject = projectRepository.findById(pid);
        if (!OpProject.isPresent()) {
            System.err.println("\033[0;31mProject not found\033[0m");
            return 0.0;
        }
        Project project = OpProject.get();
        // Calculate the total cost of the project base on the materials and labors
        System.out.println("\033[0;33mCalculating the total cost of the project...\033[0m");

        total_cost += materialService.calculateMaterialCost(pid);
        total_cost += laborService.calculateLaborCost(pid);
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
           Thread.sleep(300);
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
        System.out.println("\033[0;33mTotal cost of the project: \033[0m" + String.format("%.2f", total_cost) + " Dh");


        // Add the VAT rate to the total cost
        total_cost = total_cost * (1 + (vatRate/100));

        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\033[0;33mTotal cost of the project after VAT: \033[0m" + String.format("%.2f", total_cost) + " Dh");


        // Add the profit margin to the total cost
        total_cost = total_cost * (1 + (profitMargin/100));

        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\033[0;33mTotal cost of the project after profit margin: \033[0m" + String.format("%.2f", total_cost) + " Dh");


        // Update the total cost of the project
        project.setTotal_cost(total_cost);

        projectRepository.update(project);
        return total_cost;

    }

    public Boolean deleteMaterial(int pid, String materialName){
        return projectRepository.deleteMaterial(pid, materialName);
    }
    public Boolean deleteLabor(int pid, String laborName){
        return projectRepository.deleteLabor(pid, laborName);
    }

    public Optional<Material> updateMaterial(int pid, String materialName){
       return new MaterialRemote().updateMaterial(pid, materialName);
    }

    public Optional<Labor> updateLabor(int pid, String laborName){
        return new LaborRemote().updateLabor(pid, laborName);
    }

    public List<Material> viewMaterials(int pid){
        return projectRepository.findMaterialsByProjectId(pid);
    }

    public List<Labor> viewLabors(int pid){
        return projectRepository.findLaborsByProjectId(pid);
    }

    public  void updateProject(Project project){
        projectRepository.update(project);
    }

}
