package Service;

import Entity.*;
import View.LaborView;
import View.MaterialView;
import Repository.implementation.ClientRepositoryImpl;
import Repository.implementation.ProjectRepositoryImpl;
import Repository.implementation.QuoteRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class ProjectService {

    private ProjectRepositoryImpl projectRepository = new ProjectRepositoryImpl();
    private MaterialService materialService = new MaterialService();
    private LaborService laborService = new LaborService();
    private ClientService clientService = new ClientService();
    private QuoteService quoteService = new QuoteService();



    // ------------------- Project CRUD -------------------
    public Optional<Project> save(Project project){ return projectRepository.save(project);}
    public Optional<Project> findByNameAndClient(String projectName, String clientName){return projectRepository.findByNameAndClient(projectName, clientName);}
    public Optional<Project> findById(int pid){
        return projectRepository.findById(pid);
    }
    public List<Project> findByStatus(String status){
        return projectRepository.findByStatus(status);
    }
    public  Optional<Project> update(Project project){
        return projectRepository.update(project);
    }


    // ------------------- Project Clients -------------------
    public Optional<Client> findClientByProjectId(int id){
         if(findById(id).isPresent()){
             return clientService.findById(findById(id).get().getClient_id());
         }
         return Optional.empty();
    }
    public Optional<Client> findClientByName(String name){
        return clientService.findByName(name);
    }
    public List<Client> findClientByProjectStatus(String status){
        return clientService.findByProjectStatus(status);
    }

    // ------------------- Project Materials  -------------------
    public List<Material> findMaterials(int pid){
        return materialService.findByProjectId(pid);
    }
    public Boolean deleteMaterial(int pid, String materialName){
        return materialService.delete(pid, materialName);
    }
    public Optional<Material> updateMaterial(int pid, String materialName){
        return new MaterialView().updateMaterial(pid, materialName);
    }


    // ------------------- Project Labors -------------------
    public Optional<Labor> saveLabor(Labor labor){
        return laborService.save(labor);
    }
    public List<Labor> findLabors(int pid){
        return laborService.findLaborsByProjectId(pid);
    }
    public Optional<Labor> updateLabor(int pid, String laborName){
        return new LaborView().updateLabor(pid, laborName);
    }
    public Boolean deleteLabor(int pid, String laborName){
        return laborService.deleteLabor(pid, laborName);
    }


    // ------------------- Project Quotes -------------------
    public Optional<Quote> findQuoteByProjectId(int id){
        return quoteService.findByProjectId(id);
    }
    public void updateQuote(Quote quote){
        new QuoteRepositoryImpl().update(quote);
    }


    // ------------------- Project Cost Calculations -------------------
    public Double calculateCost(int pid, Double profitMargin){

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

        System.out.println("\033[0;33mTotal cost of the project: \033[0m" + String.format("%.2f", total_cost) + " Dh");

        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // Add the profit margin to the total cost
        total_cost = total_cost * (1 + (profitMargin/100));

        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\033[0;33mTotal cost of the project after profit margin: \033[0m" + String.format("%.2f", total_cost) + " Dh");

        // add client discount for the project

       Client client = new ClientRepositoryImpl().findById(project.getClient_id()).get();

        double effectiveDiscount = client.getDiscount_percentage() / 100;
        total_cost = total_cost * (1 - effectiveDiscount);

       System.out.println("\033[0;33mTotal cost of the project after applay discount : \033[0m" + String.format("%.2f", total_cost) + " Dh");

       // Update the total cost of the project
        project.setTotal_cost(total_cost);

        projectRepository.update(project);
        return total_cost;

    }


}
