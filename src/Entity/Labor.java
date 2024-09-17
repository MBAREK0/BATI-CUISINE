package Entity;

public class Labor extends Component{
    private double hourly_rate;
    private double hours_worked;
    private double productivity_factor;

    public Labor(String name, double unit_cost, double quantity, double vat_rate, int project_id, double hourly_rate, double hours_worked, double productivity_factor) {
        super(name, unit_cost, quantity, MaterialOrLabor.LABOR, vat_rate, project_id);
        this.hourly_rate = hourly_rate;
        this.hours_worked = hours_worked;
        this.productivity_factor = productivity_factor;
    }

    public double getHourly_rate() {
        return hourly_rate;
    }

    public void setHourly_rate(double hourly_rate) {
        this.hourly_rate = hourly_rate;
    }

    public double getHours_worked() {
        return hours_worked;
    }

    public void setHours_worked(double hours_worked) {
        this.hours_worked = hours_worked;
    }

    public double getProductivity_factor() {
        return productivity_factor;
    }

    public void setProductivity_factor(double productivity_factor) {
        this.productivity_factor = productivity_factor;
    }

    public Double calculateCost() {
        return (hourly_rate * hours_worked) * productivity_factor * getQuantity() * (1 + (getVat_rate()/100)) ;
    }

    @Override
    public String toString() {
        return getName() + ": " +  String.format("%.2f", calculateCost()) + " Dh (Number of workers: " + getQuantity() + ", unit cost: " + getUnit_cost() + ")";
    }


}

