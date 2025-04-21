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
