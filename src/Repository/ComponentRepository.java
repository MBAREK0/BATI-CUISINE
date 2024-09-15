package Repository;

import Entity.Component;
import Entity.Project;

import java.util.List;
import java.util.Optional;

public interface ComponentRepository {
    Optional<Component> findById(int id);
    Optional<Project> findProject(int id);
    List<Component> findAll();
    Optional<Component> save(Component component);
    void update(Component component);
    void delete(int id);
}