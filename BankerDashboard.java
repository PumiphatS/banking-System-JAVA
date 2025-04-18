import javax.swing.*;
import java.awt.*;


//this will be the calss that has the gui attributes or the dashboard window.
public class BankerDashboard {
	//the frame and the label for welcome.
    JFrame dashboard;
    JLabel welcomeLabel;
    
    //the label for each action that banker can do
    JLabel createUser;
    JLabel deleteUser;
    
    //button for user to interact with
    JButton createUserButton;
    JButton deleteUserButton;
    JButton logoutButton;
    
    //the three panel 
    JPanel welcomePanel;
    JPanel centerPanel;
    JPanel logoutPanel;
   
    

    public BankerDashboard() {
    	//create the frame.
        dashboard = new JFrame("Welcome Banker!");
        
        dashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dashboard.setSize(1000,500);
        dashboard.setLocationRelativeTo(null);//this will set the window towards the middle or the center of the screen.
        
        
        //----North border or the top panel that says welcome banker;
        welcomePanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); //automatic flow layout i think. and set it to center.
        welcomeLabel = new JLabel("Welcome Banker"); //create the label 
        welcomePanel.add(welcomeLabel); // add the label to the panel
        dashboard.add(welcomePanel,BorderLayout.NORTH); //finally add the panel to the dashboard
        
        
        //----Center panel where the banker can perform actions.
        centerPanel = new JPanel(new GridLayout(2,2,10,10));
        //row1
        createUser = new JLabel("Create User");
        createUserButton = new JButton("Enter");
        //row 2
        deleteUser = new JLabel("Delete User");
        deleteUserButton = new JButton("Enter");
        
        //now add the componenets into the panel.
        centerPanel.add(createUser);
        centerPanel.add(createUserButton);
        centerPanel.add(deleteUser);
        centerPanel.add(deleteUserButton);
        dashboard.add(centerPanel,BorderLayout.CENTER);
        
        
        
        
        //------South border of the panel.
        logoutPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoutButton = new JButton("Logout");
        logoutPanel.add(logoutButton);
        dashboard.add(logoutPanel,BorderLayout.SOUTH);
        
        //now make it visibl.
        dashboard.setVisible(true);
        
        
        
        
    }
}
