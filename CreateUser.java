
package banker;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.FileWriter;
import java.io.*;
import java.util.Scanner;

@SuppressWarnings({ "serial", "unused" })
public class CreateUser extends JFrame {
	
	//properties of the gui
	JFrame createUser;
	JLabel userName, passWord, firstName, lastName, accountNumber,checkingInitialBalance, savingInitialBalance, createUserLabel, homepageLabel;
	
	JButton addUserButton, logoutButton, backToDashboardButton;
	JPanel welcomePanel;
	JPanel actionPanel;
	JPanel bottomPanel;
	
	// Text fields as class variables
    private JTextField userNameTextField, passWordTextField, firstNameTextField, lastNameTextField, accountNumberTextField, checkingInitialBalanceTextField, savingInitialBalanceTextField;
    //the csv file will be stored in a string variable.
    private static final String csvFile = "src/user.csv";
	
	
	
	
	//constructor
	public CreateUser() {
		
		setTitle("Create New User");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        
        
        setLayout(new BorderLayout());
        
        //----the north border that include the wecome user panel.
        JPanel welcomePanel = new JPanel();
        welcomePanel.add(new JLabel("Input Information To Create New User"));
        add(welcomePanel, BorderLayout.NORTH);
        
        
        
        //---the middle part of the border
        actionPanel = new JPanel();
        actionPanel.setLayout(new GridLayout(7,2,3,3)); //setting the layout for the middle panel.
        userName = new JLabel("Enter Username:");
        actionPanel.add(userName);
        userNameTextField = new JTextField();
        actionPanel.add(userNameTextField);
        
        //password
        passWord = new JLabel("Enter Password:");
        actionPanel.add(passWord);
        passWordTextField = new JTextField();
        actionPanel.add(passWordTextField);
        
        //first name
        firstName = new JLabel("Enter First Name:");
        actionPanel.add(firstName);
        firstNameTextField = new JTextField();
        actionPanel.add(firstNameTextField);
        
     // Add Last Name
        lastName = new JLabel("Enter Last Name:");
        lastNameTextField = new JTextField();
        actionPanel.add(lastName);
        actionPanel.add(lastNameTextField);
        
        accountNumber = new JLabel("enter Account Number (8 digits):");
        actionPanel.add(accountNumber);
        accountNumberTextField = new JTextField();
        actionPanel.add(accountNumberTextField);
        
        
        
        checkingInitialBalance = new JLabel("Enter Initial checking Amount:");
        actionPanel.add(checkingInitialBalance);
        checkingInitialBalanceTextField = new JTextField();
        actionPanel.add(checkingInitialBalanceTextField);
        
        savingInitialBalance = new JLabel("Enter Initial Saving Amount");
        actionPanel.add(savingInitialBalance);
        savingInitialBalanceTextField = new JTextField();
        actionPanel.add(savingInitialBalanceTextField);
        add(actionPanel, BorderLayout.CENTER);
        
        
        //----now the bottom part whcih will have logout and addUser;
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        logoutButton = new JButton("Logout");
        logoutButton.setPreferredSize(new java.awt.Dimension(100, 50));
        bottomPanel.add(logoutButton);
        
        backToDashboardButton = new JButton("Dashboard");
        backToDashboardButton.setPreferredSize(new java.awt.Dimension(100, 50));
        bottomPanel.add(backToDashboardButton);
        
        addUserButton = new JButton("Create User");
        addUserButton.setPreferredSize(new java.awt.Dimension(100, 50));
        bottomPanel.add(addUserButton);
        
        add(bottomPanel,BorderLayout.SOUTH);
        
        
        //create methods for adding user to csv.
        backToDashboardButton.addActionListener(e -> {
        	dispose();
        	new BankerDashboard();
        });
        //create action listener for button logout to dispose the window and go back to lgoin page.
        logoutButton.addActionListener(e -> {
        	dispose();
        	JOptionPane.showMessageDialog(this, "Logged out Successfully!.");
        	
        });
        
        //call the method that will impose the validation when the create user button is pressed.
        addUserButton.addActionListener(e -> validateAndSaveUser());
        
        setVisible(true);
 
	}
	// Method to validate and save user info
    private void validateAndSaveUser() {
        String username = userNameTextField.getText().trim();
        String password = passWordTextField.getText().trim();
        String firstName = firstNameTextField.getText().trim();
        String lastName = lastNameTextField.getText().trim();
        String accountNum = accountNumberTextField.getText().trim();
        String checking = checkingInitialBalanceTextField.getText().trim();
        String saving = savingInitialBalanceTextField.getText().trim();
        
        //validation.
        if (username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() ||
        	    accountNum.isEmpty() || checking.isEmpty() || saving.isEmpty()) {
        	    JOptionPane.showMessageDialog(this, "All fields must be filled!", "Validation Error", JOptionPane.ERROR_MESSAGE);
        	    return;
        	}
        if (!accountNum.matches("\\d{8}")) {
            JOptionPane.showMessageDialog(this, "Account Number must be exactly 8 digits!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidDouble(checking) || !isValidDouble(saving)) {
            JOptionPane.showMessageDialog(this, "Checking and Saving amounts must be valid numbers!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
     // // Check for duplicate account number
        if (isDuplicateAccountNumber(accountNum)) {
            JOptionPane.showMessageDialog(this, "Account Number already exists!", "Duplicate Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Format amounts this will format the amount saving and checking to be 2 deciamls.
        String formattedChecking = String.format("%.2f", Double.parseDouble(checking));
        String formattedSaving = String.format("%.2f", Double.parseDouble(saving));

        // Append to CSV
        try (FileWriter writer = new FileWriter(csvFile, true)) {
            writer.append(username).append(",")
                  .append(password).append(",")
                  .append(firstName).append(",")
                  .append(lastName).append(",")
                  .append(accountNum).append(",")
                  .append(formattedChecking).append(",")
                  .append(formattedSaving).append("\n");

            JOptionPane.showMessageDialog(this, "User created successfully!"); 
            clearFields();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving user: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Check if account number already exists in CSV
    private boolean isDuplicateAccountNumber(String accountNum) {
        try (Scanner scanner = new Scanner(new File(csvFile))) {
            scanner.nextLine(); // Skip header
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                if (data.length >= 5 && data[4].equals(accountNum)) {
                    return true;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading CSV: " + e.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    
    //this method validates if its double.
    private boolean isValidDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    
    //this method clears all the textfield.
    private void clearFields() {
        userNameTextField.setText("");
        passWordTextField.setText("");
        firstNameTextField.setText("");
        lastNameTextField.setText("");
        accountNumberTextField.setText("");
        checkingInitialBalanceTextField.setText("");
        savingInitialBalanceTextField.setText("");
    }

}
