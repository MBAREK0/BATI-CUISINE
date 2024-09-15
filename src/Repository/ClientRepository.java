package Repository;

import Entity.Client;
import Entity.Project;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    Optional<Client> findByName(String name);
    List<Client> findAll();
    void save(Client client);
    void update(Client client);
    void delete(int id);
    List<Project> findProjectsByClientId(int id);
}