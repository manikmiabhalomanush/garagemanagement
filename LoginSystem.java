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
