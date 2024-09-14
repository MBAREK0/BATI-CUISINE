package repository;

import Entity.Labor;
import java.util.List;
import java.util.Optional;

public interface LaborRepository {
    Optional<Labor> findById(int id);
    List<Labor> findByProjectId(int projectId);
    List<Labor> findAll();
    void save(Labor labor);
    void update(Labor labor);
    void delete(int id);
}