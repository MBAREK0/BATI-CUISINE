package Repository;

import Entity.Labor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface LaborRepository {
    Optional<Labor> save(Labor labor) throws SQLException;
    List<Labor> findLaborsByProjectId(int id);
    Optional<Labor> updateLabor(Labor labor);
    public Boolean deleteLabor(int pid, String laborName);
    Labor mapResultSetToComponent(ResultSet rs) throws SQLException;
}
