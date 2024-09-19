package Service;

import Entity.Client;
import Entity.Project;
import Repository.implementation.ClientRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class ClientService {
    private ClientRepositoryImpl clientRepository = new ClientRepositoryImpl();

    public Optional<Client> save(Client client){
        return clientRepository.save(client);
    }

    public Optional<Client> findByName(String name){
        return clientRepository.findByName(name);
    }

    public Optional<Client> findById(int id){
        return clientRepository.findById(id);
    }

    public List<Client> findAll(){
        return clientRepository.findAll();
    }
    public List<Client> findByProjectStatus(String status){
        return clientRepository.findByProjectStatus(status);
    }

    public Optional<Client> update(Client client){
        return clientRepository.update(client);
    }
    public void delete(int id){
        clientRepository.delete(id);
    }
    public List<Project> findProjects(int cid){
        return clientRepository.findProjectsByClientId(cid);
    }
}
