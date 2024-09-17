package Repository;

import Entity.Client;
import Entity.Project;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    Optional<Client> findById(int id);
    Optional<Client> findByName(String name);
    List<Client> findAll();
    List<Project> findProjectsByClientId(int id);
    void save(Client client);
    void update(Client client);
    void delete(int id);
Client mapResultSetToClient(ResultSet rs) throws Exception;

}