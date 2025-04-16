import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        UserManager manager = new UserManager();
        manager.loadFromCSV("users.csv");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            manager.saveToCSV("users.csv");
        }));

        String[] options = {"Admin", "User"};
        String choice = (String) JOptionPane.showInputDialog(null, "Login as:", "Login",
                JOptionPane.PLAIN_MESSAGE, null, options, "User");

        if ("Admin".equals(choice)) {
            String adminPass = JOptionPane.showInputDialog("Admin password:");
            if (!"admin".equals(adminPass)) {
                JOptionPane.showMessageDialog(null, "Incorrect password.");
                return;
            }

            while (true) {
                String[] adminOps = {"Add User", "Remove User", "Exit"};
                String op = (String) JOptionPane.showInputDialog(null, "Admin Menu", "Admin",
                        JOptionPane.PLAIN_MESSAGE, null, adminOps, "Add User");

                if ("Add User".equals(op)) {
                    String name = JOptionPane.showInputDialog("New username:");
                    manager.addUser(name);
                } else if ("Remove User".equals(op)) {
                    String name = JOptionPane.showInputDialog("Username to remove:");
                    manager.removeUser(name);
                } else break;
            }
        } else {
            String uname = JOptionPane.showInputDialog("Enter username:");
            User user = manager.getUser(uname);
            if (user == null) {
                JOptionPane.showMessageDialog(null, "User not found.");
                return;
            }
            new UserDashboard(user, manager);
        }
    }
}
