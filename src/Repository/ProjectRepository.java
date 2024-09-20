package Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import Entity.*;

public interface ProjectRepository {
    Optional<Project> save(Project project);
    Optional<Project> findById(int id);
    List<Project> findByStatus (String status);
    List<Project> findProjectsByClientId(int id);
    Optional<Project> update(Project project);
    void delete(int id);
    Project mapResultSetToProject(ResultSet rs) throws SQLException;


}