package Repository.implementation;

import Entity.Labor;
import Entity.MaterialOrLabor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LaborRepositoryImpl extends ComponentRepositoryImpl {

    @Override
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
