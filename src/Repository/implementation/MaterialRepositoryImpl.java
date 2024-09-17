package Repository.implementation;

import Entity.Component;
import Entity.Material;
import Entity.MaterialOrLabor;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import Database.DatabaseConnection;

public class MaterialRepositoryImpl  {

    private final Connection connection;
    private ComponentRepositoryImpl componentRepository = new ComponentRepositoryImpl();

    public MaterialRepositoryImpl() {
        this.connection = DatabaseConnection.getConnection();
    }


    public Optional<Material> findById(int id) {
        String query = "SELECT " +
                "    c.component_id, " +
                "    c.project_id, " +
                "    c.name, " +
                "    c.unit_cost, " +
                "    c.quantity, " +
                "    c.component_type, " +
                "    c.vat_rate, " +
                "    m.transport_cost, " +
                "    m.quality_coefficient " +
                "FROM " +
                "    Components c " +
                "INNER JOIN " +
                "    Materials m ON c.component_id = m.component_id " +
                "WHERE " +
                "    c.component_type = 'Material' AND c.component_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Material material = mapResultSetToMaterial(rs);
                return Optional.of(material);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Material> save(Material material) {
      Optional<Component> component =  componentRepository.save(material);
        if(component.isPresent()){

            String query = "INSERT INTO Materials (component_id, transport_cost, quality_coefficient) VALUES (?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, component.get().getComponent_id());
                ps.setDouble(2, material.getTransport_cost());
                ps.setDouble(3, material.getQuality_coefficient());
                ps.executeUpdate();
                material.setComponent_id(component.get().getComponent_id());

                return Optional.of(material);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    protected Material mapResultSetToMaterial(ResultSet rs) throws SQLException {
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
            // cant return a Material object if the component_type is LABOR
            return null;
        }
    }

}
