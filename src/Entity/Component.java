package Entity;

public class Component {
    private int component_id;
    private String name;
    private double unit_cost;
    private double quantity;
    private MaterialOrLabor component_type;
    private double vat_rate;
    private int project_id;

    public Component(String name, double unit_cost, double quantity, MaterialOrLabor component_type, double vat_rate, int project_id) {
        this.name = name;
        this.unit_cost = unit_cost;
        this.quantity = quantity;
        this.component_type = component_type;
        this.vat_rate = vat_rate;
        this.project_id = project_id;
    }

    public int getComponent_id() {
        return component_id;
    }

    public void setComponent_id(int component_id) {
        this.component_id = component_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnit_cost() {
        return unit_cost;
    }

    public void setUnit_cost(double unit_cost) {
        this.unit_cost = unit_cost;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public MaterialOrLabor getComponent_type() {
        return component_type;
    }

    public void setComponent_type(MaterialOrLabor component_type) {
        this.component_type = component_type;
    }

    public double getVat_rate() {
        return vat_rate;
    }

    public void setVat_rate(double vat_rate) {
        this.vat_rate = vat_rate;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }
}
