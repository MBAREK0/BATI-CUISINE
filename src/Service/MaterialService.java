package Service;

import Entity.Material;
import Remote.MaterialRemote;;
import Repository.implementation.MaterialRepositoryImpl;


import java.util.List;
import java.util.Optional;

public class MaterialService {

    private MaterialRepositoryImpl materialRepository = new MaterialRepositoryImpl();

    // ------------------- Material CRUD -------------------
    public Optional<Material> save(Material material) {
        return materialRepository.save(material);
    }
    public Optional<Material> findById(int id) {
        return materialRepository.findById(id);
    }
    public List<Material> findByProjectId(int pid) {
        return materialRepository.findByProjectId(pid);
    }
    public Optional<Material> update(Material material) {
        return  materialRepository.updateMaterial(material);
    }
    public Boolean delete(int projectId, String materialName) {
        return materialRepository.deleteMaterial(projectId, materialName);
    }

    // ------------------- Material Cost Calculation -------------------
    public Double calculateMaterialCost(int pid){
        double total_cost = 0;
        List<Material> materials = findByProjectId(pid);
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
