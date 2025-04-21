// Main.java
import models.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Garage garage = new Garage();
        LoginSystem loginSystem = new LoginSystem();
        ServiceManager serviceManager = new ServiceManager();

        while (true) {
            clearScreen();
            System.out.println("===== Welcome to Ma Babar Doa Garage =====");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.print("Choose option: ");
            int opt = getValidChoice(scanner);

            String role = "", name = "", userPhone = "";
            boolean loggedIn = false;

            if (opt == 1) {
                System.out.print("Phone Number: ");
                String phone = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();

                String login = loginSystem.login(phone, password);
                if (login == null) {
                    System.out.println("Invalid login.");
                    pressToContinue(scanner);
                    continue;
                }

                String[] parts = login.split(":");
                role = parts[0];
                name = parts[1];
                userPhone = phone;
                loggedIn = true;

            } else if (opt == 2) {
                loginSystem.registerNewUser(scanner);
                pressToContinue(scanner);
                continue;
            } else {
                System.out.println("Invalid option.");
                pressToContinue(scanner);
                continue;
            }

            if (loggedIn) {
                clearScreen();
                System.out.println("Hi " + name + ", Welcome to Ma Babar Doa Garage!");

                int choice;
                do {
                    System.out.println("\n===== " + role.toUpperCase() + " MENU =====");
                    System.out.println("1. View Price Chart");

                    if (role.equals("admin")) {
                        System.out.println("2. Add Service");
                        System.out.println("3. Delete Service");
                        System.out.println("4. Check Work Updates");
                        System.out.println("0. Logout");
                    } else {
                        System.out.println("2. Add Vehicle for Service");
                        System.out.println("3. View Your Vehicle History");
                        System.out.println("4. View Vehicle Service Status");
                        System.out.println("5. Search Vehicle");
                        System.out.println("0. Logout");
                    }

                    System.out.print("Choose: ");
                    choice = getValidChoice(scanner);
                    clearScreen();

                    if (role.equals("admin")) {
                        switch (choice) {
                            case 1 -> serviceManager.viewServiceChart();
                            case 2 -> serviceManager.addService(scanner);
                            case 3 -> serviceManager.deleteService(scanner);
                            case 4 -> serviceManager.checkWorkUpdates();
                            case 0 -> {
                                System.out.println("Logging out...");
                                pressToContinue(scanner);
                            }
                            default -> System.out.println("Invalid.");
                        }
                    } else {
                        switch (choice) {
                            case 1 -> serviceManager.viewServiceChart();
                            case 2 -> garage.addVehicle(scanner, serviceManager, userPhone);
                            case 3 -> garage.viewServiceHistory(userPhone);
                            case 4 -> garage.viewServiceStatus(userPhone);
                            case 5 -> garage.searchVehicle(scanner);
                            case 0 -> {
                                System.out.println("Logging out...");
                                pressToContinue(scanner);
                            }
                            default -> System.out.println("Invalid.");
                        }
                    }

                    if (choice != 0) {
                        pressToContinue(scanner);
                        clearScreen();
                    }

                } while (choice != 0);
            }
        }
    }

    public static void pressToContinue(Scanner sc) {
        System.out.println("\nPress Enter to return to menu...");
        sc.nextLine();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static int getValidChoice(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Enter a number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }
} 

// models/LoginSystem.java
package models;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LoginSystem {
    private final Map<String, String> users;

    public LoginSystem() {
        users = new HashMap<>();
        users.put("01711111111", "admin:Admin Bhai:admin123");
    }

    public void registerNewUser(Scanner scanner) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Phone Number: ");
        String phone = scanner.nextLine();

        if (users.containsKey(phone)) {
            System.out.println("User already exists!");
            return;
        }

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Are you signing up as 'admin' or 'user'? ");
        String role = scanner.nextLine().toLowerCase();

        if (!role.equals("admin") && !role.equals("user")) {
            System.out.println("Invalid role. Sign-up failed.");
            return;
        }

        String userData = role + ":" + name + ":" + password;
        users.put(phone, userData);
        System.out.println("Sign-up successful!");
    }

    public String login(String phone, String password) {
        if (!users.containsKey(phone)) return null;
        String data = users.get(phone);
        String[] parts = data.split(":");
        if (parts.length == 3 && parts[2].equals(password)) {
            return parts[0] + ":" + parts[1];
        }
        return null;
    }
}

// models/ServiceManager.java
package models;
import java.util.*;

public class ServiceManager {
    private final List<Service> services;

    public ServiceManager() {
        services = new ArrayList<>();
        services.add(new Service("Oil Change", 500));
        services.add(new Service("Engine Tune-up", 1200));
        services.add(new Service("Wheel Alignment", 800));
    }

    public void addService(Scanner scanner) {
        System.out.print("Enter service name: ");
        String name = scanner.nextLine();

        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        services.add(new Service(name, price));
        System.out.println("Service added successfully.");
    }

    public void deleteService(Scanner scanner) {
        viewServiceChart();
        System.out.print("Enter service name to delete: ");
        String name = scanner.nextLine();

        boolean removed = services.removeIf(s -> s.getName().equalsIgnoreCase(name));
        if (removed) {
            System.out.println("Service removed.");
        } else {
            System.out.println("Service not found.");
        }
    }

    public void viewServiceChart() {
        if (services.isEmpty()) {
            System.out.println("No services available.");
            return;
        }
        String line = "+----------------------+------------+";
        String format = "| %-20s | %-10s |%n";
        System.out.println(line);
        System.out.printf(format, "Service Name", "Price (৳)");
        System.out.println(line);
        for (Service s : services) {
            System.out.printf(format, s.getName(), s.getCost());
        }
        System.out.println(line);
    }

    public void checkWorkUpdates() {
        System.out.println("All systems operational.");
        System.out.println("3 vehicles in queue.");
        System.out.println("2 services completed today.");
        System.out.println("1 urgent task scheduled.");
    }

    public List<Service> getServices() {
        return services;
    }
}

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

// models/Service.java
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
