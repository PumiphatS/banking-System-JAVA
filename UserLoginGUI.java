import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class UserLoginGUI {

    private static List<User> userList = new ArrayList<>();
    private static User loggedInUser = null;
    private static double checkingBalance = 1000.00;
    private static double savingsBalance = 500.00;

    public static void main(String[] args) {
        loadUsersFromCSV("user.csv");
        showLoginScreen();
    }

    static class User {
        String username, password, firstName, lastName, accountNumber;

        public User(String username, String password, String firstName, String lastName, String accountNumber) {
            this.username = username;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
            this.accountNumber = accountNumber;
        }
    }

    // load csv
    private static void loadUsersFromCSV(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // skip header
                }
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    userList.add(new User(
                            parts[0].trim(), parts[1].trim(),
                            parts[2].trim(), parts[3].trim(),
                            parts[4].trim()));
                }
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "CSV file not found!\nMake sure 'user.csv' is in the project folder.", "File Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading CSV file.", "Read Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    // login screen
    private static void showLoginScreen() {
        JFrame frame = new JFrame("Bank Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        JButton loginButton = new JButton("Login");

        panel.add(userLabel); panel.add(userField);
        panel.add(passLabel); panel.add(passField);
        panel.add(new JLabel()); panel.add(loginButton);

        frame.add(panel);
        frame.setVisible(true);

        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            for (User user : userList) {
                if (user.username.equals(username) && user.password.equals(password)) {
                    loggedInUser = user;
                    frame.dispose();
                    showMainMenu();
                    return;
                }
            }
            JOptionPane.showMessageDialog(frame, "Invalid credentials.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        });
    }

    private static void showMainMenu() {
        JFrame frame = new JFrame("Banking Options");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel greeting = new JLabel("Welcome, " + loggedInUser.firstName + " " + loggedInUser.lastName);
        greeting.setAlignmentX(Component.CENTER_ALIGNMENT);
        greeting.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel accountInfo = new JLabel("Account Number: " + loggedInUser.accountNumber);
        accountInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel checkingLabel = new JLabel("Checking Balance: $" + String.format("%.2f", checkingBalance));
        JLabel savingsLabel = new JLabel("Savings Balance: $" + String.format("%.2f", savingsBalance));
        checkingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        savingsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton transferButton = new JButton("Transfer");

        JPanel depositPanel = new JPanel();
        JPanel withdrawPanel = new JPanel();
        JPanel transferPanel = new JPanel();

        depositPanel.add(depositButton);
        withdrawPanel.add(withdrawButton);
        transferPanel.add(transferButton);

        depositButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Deposit functionality here."));
        withdrawButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Withdraw functionality here."));
        transferButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Transfer functionality here."));

        panel.add(greeting);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(accountInfo);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(checkingLabel);
        panel.add(savingsLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(depositPanel);
        panel.add(withdrawPanel);
        panel.add(transferPanel);

        frame.add(panel);
        frame.setVisible(true);
    }
}
