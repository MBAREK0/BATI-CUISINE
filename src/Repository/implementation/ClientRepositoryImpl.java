package Repository.implementation;


import Entity.Client;
import Entity.Project;
import Repository.ClientRepository;
import Database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientRepositoryImpl implements ClientRepository {
    private final Connection connection;
    private final ProjectRepositoryImpl projectRepository = new ProjectRepositoryImpl();

    public ClientRepositoryImpl() {
        this.connection = DatabaseConnection.getConnection();


    }

    public Optional<Client> findById(int id) {
        String query = "SELECT * FROM Clients WHERE client_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Client client = mapResultSetToClient(rs);
                return Optional.of(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Client> findByName(String name) {
        String query = "SELECT * FROM Clients WHERE name = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Client client = mapResultSetToClient(rs);
                return Optional.of(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT * FROM Clients";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Client client = mapResultSetToClient(rs);
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

  public List<Project> findProjectsByClientId(int id) {
        return projectRepository.findProjectsByClientId(id);
    }


    @Override
    public void save(Client client) {
        String query = "INSERT INTO Clients (name, address, phone, is_professional,discount_percentage) VALUES (?, ?, ?, ?,?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, client.getName());
            ps.setString(2, client.getAddress());
            ps.setString(3, client.getPhone());
            ps.setBoolean(4, client.isIs_professional());
            ps.setDouble(5, client.getDiscount_percentage());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Client client) {
        String query = "UPDATE Clients SET name = ?, address = ?, phone = ?, is_professional = ? , discount_percentage = ? WHERE client_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, client.getName());
            ps.setString(2, client.getAddress());
            ps.setString(3, client.getPhone());
            ps.setBoolean(4, client.isIs_professional());
            ps.setDouble(5, client.getDiscount_percentage());
            ps.setInt(6, client.getClient_id());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM Clients WHERE client_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected Client mapResultSetToClient(ResultSet rs) throws SQLException {
        int id = rs.getInt("client_id");
        String name = rs.getString("name");
        String address = rs.getString("address");
        String phone = rs.getString("phone");
        boolean isProfessional = rs.getBoolean("is_professional");
        Double discount_percentage = rs.getDouble("discount_percentage");
        Client client = new Client(name, address, phone, isProfessional, discount_percentage);
        client.setClient_id(id);
        return client;
    }
}
