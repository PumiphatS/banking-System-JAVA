import javax.swing.*;
import java.awt.*;

public class UserDashboard extends JFrame {
    private User user;
    private UserManager manager;

    private JLabel spendingLabel;
    private JLabel savingsLabel;

    public UserDashboard(User user, UserManager manager) {
        this.user = user;
        this.manager = manager;

        setTitle("Welcome, " + user.getUsername());
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        spendingLabel = new JLabel();
        savingsLabel = new JLabel();
        updateBalances();

        JPanel top = new JPanel(new GridLayout(2, 1));
        top.add(spendingLabel);
        top.add(savingsLabel);

        JPanel center = new JPanel(new GridLayout(3, 2, 10, 10));
        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton ownTransferBtn = new JButton("Own Transfer");
        JButton userTransferBtn = new JButton("Transfer to User");
        JButton exitBtn = new JButton("Exit");

        depositBtn.addActionListener(e -> {
            String[] options = {"Spending", "Savings"};
            String acc = (String) JOptionPane.showInputDialog(this, "Account Type", "Deposit",
                    JOptionPane.PLAIN_MESSAGE, null, options, "Spending");
            String amtStr = JOptionPane.showInputDialog("Amount:");
            try {
                double amt = Double.parseDouble(amtStr);
                if (acc.equals("Spending")) user.getSpending().deposit(amt);
                else user.getSavings().deposit(amt);
                updateBalances();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount.");
            }
        });

        withdrawBtn.addActionListener(e -> {
            String[] options = {"Spending", "Savings"};
            String acc = (String) JOptionPane.showInputDialog(this, "Account Type", "Withdraw",
                    JOptionPane.PLAIN_MESSAGE, null, options, "Spending");
            String amtStr = JOptionPane.showInputDialog("Amount:");
            try {
                double amt = Double.parseDouble(amtStr);
                boolean success = acc.equals("Spending") ?
                        user.getSpending().withdraw(amt) : user.getSavings().withdraw(amt);
                if (!success) JOptionPane.showMessageDialog(this, "Insufficient funds.");
                updateBalances();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount.");
            }
        });

        ownTransferBtn.addActionListener(e -> {
            String[] options = {"Spending", "Savings"};
            String from = (String) JOptionPane.showInputDialog(this, "Transfer from:", "Own Transfer",
                    JOptionPane.PLAIN_MESSAGE, null, options, "Spending");
            String to = from.equals("Spending") ? "Savings" : "Spending";
            String amtStr = JOptionPane.showInputDialog("Amount:");
            try {
                double amt = Double.parseDouble(amtStr);
                boolean success = user.transferBetweenOwn(from, to, amt);
                if (!success) JOptionPane.showMessageDialog(this, "Transfer failed.");
                updateBalances();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount.");
            }
        });

        userTransferBtn.addActionListener(e -> {
            String recipient = JOptionPane.showInputDialog("Send to username:");
            User toUser = manager.getUser(recipient);
            if (toUser == null || toUser == user) {
                JOptionPane.showMessageDialog(this, "Invalid user.");
                return;
            }
            String[] options = {"Spending", "Savings"};
            String toType = (String) JOptionPane.showInputDialog(this, "Their Account:", "Transfer",
                    JOptionPane.PLAIN_MESSAGE, null, options, "Spending");
            String amtStr = JOptionPane.showInputDialog("Amount:");
            try {
                double amt = Double.parseDouble(amtStr);
                boolean success = user.transferTo(toUser, toType, amt);
                if (!success) JOptionPane.showMessageDialog(this, "Transfer failed.");
                updateBalances();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount.");
            }
        });

        exitBtn.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this, "Exit dashboard?", "Exit",
                    JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                dispose();
                System.exit(0);
            }
        });

        center.add(depositBtn);
        center.add(withdrawBtn);
        center.add(ownTransferBtn);
        center.add(userTransferBtn);
        center.add(exitBtn); // 5th spot
        center.add(new JLabel("")); // placeholder to balance grid

        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        setVisible(true);
    }

    private void updateBalances() {
        spendingLabel.setText("Spending: $" + String.format("%.2f", user.getSpending().getBalance()));
        savingsLabel.setText("Savings: $" + String.format("%.2f", user.getSavings().getBalance()));
    }
}
