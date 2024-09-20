package Repository.implementation;

import Entity.*;
import Repository.ProjectRepository;
import Database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectRepositoryImpl implements ProjectRepository {

    private final Connection connection;

    private QuoteRepositoryImpl quoteRepositoryImpl = new QuoteRepositoryImpl();

    public ProjectRepositoryImpl() {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public Optional<Project> save(Project project){
        String query = "INSERT INTO Projects (project_name, profit_margin, total_cost, project_status, surface_area, client_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, project.getProject_name());
            ps.setDouble(2, project.getProfit_margin());
            ps.setDouble(3, project.getTotal_cost());
            ps.setString(4, project.getProject_status());
            ps.setDouble(5, project.getSurface_area());
            ps.setInt(6, project.getClient_id());
            ps.executeUpdate();

            // Get the generated project_id
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    project.setProject_id(generatedKeys.getInt(1));
                }
            }
            return Optional.of(project);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Project> findById(int id) {
        String query = "SELECT * FROM Projects WHERE project_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Project project = mapResultSetToProject(rs);
                    return Optional.of(project);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Project> findByNameAndClient(String name, String clientName) {
        Optional<Client> client = new ClientRepositoryImpl().findByName(clientName);
        if (!client.isPresent()) {
            return Optional.empty();
        }
        int client_id = client.get().getClient_id();

        String query = "SELECT * FROM Projects WHERE project_name = ? AND client_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setInt(2, client_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Project project = mapResultSetToProject(rs);
                    return Optional.of(project);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();

    }

    @Override
    public List<Project> findByStatus(String status) {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT * FROM Projects WHERE LOWER(project_status) = LOWER(?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {  // Use PreparedStatement
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Project project = mapResultSetToProject(rs);
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    @Override
    public List<Project> findProjectsByClientId(int id) {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT * FROM Projects WHERE client_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Project project = mapResultSetToProject(rs);
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    @Override
    public Optional<Project> update (Project project){
        String query = "UPDATE Projects SET project_name = ?, profit_margin = ?, total_cost = ?, project_status = ?, surface_area = ?, client_id = ? WHERE project_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, project.getProject_name());
            ps.setDouble(2, project.getProfit_margin());
            ps.setDouble(3, project.getTotal_cost());
            ps.setString(4, project.getProject_status());
            ps.setDouble(5, project.getSurface_area());
            ps.setInt(6, project.getClient_id());
            ps.setInt(7, project.getProject_id());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.of(project);
    }

    @Override
    public void delete ( int id){
        String query = "DELETE FROM Projects WHERE project_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Project mapResultSetToProject (ResultSet rs) throws SQLException {
        int project_id = rs.getInt("project_id");
        String project_name = rs.getString("project_name");
        double profit_margin = rs.getDouble("profit_margin");
        double total_cost = rs.getDouble("total_cost");
        String project_status = rs.getString("project_status");
        double surface_area = rs.getDouble("surface_area");
        int client_id = rs.getInt("client_id");
        Project project = new Project(project_name, profit_margin, total_cost, project_status, surface_area, client_id);
        project.setProject_id(project_id);
        return project;
    }

}
