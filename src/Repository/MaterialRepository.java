package Repository;

import Entity.Material;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface MaterialRepository {

     Optional<Material> save(Material material);
     Optional<Material> findById(int id);
     List<Material> findByProjectId(int id);
     Optional<Material> updateMaterial(Material material);
     Boolean deleteMaterial(int pid, String materialName);
     Material mapResultSetToMaterial(ResultSet rs) throws SQLException;

}
