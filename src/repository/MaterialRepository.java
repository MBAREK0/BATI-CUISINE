package repository;

import Entity.Material;
import java.util.List;
import java.util.Optional;

public interface MaterialRepository {
    Optional<Material> findById(int id);
    List<Material> findByProjectId(int projectId);
    List<Material> findAll();
    void save(Material material);
    void update(Material material);
    void delete(int id);
}