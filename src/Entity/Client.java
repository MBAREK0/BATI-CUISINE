package Entity;

public class Client {
    private int client_id;
    private String name;
    private String address;
    private String phone;
    private boolean is_professional;

    public Client(String name, String address, String phone, boolean is_professional) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.is_professional = is_professional;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isIs_professional() {
        return is_professional;
    }

    public void setIs_professional(boolean is_professional) {
        this.is_professional = is_professional;
    }

    @Override
    public String toString() {
        return "Client{" + "client_id=" + client_id + ", name=" + name + ", address=" + address + ", phone=" + phone + ", is_professional=" + is_professional + '}';
    }

}
