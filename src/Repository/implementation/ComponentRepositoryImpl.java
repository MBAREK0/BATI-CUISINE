package Repository.implementation;

import Database.DatabaseConnection;
import Entity.*;
import Repository.ComponentRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ComponentRepositoryImpl implements ComponentRepository {

    private final Connection connection;

    public ComponentRepositoryImpl() {
        this.connection = DatabaseConnection.getConnection();
    }

    public Optional<Component> findById(int id) {
        String query = "SELECT \n" +
                "    c.component_id, \n" +
                "    c.project_id, \n" +
                "    c.name, \n" +
                "    c.unit_cost, \n" +
                "    c.quantity, \n" +
                "    c.component_type, \n" +
                "    c.vat_rate,\n" +
                "    m.transport_cost, \n" +
                "    m.quality_coefficient, \n" +
                "    l.hourly_rate, \n" +
                "    l.hours_worked, \n" +
                "    l.productivity_factor\n" +
                "FROM \n" +
                "    Components c\n" +
                "LEFT JOIN \n" +
                "    Materials m ON c.component_id = m.component_id\n" +
                "LEFT JOIN \n" +
                "    Labor l ON c.component_id = l.component_id\n" +
                "WHERE c.component_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Component component = mapResultSetToComponent(rs);
                return Optional.of(component);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Component> findAll() {
        String query = "SELECT \n" +
                "    c.component_id, \n" +
                "    c.project_id, \n" +
                "    c.name, \n" +
                "    c.unit_cost, \n" +
                "    c.quantity, \n" +
                "    c.component_type, \n" +
                "    c.vat_rate,\n" +
                "    m.transport_cost, \n" +
                "    m.quality_coefficient, \n" +
                "    l.hourly_rate, \n" +
                "    l.hours_worked, \n" +
                "    l.productivity_factor\n" +
                "FROM \n" +
                "    Components c\n" +
                "LEFT JOIN \n" +
                "    Materials m ON c.component_id = m.component_id\n" +
                "LEFT JOIN \n" +
                "    Labor l ON c.component_id = l.component_id";
        List<Component> components = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Component component = mapResultSetToComponent(rs);
                components.add(component);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return components;
    }

    public Optional<Project> findProject(int pid){
        String query = "SELECT * FROM Projects WHERE project_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, pid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Project project = new ProjectRepositoryImpl().mapResultSetToProject(rs);
                return Optional.of(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Component> save(Component component) {
        String query = "INSERT INTO Components (name, unit_cost, quantity, component_type, vat_rate, project_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, component.getName());
            ps.setDouble(2, component.getUnit_cost());
            ps.setDouble(3, component.getQuantity());
            ps.setString(4, component.getComponent_type().toString());
            ps.setDouble(5, component.getVat_rate());
            ps.setInt(6, component.getProject_id());
            ps.executeUpdate();

            // Get the generated project_id
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int component_id = generatedKeys.getInt(1);
                    component.setComponent_id(component_id);
                    return Optional.of(component);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void update(Component component) {
        String query = "UPDATE Components SET name = ?, unit_cost = ?, quantity = ?, component_type = ?, vat_rate = ?, project_id = ? WHERE component_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, component.getName());
            ps.setDouble(2, component.getUnit_cost());
            ps.setDouble(3, component.getQuantity());
            ps.setString(4, component.getComponent_type().toString());
            ps.setDouble(5, component.getVat_rate());
            ps.setInt(6, component.getProject_id());
            ps.setInt(7, component.getComponent_id());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM Components WHERE component_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected Component mapResultSetToComponent(ResultSet rs) throws SQLException {
     int component_id = rs.getInt("component_id");
        String name = rs.getString("name");
        double unit_cost = rs.getDouble("unit_cost");
        double quantity = rs.getDouble("quantity");
        MaterialOrLabor component_type = MaterialOrLabor.valueOf(rs.getString("component_type"));
        double vat_rate = rs.getDouble("vat_rate");
        int project_id = rs.getInt("project_id");

        if(component_type == MaterialOrLabor.MATERIAL){
          double transport_cost = rs.getDouble("transport_cost");
          double quality_coefficient = rs.getDouble("quality_coefficient");
          return new Material(name, unit_cost, quantity, vat_rate, project_id, transport_cost, quality_coefficient);
        }else{
          double hourly_rate = rs.getDouble("hourly_rate");
          double hours_worked = rs.getDouble("hours_worked");
          double productivity_factor = rs.getDouble("productivity_factor");
          return new Labor(name, unit_cost, quantity, vat_rate, project_id, hourly_rate, hours_worked, productivity_factor);
        }
    }
}
