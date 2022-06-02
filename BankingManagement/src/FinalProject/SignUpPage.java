package FinalProject;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

//Create Class Name SignUpPage
//Extends from JFrame
//Implements ActionListener

public class SignUpPage extends JFrame implements ActionListener
{
	
	// creating the object of DataStore and getting instance 
    // By using getInstance() method
	
	DataStore ds = DataStore.getInstance();
	
	JTextField firstName = new JTextField("",20);
	JTextField lastName = new JTextField("",20);
	JTextField email = new JTextField("",20);
	JTextField ContactNo = new JTextField("",20);
	JTextField SIN = new JTextField("",20);
	JTextField address = new JTextField("",20);
	JTextField occupation = new JTextField("",20);

	JRadioButton maleB  = new JRadioButton("male");
	JRadioButton femaleB  = new JRadioButton("female");
	ButtonGroup bg = new ButtonGroup();
	
	JComboBox<String> accountType = new JComboBox<String>();
	JComboBox<Integer> day = new JComboBox<Integer>();
	JComboBox<Integer> month = new JComboBox<Integer>();
	JComboBox<Integer> year = new JComboBox<Integer>();
	
	
	
	JPanel y = new JPanel();
	JPanel y1 = new JPanel();
	JPanel y2 = new JPanel();
	JPanel y3 = new JPanel();
	JPanel y4 = new JPanel();
	JPanel y5 = new JPanel();
	JPanel y6 = new JPanel();
	JPanel y7 = new JPanel();
	JPanel y8 = new JPanel();
	JPanel y9 = new JPanel();
	JPanel y10 = new JPanel();
	
	JButton backB= new JButton("Back");
	JButton signUpB = new JButton("Create Account");
	
	public SignUpPage()
	{
		//The WindowListener interface defines methods that handle most window events, such as the events for opening and closing the window
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we)
			{
				ds.saveData();
				System.exit(0);
			}
		});
		
		this.setSize(500,720);
	    this.setVisible(true);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLayout(new GridLayout(1, 2, 10,0));
	    
	    this.add(y);
	    
	    y.setLayout(new GridLayout(0, 1,10,20));
	    y.add(new JLabel("Fill up the Form below", JLabel.CENTER));
	    
	    accountType.addItem("Saving Account");
	    accountType.addItem("Chequing Account");
	    y.add(accountType);
	    
	    y.add(y1);
	    // grid layout is a layout manager to assign rectangular grid shape
	    y1.setLayout(new GridLayout(0,2,5,0));
	    y1.add(new JLabel("First name"));
	    y1.add(firstName);
	    y.add(y9);
	    y9.setLayout(new GridLayout(0, 2,5,0));
	    y9.add(new JLabel("Last name"));
	    y9.add(lastName);
	    
	    
	    y.add(y2);
	    bg.add(maleB);
	    bg.add(femaleB);
	    y2.add(maleB);
	    y2.add(femaleB);
	    
	    y.add(y3);
	    generateDate();
	    y3.add(new JLabel("Birth Date: "));
	    y3.add(day);
	    y3.add(month);
	    y3.add(year);

	    y.add(y4);
	    y4.setLayout(new GridLayout(0, 2,5,0));
	    y4.add(new JLabel("E-Mail: "));
	    y4.add(email);
	    
	    y.add(y5);
	    y5.setLayout(new GridLayout(0, 2,5,0));
	    y5.add(new JLabel("Phone No: "));
	    y5.add(ContactNo);
	    
	    y.add(y6);
	    y6.setLayout(new GridLayout(0, 2,5,0));
	    y6.add(new JLabel("SIN No: "));
	    y6.add(SIN);
	    
	    y.add(y7);
	    y7.setLayout(new GridLayout(0, 2,5,0));
	    y7.add(new JLabel("Occupation: "));
	    y7.add(occupation);
	    
	    y.add(y8);
	    y8.setLayout(new GridLayout(0, 2,5,0));
	    y8.add(new JLabel("Full Address: "));
	    y8.add(address);
	    
	    y.add(y10);
	    y10.setLayout(new GridLayout(0, 2, 5,0));
	    backB.addActionListener(this);
	    y10.add(backB);
	    signUpB.addActionListener(this);
	    y10.add(signUpB);
	}

	void generateDate()
	{
		for(int i=1;i<=31;i++)
		{
			day.addItem(i);
		}
		for(int i=1;i<=12;i++)
		{
			month.addItem(i);
		}
		for(int i=1900;i<=2018;i++)
		{
			year.addItem(i);
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		System.out.println("hm");
		if(e.getActionCommand().equals("Back"))
		{
			this.dispose();
			new Login();
		}
		else if(e.getActionCommand().equals("Create Account"))
		{
			if(isFormFilled())
			{
				createNewAccount();
				this.dispose();
				new Login();
			}
			else
			{
				JOptionPane.showMessageDialog(null,"Please fill up all the fields","Failed", JOptionPane.ERROR_MESSAGE );
			}
		}
	}
	
	boolean isFormFilled()
	{
		if(firstName.getText().isEmpty() || lastName.getText().isEmpty() 
				|| email.getText().isEmpty() || ContactNo.getText().isEmpty()
				|| SIN.getText().isEmpty() || address.getText().isEmpty() || occupation.getText().isEmpty())
		{
			return false;
		}
		if(!maleB.isSelected() && !femaleB.isSelected())
		{
			return false;
		}
		return true;
	}
	
	void createNewAccount()
	{
		String sex;
		Date d;
		UserAccount ac;
		
		if(maleB.isSelected())
			sex="male";
		else
			sex="female";
		
		d=new Date((int)year.getSelectedItem(),(int)month.getSelectedItem(),(int)day.getSelectedItem());
		
		UserInfo u= new UserInfo(firstName.getText(), lastName.getText(), email.getText(),
				ContactNo.getText(), SIN.getText(), address.getText(), occupation.getText(), 
				sex, d);
		
		if(accountType.getSelectedItem().equals("Savings Account"))
		{
			ac= new SavingAccount(u);
			ds.addNewAccount(ac);
		}
		else
		{
			ac = new ChequingAccount(u);
			ds.addNewAccount(ac);
		}
		JOptionPane.showMessageDialog(null,"Debit Card:"+ac.getAccuntNo()+"\nPIN:"+ac.getPIN(),"Success", JOptionPane.INFORMATION_MESSAGE );
		
		
	}
}


