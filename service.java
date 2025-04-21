package models;

public class Service {
    private String name;
    private double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() { return name; }
    public double getCost() { return cost; }
    public String toString() { return name + " - ৳" + cost; }
}

// models/Garage.java
package models;
import java.util.*;

public class Garage {
    private final List<Vehicle> vehicles;

    public Garage() {
        vehicles = new ArrayList<>();
    }

    public void addVehicle(Scanner scanner, ServiceManager serviceManager, String userPhone) {
        System.out.print("Enter your name: ");
        String owner = scanner.nextLine();

        System.out.print("Enter vehicle model: ");
        String model = scanner.nextLine();

        List<Service> availableServices = serviceManager.getServices();
        if (availableServices.isEmpty()) {
            System.out.println("No services available. Please contact the admin.");
            return;
        }

        System.out.println("Available Services:");
        for (int i = 0; i < availableServices.size(); i++) {
            System.out.println((i + 1) + ". " + availableServices.get(i));
        }

        System.out.print("Which service do you want? (Enter number): ");
        int serviceChoice = scanner.nextInt();
        scanner.nextLine();

        if (serviceChoice < 1 || serviceChoice > availableServices.size()) {
            System.out.println("Invalid choice. Vehicle not added.");
            return;
        }

        Service selectedService = availableServices.get(serviceChoice - 1);
        Vehicle v = new Vehicle(owner, model, userPhone);
        v.addService(selectedService.getName());
        vehicles.add(v);

        System.out.println("Vehicle added for service: " + selectedService.getName());
    }

    public void viewServiceHistory(String userPhone) {
        boolean found = false;
        for (Vehicle v : vehicles) {
            if (v.getPhone().equals(userPhone)) {
                found = true;
                System.out.println("Vehicle: " + v.getModel());
                System.out.println("Service History:");
                for (String s : v.getServiceHistory()) {
                    System.out.println(" - " + s);
                }
            }
        }
        if (!found) {
            System.out.println("No vehicles found under your account.");
        }
    }

    public void viewServiceStatus(String userPhone) {
        boolean found = false;
        System.out.println("Your Vehicle Service Status:");
        for (Vehicle v : vehicles) {
            if (v.getPhone().equals(userPhone)) {
                found = true;
                System.out.println("Vehicle: " + v.getModel());
                System.out.println("Status: " + v.getStatus());
                System.out.println("-----------------------------");
            }
        }
        if (!found) {
            System.out.println("No vehicles found under your account.");
        }
    }

    public void searchVehicle(Scanner scanner) {
        System.out.println("Search by:\n1. Phone Number\n2. Vehicle Model");
        System.out.print("Choose: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        boolean found = false;

        if (choice == 1) {
            System.out.print("Enter phone number: ");
            String phone = scanner.nextLine();
            for (Vehicle v : vehicles) {
                if (v.getPhone().equals(phone)) {
                    found = true;
                    System.out.println("Owner: " + v.getOwner());
                    System.out.println("Model: " + v.getModel());
                    System.out.println("Status: " + v.getStatus());
                }
            }
        } else if (choice == 2) {
            System.out.print("Enter vehicle model: ");
            String model = scanner.nextLine();
            for (Vehicle v : vehicles) {
                if (v.getModel().equalsIgnoreCase(model)) {
                    found = true;
                    System.out.println("Owner: " + v.getOwner());
                    System.out.println("Phone: " + v.getPhone());
                    System.out.println("Status: " + v.getStatus());
                }
            }
        } else {
            System.out.println("Invalid choice.");
            return;
        }

        if (!found) {
            System.out.println("No matching vehicles found.");
        }
    }
}
