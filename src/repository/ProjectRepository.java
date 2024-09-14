package repository;

import java.util.List;
import java.util.Optional;
import Entity.Project;

public interface ProjectRepository {
    Optional<Project> findById(int id);
    List<Project> findAll();
    void save(Project project);
    void update(Project project);
    void delete(int id);
}