package Entity;

public class Project {
    private int project_id;
    private String project_name;
    private double profit_margin;
    private double total_cost;
    private String project_status;
    private double surface_area;
    private int client_id;

    public Project(
            String project_name,
            double profit_margin, double total_cost,
            String project_status, double surface_area,
            int client_id
    ) {

        this.project_name = project_name;
        this.profit_margin = profit_margin;
        this.total_cost = total_cost;
        this.project_status = project_status;
        this.surface_area = surface_area;
        this.client_id = client_id;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public double getProfit_margin() {
        return profit_margin;
    }

    public void setProfit_margin(double profit_margin) {
        this.profit_margin = profit_margin;
    }

    public double getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(double total_cost) {
        this.total_cost = total_cost;
    }

    public String getProject_status() {
        return project_status;
    }

    public void setProject_status(String project_status) {
        this.project_status = project_status;
    }

    public double getSurface_area() {
        return surface_area;
    }

    public void setSurface_area(double surface_area) {
        this.surface_area = surface_area;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    @Override
    public String toString() {
        return "Project{" +
                "project_id=" + project_id +
                ", project_name='" + project_name + '\'' +
                ", profit_margin=" + profit_margin +
                ", total_cost=" + total_cost +
                ", project_status='" + project_status + '\'' +
                ", surface_area=" + surface_area +
                ", client_id=" + client_id +
                '}';
    }


}
