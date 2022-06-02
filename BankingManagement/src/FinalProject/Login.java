package FinalProject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

// Create Class Name Login
// Extends from JFrame
// Implements ActionListener

public class Login extends JFrame implements ActionListener
{
	
	JPasswordField PF = new JPasswordField("",20);
	
	JTextField DT = new JTextField("",20);
	
	JButton B1 = new JButton("Login"), B2= new JButton("Create a new Account");
	
	// creating the object of DataStore and getting instance 
    // By using getInstance() method
	
	DataStore ds = DataStore.getInstance();
	JPanel NY = new JPanel();
	JPanel NY2= new JPanel();
	JPanel NY3= new JPanel();
	JPanel NY4= new JPanel();
	
	// Create a class constructor for the Main class
	public Login()
	{
      //The WindowListener interface defines methods that handle most window events, such as the events for opening and closing the window
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we)
			{
				ds.saveData();
				System.exit(0);
			}
		});
		ds.printAccounts();
		this.setSize(300, 150);
	    this.setVisible(true);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    
	    
	    B1.addActionListener(this);
	    B2.addActionListener(this);
	    
	    //button color
	    B1.setBackground(Color.BLACK);
	    B1.setForeground(Color.WHITE);
	    
	   
	    B2.setBackground(Color.BLACK);
	    B2.setForeground(Color.WHITE);
	   
	    this.add(NY);
	    
	    NY.add(NY2);
	    NY.add(NY3);
	    NY.add(NY4);

	    
	    NY2.add(new JLabel("Debit Card "));
	    NY2.add(DT);
	    
	    NY3.add(new JLabel("Password : "));
	    NY3.add(PF);
	    
	    NY4.add(B1);
	    NY4.add(B2);
	    
	    // to adding my new content into a JPanel
	    this.revalidate();
	   
	}
	
	public static void main(String args[])
	{
		DataStore.getInstance().loadData();
		new Login();
	}

	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("Login"))
		{
			UserAccount ac;
			if((ac=ds.getAccount(DT.getText(), PF.getText()))!=null)
			{
				this.dispose();
				new AccountDetail(ac);
			}
			else
			{
				JOptionPane.showMessageDialog(this,"Debit Card & Password didn't Match!","Login Failed",JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(e.getActionCommand().equals("Create a new Account"))
		{
			this.dispose();
			new SignUpPage();
		}
	}
}

