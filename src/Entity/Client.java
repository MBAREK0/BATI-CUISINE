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
        // ANSI escape code for yellow text
        String yellow = "\033[0;33m";
        String reset = "\033[0m";

        // Calculate the maximum widths for each column dynamically
        int nameWidth = Math.max("Name".length(), name.length());
        int addressWidth = Math.max("Address".length(), address.length());
        int phoneWidth = Math.max("Phone".length(), phone.length());
        int professionalWidth = Math.max("Professional".length(), is_professional ? "Yes".length() : "No".length());

        // Build the table
        StringBuilder sb = new StringBuilder();
        sb.append(yellow); // Start with yellow color

        // Top border
        sb.append("+").append("-".repeat(nameWidth + 2))
                .append("+").append("-".repeat(addressWidth + 2))
                .append("+").append("-".repeat(phoneWidth + 2))
                .append("+").append("-".repeat(professionalWidth + 2))
                .append("+\n");

        // Header row
        sb.append(String.format("| %-"+nameWidth+"s | %-"+addressWidth+"s | %-"+phoneWidth+"s | %-"+professionalWidth+"s |\n", "Name", "Address", "Phone", "Professional"));

        // Middle border
        sb.append("+").append("-".repeat(nameWidth + 2))
                .append("+").append("-".repeat(addressWidth + 2))
                .append("+").append("-".repeat(phoneWidth + 2))
                .append("+").append("-".repeat(professionalWidth + 2))
                .append("+\n");

        // Data row
        sb.append(String.format("| %-"+nameWidth+"s | %-"+addressWidth+"s | %-"+phoneWidth+"s | %-"+professionalWidth+"s |\n", name, address, phone, is_professional ? "Yes" : "No"));

        // Bottom border
        sb.append("+").append("-".repeat(nameWidth + 2))
                .append("+").append("-".repeat(addressWidth + 2))
                .append("+").append("-".repeat(phoneWidth + 2))
                .append("+").append("-".repeat(professionalWidth + 2))
                .append("+\n");

        sb.append(reset); // Reset the color

        return sb.toString();
    }



}
