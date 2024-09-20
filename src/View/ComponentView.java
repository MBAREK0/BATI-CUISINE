package View;

import Entity.Labor;
import Entity.Material;
import Entity.Project;
import Service.ProjectService;

import java.util.List;
import java.util.Scanner;

public class ComponentView {

    private ProjectService projectService = new ProjectService();
    private Scanner scanner = new Scanner(System.in);



    public void viewComponent(Project project){

        String component;
         do {
             System.out.print("\033[0;94mWould you like to view materials or labors or both? (m/l/b) type 'q' to quit: \033[0m");
             component = scanner.nextLine();
         } while (!component.equals("m") && !component.equals("l") && !component.equals("b") && !component.equals("q"));

        if (component.equals("q")) {
            return;
        }

        if (component.equals("m")) {

            List<Material> materials = projectService.findMaterials(project.getProject_id());
            if(materials.isEmpty()){
                System.out.println();
                System.out.println("Materials:");
                System.out.println("~~~~~~~~~~");
                System.err.println("  No materials found");

            }else {
                System.out.println();
                System.out.println("Materials:");
                System.out.println("~~~~~~~~~~");
                materials.forEach(System.out::println);
            }
        }
        else if (component.equals("l")) {
            List<Labor> labors =  projectService.findLabors(project.getProject_id());
            if(labors.isEmpty()) {
                System.out.println();
                System.out.println("Labors:");
                System.out.println("~~~~~~~~~~");
                System.err.println("  No labors found");
            } else {
                System.out.println();
                System.out.println("Labors:");
                System.out.println("~~~~~~~~~~");
                labors.forEach(System.out::println);
            }
        }
        else {
            List<Material> materials = projectService.findMaterials(project.getProject_id());
            if(materials.isEmpty()){
                System.out.println();
                System.out.println("Materials:");
                System.out.println("~~~~~~~~~~");
                System.err.println("  No materials found");

            }else {
                System.out.println();
                System.out.println("Materials:");
                System.out.println("~~~~~~~~~~");
                materials.forEach(System.out::println);
            }

            List<Labor> labors =  projectService.findLabors(project.getProject_id());
            if(labors.isEmpty()) {
                System.out.println();
                System.out.println("Labors:");
                System.out.println("~~~~~~~~~~");
                System.err.println("  No labors found");
            } else {
                System.out.println();
                System.out.println("Labors:");
                System.out.println("~~~~~~~~~~");
                labors.forEach(System.out::println);
            }
        }

    }

    public  void  deleteComponent(Project project){
        String component;
        do {
            System.out.print("Would you like to delete a material or a labor? (m/l) type 'q' to quit: ");
            component = scanner.nextLine();

            if (component.equals("q")) return;

        } while (!component.equals("m") && !component.equals("l") && !component.equals("q"));


        if (component.equals("m")) {
            System.out.print("Enter the material name: ");
            String materialName = scanner.nextLine();
            if(projectService.deleteMaterial(project.getProject_id(), materialName))
                System.out.println("\033[0;32mMaterial deleted successfully\033[0m");
            else
                System.err.println("Material not found");
        } else {
            System.out.print("Enter the labor name: ");
            String laborName = scanner.nextLine();
            if( projectService.deleteLabor(project.getProject_id(), laborName))
                System.out.println("\033[0;32mLabor deleted successfully\033[0m");
            else
                System.err.println("Labor not found");
        }

    }

    public void updateComponent(Project project){

        String component;
        do {
            System.out.print("Would you like to update a material or a labor? (m/l) type 'q' to quit: ");
            component = scanner.nextLine();

            if (component.equals("q")) return;
        } while (!component.equals("m") && !component.equals("l") && !component.equals("q")) ;

        if (component.equals("m")) {
            System.out.print("Enter the material name: ");
            String materialName = scanner.nextLine();
            if(projectService.updateMaterial(project.getProject_id(), materialName).isPresent())
                System.out.println("\033[0;32mMaterial updated successfully\033[0m");
            else
                System.err.println("Material not found");
        } else {
            System.out.print("Enter the labor name: ");
            String laborName = scanner.nextLine();
            if(projectService.updateLabor(project.getProject_id(), laborName).isPresent())
                System.out.println("\033[0;32mLabor updated successfully\033[0m");
            else
                System.err.println("Labor not found");
        }

        // update the project total cost after updating the material or labor


    }


}
