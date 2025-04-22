import javax.swing.*;
import java.awt.*;

public class UserLoginGUI {

    // sample username and password
    private static final String VALID_USERNAME = "user123";
    private static final String VALID_PASSWORD = "pass123";

    // sample balance
    private static double checkingBalance = 1000.00;
    private static double savingsBalance = 500.00;

    public static void main(String[] args) {
        showLoginScreen();
    }


    private static void showLoginScreen() {
        JFrame frame = new JFrame("Account Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 180);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        JButton loginButton = new JButton("Login");

        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(new JLabel());
        panel.add(loginButton);

        frame.add(panel);
        frame.setVisible(true);

        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            if (username.equals(VALID_USERNAME) && password.equals(VALID_PASSWORD)) {
                frame.dispose();
                showMainMenu();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
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

        JLabel checkingLabel = new JLabel("Checking Balance: $" + checkingBalance);
        JLabel savingsLabel = new JLabel("Savings Balance: $" + savingsBalance);
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

        // link to peers functionality
        depositButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Deposit here"));
        withdrawButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Withdraw here"));
        transferButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Transfer here"));

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
