package Repository;

import Entity.Labor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface LaborRepository {
    List<Labor> findLaborsByProjectId(int id);
    Optional<Labor> save(Labor labor);
    Labor mapResultSetToComponent(ResultSet rs) throws SQLException;
}
