package repository;

import Entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    Optional<Client> findById(int id);
    List<Client> findAll();
    void save(Client client);
    void update(Client client);
    void delete(int id);
}