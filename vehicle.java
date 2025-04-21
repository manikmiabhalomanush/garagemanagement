// models/Vehicle.java
package models;
import java.util.ArrayList;
import java.util.List;

public class Vehicle {
    private String owner;
    private String model;
    private String phone;
    private String status;
    private List<String> serviceHistory;

    public Vehicle(String owner, String model, String phone) {
        this.owner = owner;
        this.model = model;
        this.phone = phone;
        this.status = "Pending";
        this.serviceHistory = new ArrayList<>();
    }

    public void addService(String serviceName) {
        serviceHistory.add(serviceName);
    }

    public String getOwner() { return owner; }
    public String getModel() { return model; }
    public String getPhone() { return phone; }
    public List<String> getServiceHistory() { return serviceHistory; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
