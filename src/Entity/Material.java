package Entity;



public class Material extends Component {
    private double transport_cost;
    private double quality_coefficient;
    private int component_id;

    public Material(String name, double unit_cost, double quantity, double vat_rate, int project_id, double transport_cost, double quality_coefficient) {
        super(name, unit_cost, quantity, MaterialOrLabor.MATERIAL, vat_rate, project_id);
        this.transport_cost = transport_cost;
        this.quality_coefficient = quality_coefficient;
    }
    @Override
    public int getComponent_id() {
        return component_id;
    }

    @Override
    public void setComponent_id(int component_id) {
        this.component_id = component_id;
    }

    public double getTransport_cost() {
        return transport_cost;
    }

    public void setTransport_cost(double transport_cost) {
        this.transport_cost = transport_cost;
    }

    public double getQuality_coefficient() {
        return quality_coefficient;
    }

    public void setQuality_coefficient(double quality_coefficient) {
        this.quality_coefficient = quality_coefficient;
    }

    public Double calculateCost() {
        Double totalCost =  getUnit_cost() * getQuantity() * this.quality_coefficient  + this.transport_cost ;
        return totalCost *  (1 + ( getVat_rate() / 100 ));
    }

    @Override
    public String toString() {
        return getName() + ": " + calculateCost() + " Dh (Quantity: " + getQuantity() + ", unit cost: " + getUnit_cost() + ")";

    }
}
