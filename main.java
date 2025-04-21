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
