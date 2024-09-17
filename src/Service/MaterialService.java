package Service;

import Entity.Material;
import Repository.implementation.ProjectRepositoryImpl;


import java.util.List;

public class MaterialService {
    private ProjectRepositoryImpl projectRepository = new ProjectRepositoryImpl();

    public Double calculateMaterialCost(int pid){
        double total_cost = 0;
        List<Material> materials = projectRepository.findMaterialsByProjectId(pid);
        if (materials.isEmpty()) {
            return 0.0;
        }
        for (Material material : materials) {
          if (material.getQuantity() > 0)
              total_cost += material.calculateCost();

        }
        return total_cost;
    }
}
