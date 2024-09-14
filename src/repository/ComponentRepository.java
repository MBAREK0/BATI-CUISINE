package repository;

import Entity.Component;
import java.util.List;
import java.util.Optional;

public interface ComponentRepository {
    Optional<Component> findById(int id);
    List<Component> findByProjectId(int projectId);
    List<Component> findAll();
    void save(Component component);
    void update(Component component);
    void delete(int id);
}