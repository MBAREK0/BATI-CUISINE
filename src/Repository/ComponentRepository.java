package Repository;

import Entity.Component;
import Entity.MaterialOrLabor;
import Entity.Project;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

public interface ComponentRepository {
    Optional<Component> findById(int id);
    List<Component> findAll();
    List<Component> findByProjectId(int id);
    Optional<Component> save(Component component);
    Optional<Component>  update(Component component);
     Boolean delete(int pid, String componentName, MaterialOrLabor materialOrLabor);
    Component mapResultSetToComponent(ResultSet rs) throws Exception;
}