package Service;

import Entity.Labor;
import Repository.implementation.ProjectRepositoryImpl;

import java.util.List;

public class LaborService {

    private ProjectRepositoryImpl projectRepository = new ProjectRepositoryImpl();

    public Double calculateLaborCost(int pid){
        double total_cost = 0;
        List<Labor> labors = projectRepository.findLaborsByProjectId(pid);
        for (Labor labor : labors) {
            if (labor.getQuantity() > 0)
                total_cost += labor.calculateCost();
        }
        return total_cost;
    }
}
