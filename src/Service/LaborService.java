package Service;

import Entity.Labor;
import Repository.implementation.LaborRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class LaborService {

    private LaborRepositoryImpl laborRepository = new LaborRepositoryImpl();


    public Optional<Labor> save(Labor labor){
        return laborRepository.save(labor);
    }
    public List<Labor> findLaborsByProjectId(int id){
        return laborRepository.findLaborsByProjectId(id);
    }
    public Optional<Labor> updateLabor(Labor labor){
        return laborRepository.updateLabor(labor);
    }
    public Boolean deleteLabor(int pid, String laborName){
        return laborRepository.deleteLabor(pid, laborName);
    }

    public Double calculateLaborCost(int pid){
        double total_cost = 0;
        List<Labor> labors = findLaborsByProjectId(pid);
        for (Labor labor : labors) {
            if (labor.getQuantity() > 0)
                total_cost += labor.calculateCost();
        }
        return total_cost;
    }
}
