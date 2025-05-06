import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserPage {
    // user balances from csv
    private static Map<String, User> userBalances = new HashMap<>();

    // currently selected user
    private static User currentUser = null;

    public static void main(String[] args) {
        // test with hardcoded username
        launchForUser("user"); // replace with a real username from user.csv
    }

    public static void launchForUser(String selectedUsername) {
        loadUserBalances("src/user.csv");
        currentUser = userBalances.get(selectedUsername);

        if (currentUser != null) {
            showMainMenu();
        } else {
            JOptionPane.showMessageDialog(null, "User not found: " + selectedUsername, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ✅ Updated to match 7-column CSV
    private static void loadUserBalances(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] column = line.split(",", -1); // -1 to preserve empty fields
                if (column.length >= 7) {
                    String username = column[0];
                    String password = column[1];
                    String field3 = column[2];
                    String field4 = column[3];
                    String field5 = column[4];
                    double checkingBalance = Double.parseDouble(column[5]);
                    double savingsBalance = Double.parseDouble(column[6]);

                    userBalances.put(username, new User(username, password, field3, field4, field5, checkingBalance, savingsBalance));
                }
            }
        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Failed to load user.csv", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void showMainMenu() {
        JFrame frame = new JFrame("Banking Options");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel greeting = new JLabel("What would you like to do?");
        greeting.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel checkingLabel = new JLabel("Checking Balance: $" + currentUser.getCheckingBalance());
        JLabel savingsLabel = new JLabel("Savings Balance: $" + currentUser.getSavingsBalance());
        checkingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        savingsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton transferButton = new JButton("Transfer");
        JButton logoutButton = new JButton("Logout");

        Dimension buttonSize = new Dimension(120, 30);
        depositButton.setPreferredSize(buttonSize);
        withdrawButton.setPreferredSize(buttonSize);
        transferButton.setPreferredSize(buttonSize);
        logoutButton.setPreferredSize(buttonSize);

        JPanel depositPanel = new JPanel();
        JPanel withdrawPanel = new JPanel();
        JPanel transferPanel = new JPanel();
        JPanel logoutPanel = new JPanel();

        depositPanel.add(depositButton);
        withdrawPanel.add(withdrawButton);
        transferPanel.add(transferButton);
        logoutPanel.add(logoutButton);

        depositButton.addActionListener(e -> {
            frame.dispose();
            new DepositPanel(currentUser);
        });
        withdrawButton.addActionListener(e -> {
            frame.dispose();
            new WithdrawPanel(currentUser);
        });
        transferButton.addActionListener(e -> {
            frame.dispose();
            new TransferHandler(currentUser);
        });
        logoutButton.addActionListener(e -> {
            frame.dispose();
            JOptionPane.showMessageDialog(null, "Logged out successfully.");
            new UserLogin().setVisible(true);
        });

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(greeting);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(checkingLabel);
        panel.add(savingsLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(depositPanel);
        panel.add(withdrawPanel);
        panel.add(transferPanel);
        panel.add(logoutPanel);

        frame.add(panel);
        frame.setVisible(true);
    }

    // save user balances to csv
    public static void saveUserBalances(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("username,password,field3,field4,field5,checking,savings");

            for (User user : userBalances.values()) {
                writer.printf("%s,%s,%s,%s,%s,%.2f,%.2f%n",
                        user.getUsername(),
                        user.getPassword(),
                        user.getField3(),
                        user.getField4(),
                        user.getField5(),
                        user.getCheckingBalance(),
                        user.getSavingsBalance());
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to save user.csv", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
