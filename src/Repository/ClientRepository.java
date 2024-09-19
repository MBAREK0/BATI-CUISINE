package Repository;

import Entity.Client;
import Entity.Project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    Optional<Client> findById(int id);
    Optional<Client> findByName(String name);
    List<Client> findByProjectStatus(String status);
    List<Client> findAll();
    Optional<Client> save(Client client);
    Optional<Client> update(Client client);
    void delete(int id);
    List<Project> findProjectsByClientId(int id);
    Client mapResultSetToClient(ResultSet rs) throws SQLException;

}