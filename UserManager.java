import java.io.*;
import java.util.*;

public class UserManager {
    private Map<String, User> users = new HashMap<>();

    public void addUser(String username) {
        if (!users.containsKey(username)) {
            users.put(username, new User(username));
        }
    }

    public void removeUser(String username) {
        users.remove(username);
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public Set<String> getUsernames() {
        return users.keySet();
    }

    public void saveToCSV(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("username,spendingBalance,savingsBalance");
            for (User user : users.values()) {
                writer.printf("%s,%.2f,%.2f%n",
                    user.getUsername(),
                    user.getSpending().getBalance(),
                    user.getSavings().getBalance()
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromCSV(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String username = parts[0];
                    double spending = Double.parseDouble(parts[1]);
                    double savings = Double.parseDouble(parts[2]);

                    User user = new User(username);
                    user.getSpending().deposit(spending);
                    user.getSavings().deposit(savings);
                    users.put(username, user);
                }
            }
        } catch (IOException e) {
            System.out.println("No existing user data found. Starting fresh.");
        }
    }
}
