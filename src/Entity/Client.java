package Entity;

public class Client {
    private int client_id;
    private String name;
    private String address;
    private String phone;
    private boolean is_professional;
    private Double discount_percentage;

    public Client(String name, String address, String phone, boolean is_professional,Double discount_percentage) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.is_professional = is_professional;
        this.discount_percentage = discount_percentage;
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

    public Double getDiscount_percentage() {
        return discount_percentage;
    }

    public void setDiscount_percentage(Double discount_percentage) {
        this.discount_percentage = discount_percentage;
    }

    @Override
    public String toString() {
        // ANSI escape code for yellow text
        String yellow = "\033[0;33m";
        String reset = "\033[0m";

        // Instance variables
        String name = this.name;
        String address = this.address;
        String phone = this.phone;
        boolean is_professional = this.is_professional;
        double discount_percentage = this.discount_percentage;

        // Calculate maximum width needed for each column
        int nameWidth = Math.max(8, name.length());
        int addressWidth = Math.max(12, address.length());
        int phoneWidth = Math.max(12, phone.length());
        int professionalWidth = Math.max(14, (is_professional ? "Yes" : "No").length());
        int discountWidth = Math.max(10, String.format("%.1f %%", discount_percentage).length());

        // Build the table
        StringBuilder sb = new StringBuilder();
        sb.append(yellow); // Start with yellow color

        // Top border
        sb.append("+").append("-".repeat(nameWidth + 2))
                .append("+").append("-".repeat(addressWidth + 2))
                .append("+").append("-".repeat(phoneWidth + 2))
                .append("+").append("-".repeat(professionalWidth + 2))
                .append("+").append("-".repeat(discountWidth + 2))
                .append("+\n");

        // Header row
        sb.append(String.format("| %-"+nameWidth+"s | %-"+addressWidth+"s | %-"+phoneWidth+"s | %-"+professionalWidth+"s | %-"+discountWidth+"s |\n",
                "Name", "Address", "Phone", "Professional", "Discount"));

        // Middle border
        sb.append("+").append("-".repeat(nameWidth + 2))
                .append("+").append("-".repeat(addressWidth + 2))
                .append("+").append("-".repeat(phoneWidth + 2))
                .append("+").append("-".repeat(professionalWidth + 2))
                .append("+").append("-".repeat(discountWidth + 2))
                .append("+\n");

        // Data row with one space after the percentage sign
        sb.append(String.format("| %-"+nameWidth+"s | %-"+addressWidth+"s | %-"+phoneWidth+"s | %-"+professionalWidth+"s | %"+(discountWidth -1)+".1f %%|\n",
                name, address, phone, is_professional ? "Yes" : "No", discount_percentage));

        // Bottom border
        sb.append("+").append("-".repeat(nameWidth + 2))
                .append("+").append("-".repeat(addressWidth + 2))
                .append("+").append("-".repeat(phoneWidth + 2))
                .append("+").append("-".repeat(professionalWidth + 2))
                .append("+").append("-".repeat(discountWidth + 2))
                .append("+\n");

        sb.append(reset); // Reset the color

        return sb.toString();
    }




}

