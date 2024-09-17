package Repository.implementation;

import Database.DatabaseConnection;
import Entity.Component;
import Entity.Labor;
import Entity.Material;
import Entity.MaterialOrLabor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LaborRepositoryImpl {
    private final Connection connection;
    private ComponentRepositoryImpl componentRepository = new ComponentRepositoryImpl();

    public LaborRepositoryImpl() {
        this.connection = DatabaseConnection.getConnection();
    }
    public Optional<Labor> save(Labor labor) {
        Optional<Component> component = componentRepository.save(labor);
        if (component.isPresent()) {
            String query = "INSERT INTO Labor (component_id, hourly_rate, hours_worked, productivity_factor) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, component.get().getComponent_id());
                ps.setDouble(2, labor.getHourly_rate());
                ps.setDouble(3, labor.getHours_worked());
                ps.setDouble(4, labor.getProductivity_factor());
                ps.executeUpdate();
                return Optional.of(labor);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    public List<Labor> findLaborsByProjectId(int id) {
        String query = "SELECT \n" +
                "    c.component_id, \n" +
                "    c.project_id, \n" +
                "    c.name, \n" +
                "    c.unit_cost, \n" +
                "    c.quantity, \n" +
                "    c.component_type, \n" +
                "    c.vat_rate,\n" +
                "    l.hourly_rate, \n" +
                "    l.hours_worked, \n" +
                "    l.productivity_factor\n" +
                "FROM \n" +
                "    Components c\n" +
                "INNER JOIN \n" +
                "    Labor l ON c.component_id = l.component_id\n" +
                "WHERE c.project_id = ?";

        List<Labor> labors = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Labor labor = mapResultSetToComponent(rs);
                labors.add(labor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return labors;
    }




    protected Labor mapResultSetToComponent(ResultSet rs) throws SQLException {
        int component_id = rs.getInt("component_id");
        String name = rs.getString("name");
        double unit_cost = rs.getDouble("unit_cost");
        double quantity = rs.getDouble("quantity");
        MaterialOrLabor component_type = MaterialOrLabor.valueOf(rs.getString("component_type"));
        double vat_rate = rs.getDouble("vat_rate");
        int project_id = rs.getInt("project_id");

        if(component_type == MaterialOrLabor.LABOR){
            double hourly_rate = rs.getDouble("hourly_rate");
            double hours_worked = rs.getDouble("hours_worked");
            double productivity_factor = rs.getDouble("productivity_factor");
            return new Labor(name, unit_cost, quantity, vat_rate, project_id, hourly_rate, hours_worked, productivity_factor);
        }else{
            // cant return a Material object if the component_type is LABOR
            return null;
        }
    }
}
