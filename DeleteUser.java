package banker;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings({ "serial", "unused" })
public class DeleteUser extends JFrame {
	
	//this is where all the content of stuff will be.
	private JPanel contentPanel;
	
	//we will use array list to store all the user's data.
	private ArrayList<String[]> userData = new ArrayList<>();
	
	//we will import the csv file with the path.
	private final String CSV_FILE = "src/user.csv";
	//THIS WILL BE THE HEADER FOR OUR CSV FILE EVERYTIME.
	private final String header = "username, password, firstName, lastName, accountNumber, checkingAmount, savingAmount";
	
	//now we will set up the window design for deleting users.
	public DeleteUser(){
		
		setTitle("Delete User Management:"); //we set the title of the window frame
		setSize(600,500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);//close on the x button
		setLayout(new BorderLayout()); //set the layout as borderlayout.
		setLocationRelativeTo(null); //the window will pop up in the middle.
		
		
		//we have two panel for this window the 
		//-----this top one or the center one will be where the content will be filled.
		contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); //we set the layout in box layout and in y axis or vertically
		JScrollPane scrollPane = new JScrollPane(contentPanel); //we make the content in the panel scrollable by adding scrollpane method.
		add(scrollPane, BorderLayout.CENTER);
		
		//the bottom panel will have dashboard button and logout button to logout.
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton dashboardButton = new JButton("Dashboard");
		JButton logoutButton = new JButton("Logout");
		
		//logout button will return to the Online Banking System login. 
		logoutButton.addActionListener(e -> {
			//----------------hey friends this is where the code for the welcome window will appear.
			//if the file or the class for the welcome window is Welcome please write
			//------new Window();
			dispose();
			//new Window;
		});
		
		//and for the dashboard it will have like an option pane that will say returning to dashboard.
		dashboardButton.addActionListener(e -> {
			//closes this window
			dispose();
			
			//show the message that says returning to dashboard.
        JOptionPane.showMessageDialog(this, "Returning to Dashboard...");
        //open the banker dashboard once more.
        new BankerDashboard();
        
        });
		
		//now we add the buttons to the panels.
		bottomPanel.add(dashboardButton);
		bottomPanel.add(logoutButton);
		//now add the panel to south of the winodw layout which is borderlayout;
		add(bottomPanel, BorderLayout.SOUTH);
		
		//load the CSV
		loadCSV();
		
		setVisible(true);

		
	}
	

	private void loadCSV() {
	    userData.clear();
	    contentPanel.removeAll();

	    try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
	        String line;

	        br.readLine();  // Skip the first header line

	        while ((line = br.readLine()) != null) {
	            line = line.trim();
	            if (line.isEmpty() || line.equals(header)) {
	                continue;  // Skip empty lines or duplicate headers
	            }
	            String[] values = line.split(",");
	            userData.add(values);
	            addUserRow(values);
	        }

	        revalidate();
	        repaint();
	    } catch (IOException e) {
	        JOptionPane.showMessageDialog(this, "Error loading user data: " + e.getMessage());
	    }
	}
	
	
	   private void addUserRow(String[] values) {
	        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));  // Horizontal row

	        // For each value (e.g., John, Doe, Admin), create a read-only text field
	        for (String value : values) {
	            JTextField textField = new JTextField(value, 10);
	            textField.setEditable(false);   // Make it read-only
	            rowPanel.add(textField);
	        }

	        // Create Delete button for this row
	        JButton deleteButton = new JButton("Delete");

	        // Define what happens when Delete is clicked
	        deleteButton.addActionListener(e -> {
	            // Show confirmation dialog before deleting
	            int confirm = JOptionPane.showConfirmDialog(
	                this,
	                "Are you sure you want to delete this user?",
	                "Confirm Deletion",
	                JOptionPane.YES_NO_OPTION
	            );

	            // If user clicks "Yes" it will contentPanle pane will show up.
	            if (confirm == JOptionPane.YES_OPTION) {
	                int index = contentPanel.getComponentZOrder(rowPanel);  // Find row index
	                contentPanel.remove(rowPanel);                          // Remove from GUI
	                contentPanel.revalidate();
	                contentPanel.repaint();

	                userData.remove(index);   //this method will remove the user from the list.
	                updateCSV();               // we call the updateCSV method that will rewrite the new information.
	            }
	        });

	        rowPanel.add(deleteButton);    // Add Delete button to the row
	        contentPanel.add(rowPanel);    // Add the row to the main panel
	    }

	    //now we rewrite inot the CSV file because we made change to it with deleting a user.
	   private void updateCSV() {
		    try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE))) {
		        bw.write(header);      // Always write header first
		        bw.newLine();

		        for (String[] user : userData) {
		            bw.write(String.join(",", user));
		            bw.newLine();
		        }
		    } catch (IOException e) {
		        JOptionPane.showMessageDialog(this, "Error updating CSV: " + e.getMessage());
		    }
		}

	
	
	
}
