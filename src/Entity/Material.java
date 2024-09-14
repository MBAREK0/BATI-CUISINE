package Entity;



public class Material extends Component {
    private double transport_cost;
    private double quality_coefficient;

    public Material(String name, double unit_cost, double quantity, double vat_rate, int project_id, double transport_cost, double quality_coefficient) {
        super(name, unit_cost, quantity, MaterialOrLabor.MATERIAL, vat_rate, project_id);
        this.transport_cost = transport_cost;
        this.quality_coefficient = quality_coefficient;
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
}
