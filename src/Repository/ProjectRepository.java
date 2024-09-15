package Repository;

import java.util.List;
import java.util.Optional;

import Entity.*;

public interface ProjectRepository {
    Optional<Project> findById(int id);
    Optional<Project> findByName(String name);
    List<Project> findAll();
    List<Project> findByStatus (String status);
    Optional<Client> findClientByProjectId(int id);

    Optional<Project> save(Project project);
    void update(Project project);
    void delete(int id);
    List<Component> findComponentsByProjectId(int id);
    List<Material> findMaterialsByProjectId(int id);
    List<Labor> findLaborsByProjectId(int id);
}