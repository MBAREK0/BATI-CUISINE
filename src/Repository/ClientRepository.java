package Repository;

import Entity.Client;
import Entity.Project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    Optional<Client> save(Client client);
    Optional<Client> findById(int id);
    Optional<Client> findByName(String name);
    List<Client> findByProjectStatus(String status);
    List<Client> findAll();
    List<Project> findProjectsByClientId(int id) ;
    Optional<Client> update(Client client);
    void delete(int id);
    Client mapResultSetToClient(ResultSet rs) throws SQLException;
}