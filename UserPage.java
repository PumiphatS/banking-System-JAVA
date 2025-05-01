import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserPage {
    // user balances from csv
    private static Map<String, User> userBalances = new HashMap<>();

    // currently selected user (manually set below)
    private static User currentUser = null;

    public static void main(String[] args) {
        
        loadUserBalances("user.csv"); // load balances from CSV

        if (!userBalances.isEmpty()) {
            currentUser = userBalances.values().iterator().next(); // get the first User object
        }
        

        if (currentUser != null) {
            showMainMenu();
        } else {
            JOptionPane.showMessageDialog(null, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // load balances from user.csv (columns: username [0], password [1], checking [5], savings [6])
    private static void loadUserBalances(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] column = line.split(",");
                if (column.length >= 7) {
                    String username = column[0];
                    double checkingBalance = Double.parseDouble(column[5]);
                    double savingsBalance = Double.parseDouble(column[6]);
                    userBalances.put(username, new User(username, checkingBalance, savingsBalance));
                }
            }
        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Failed to load user.csv", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // user banking menu
    private static void showMainMenu() {
        JFrame frame = new JFrame("Banking Options"); // window title
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel greeting = new JLabel("What would you like to do?");
        greeting.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel checkingLabel = new JLabel("Checking Balance: $" + currentUser.checkingBalance); // from column 6
        JLabel savingsLabel = new JLabel("Savings Balance: $" + currentUser.savingsBalance); // from column 7
        checkingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        savingsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // buttons
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton transferButton = new JButton("Transfer");

        // button sizes
        Dimension buttonSize = new Dimension(120, 30);
        depositButton.setPreferredSize(buttonSize);
        withdrawButton.setPreferredSize(buttonSize);
        transferButton.setPreferredSize(buttonSize);

        // button placement (center)
        JPanel depositPanel = new JPanel();
        JPanel withdrawPanel = new JPanel();
        JPanel transferPanel = new JPanel();

        depositPanel.add(depositButton);
        withdrawPanel.add(withdrawButton);
        transferPanel.add(transferButton);

        // button functionality
        depositButton.addActionListener(e -> {
            frame.dispose(); // close current window
            //new DepositPage(currentUser); // call deposit class to open window
        });
        withdrawButton.addActionListener(e -> {
            frame.dispose();
            //new WithdrawPage(currentUser); //rename
        });
        transferButton.addActionListener(e -> {
            frame.dispose();
            new TransferHandler(currentUser);
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

        frame.add(panel);
        frame.setVisible(true);
    }
}
