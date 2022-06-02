package FinalProject;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.text.DecimalFormat;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

//Create Class Name AccountDetail
//Extends from JFrame
//Implements ActionListener

public class AccountDetail extends JFrame implements ActionListener
{
	DataStore ds = DataStore.getInstance();
	UserAccount ac;
	
	JLabel welcomeText = new JLabel();
	
	JTextField AccountNo = new JTextField("",20);
	JTextField Amount = new JTextField("",10);
	
	JPasswordField OP= new JPasswordField("",20);
	JPasswordField NP= new JPasswordField("",20);
	JPasswordField renewp= new JPasswordField("",20);
	
	JTextField Cemail = new JTextField("",20);
	JTextField CContactNo = new JTextField("",20);
	JTextField CSIN = new JTextField("",20);
	JTextField Caddress = new JTextField("",20);
	JTextField Coccupation = new JTextField("",20);
	
	JButton TransferB= new JButton("Transfer Money");
	JButton WithdrawB= new JButton("Withdraw Money");
	JButton DepositB= new JButton("Deposit Money");
	JButton BalanceB= new JButton("Balance Check");
	JButton PayBillB= new JButton("Pay Bill");
	JButton UserDetailB= new JButton("User Detail");
	JButton ChangePinB= new JButton("Change PIN");
	JButton LogoutB = new JButton("Log Out");
	
	JButton TSB = new JButton("Transfer");
	JButton WSB = new JButton("Withdraw");
	JButton DSB = new JButton("Deposit");
	JButton PSB = new JButton("Pay");
	JButton PinB = new JButton("Submit");
	
	JPanel left = new JPanel();
	JPanel right = new JPanel();
	JPanel top = new JPanel();
	JPanel bottom = new JPanel();
	JPanel center = new JPanel();
	
	
	public AccountDetail(UserAccount ac)
	{
		this.ac=ac;
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we)
			{
				ds.saveData();
				System.exit(0);
			}
		});
		this.setTitle("Dashboard");
		this.setSize(700, 400);
	    this.setVisible(true);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    TSB.addActionListener(this);
	    WSB.addActionListener(this);
	    PSB.addActionListener(this);
	    DSB.addActionListener(this);
	    PinB.addActionListener(this);
	    
	    this.add(left,BorderLayout.WEST);
	    this.add(right,BorderLayout.EAST);
	    this.add(top,BorderLayout.NORTH);
	    this.add(bottom,BorderLayout.SOUTH);
	    this.add(center,BorderLayout.CENTER);
	    
	    
	    top.setBorder(new EmptyBorder(10, 0, 10, 0));
	    welcomeText.setText("Welcome, " + ac.user.firstName + " " + ac.user.lastName);
	    top.add(welcomeText);
	    
	    
	    left.setLayout(new GridLayout(7, 1,5,10));
	    TransferB.addActionListener(this);
	    left.add(TransferB);
	    WithdrawB.addActionListener(this);
	    left.add(WithdrawB);
	    DepositB.addActionListener(this);
	    left.add(DepositB);
	    BalanceB.addActionListener(this);
	    left.add(BalanceB);
	    PayBillB.addActionListener(this);
	    left.add(PayBillB);
	    
	    right.setLayout(new GridLayout(7, 1,5,10));
	    UserDetailB.addActionListener(this);
	    right.add(UserDetailB);
	    ChangePinB.addActionListener(this);
	    right.add(ChangePinB);
	    
	    
	    
	    LogoutB.addActionListener(this);
	    bottom.setLayout(new FlowLayout(FlowLayout.RIGHT));
	    bottom.add(LogoutB);
	    
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("Transfer Money"))
		{
			generateTransferPanel();
		}
		else if(e.getActionCommand().equals("Withdraw Money"))
		{
			generateWithdrawalPanel();
		}
		else if(e.getActionCommand().equals("Deposit Money"))
		{
			generateDepositPanel();
		}
		else if(e.getActionCommand().equals("Balance Check"))
		{
			generateBalancePanel();
		}
		else if(e.getActionCommand().equals("Pay Bill"))
		{
			generatePayBillPanel();
		}
		else if(e.getActionCommand().equals("Transfer"))
		{
			transfer();
		}
		else if(e.getActionCommand().equals("Withdraw"))
		{
			withdraw();
		}
		else if(e.getActionCommand().equals("Deposit"))
		{
			deposit();
			
		}
		else if(e.getActionCommand().equals("Pay"))
		{
			payBill();
		}
		else if(e.getActionCommand().equals("User Detail"))
		{
			generateUserDetailPanel();
		}
		else if(e.getActionCommand().equals("Change PIN"))
		{
			generatePinPanel();
		}
		else if(e.getActionCommand().equals("Submit"))
		{
			changePIN();
		}
		else if(e.getActionCommand().equals("Log Out"))
		{
			this.dispose();
			new Login();
		}
		
	}
	
	void generateTransferPanel()
	{
		panelClear();
		center.add(new JLabel("Enter Account no: "));
		center.add(AccountNo);
		center.add(new JLabel("Enter Amount: "));
		center.add(Amount);
		center.add(new JLabel());
		center.add(TSB);
		this.revalidate();
	}
	
	void generateWithdrawalPanel()
	{
		panelClear();
		center.add(new JLabel("Enter Amount: "));
		center.add(Amount);
		center.add(new JLabel());
		center.add(WSB);
		this.revalidate();
	}
	
	void generateDepositPanel()
	{
		panelClear();
		center.add(new JLabel("Enter Amount: "));
		center.add(Amount);
		center.add(new JLabel());
		center.add(DSB);
		this.revalidate();
	}
	
	void generateBalancePanel()
	{
		panelClear();
		center.add(new JLabel("Current Balance: "));
		DecimalFormat df = new DecimalFormat("0.00");
		center.add(new JLabel(""+df.format(ac.getBalance())));
		center.add(new JLabel());
		center.add(new JLabel());
		this.revalidate();
	}
	
	void generatePayBillPanel()
	{
		panelClear();
		center.add(new JLabel("Enter ID/BillNo: "));
		center.add(AccountNo);
		center.add(new JLabel("Enter Amount: "));
		center.add(Amount);
		center.add(new JLabel());
		center.add(PSB);
		this.revalidate();
	}
	
	void generateUserDetailPanel()
	{
		panelClear();
		center.setBorder(new EmptyBorder(50, 50, 50,50));
		center.setLayout(new GridLayout(0, 2, 5, 10));
		center.add(new JLabel("Full Name:"));
		center.add(new JLabel(ac.user.firstName + " " + ac.user.lastName));
		center.add(new JLabel("Sex:"));
		center.add(new JLabel(ac.user.sex));
		center.add(new JLabel("Date of Birth:"));
		center.add(new JLabel(ac.user.birthdate.getDate()+"/"+ac.user.birthdate.getMonth()+"/"+ac.user.birthdate.getYear()));
		center.add(new JLabel("Email:"));
		center.add(new JLabel(ac.user.email));
		center.add(new JLabel("Phone No.:"));
		center.add(new JLabel(ac.user.phoneNo));
		center.add(new JLabel("SIN:"));
		center.add(new JLabel(ac.user.SIN));
		center.add(new JLabel("Address:"));
		center.add(new JLabel(ac.user.address));
		center.add(new JLabel("Occupation:"));
		center.add(new JLabel(ac.user.occupation));
		this.revalidate();
		
	}
	
	void generatePinPanel()
	{
		panelClear();
		center.setBorder(new EmptyBorder(80, 50, 70,50));
		center.setLayout(new GridLayout(4, 2, 5, 10));
		center.add(new JLabel("Old PIN:"));
		center.add(OP);
		center.add(new JLabel("Enter New PIN:"));
		center.add(NP);
		center.add(new JLabel("Re-Enter New PIN:"));
		center.add(renewp);
		center.add(new JLabel(" "));
		center.add(PinB);
		this.revalidate();
		
	}
	
	void panelClear()
	{
		center.removeAll();
		center.setBorder(new EmptyBorder(80, 50, 110,50));
		center.setLayout(new GridLayout(3, 2, 5, 10));
		AccountNo.setText("");
		Amount.setText("");
		OP.setText("");
		NP.setText("");
		renewp.setText("");
	}
	
	void payBill()
	{
		try
		{
			double amount=Double.parseDouble(Amount.getText());
			if(ac.payBill(amount))
			{
				JOptionPane.showMessageDialog(this,"Bill successfully Paid!","Success",JOptionPane.OK_OPTION);
			}
			else
			{
				JOptionPane.showMessageDialog(this,"You don't have enough Balance","Failed",JOptionPane.ERROR_MESSAGE);
			}
			
			
		}
		catch(NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(this,"Enter valid amount","Failed",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	void transfer()
	{
		try
		{
			double amount=Double.parseDouble(Amount.getText());
			
			UserAccount otherAccount;
			if((otherAccount=ds.getAccount(AccountNo.getText()))!=null)
			{
				if(ac.transferMoney(otherAccount, amount))
				{
					JOptionPane.showMessageDialog(this,"Successfully Transferred","Success",JOptionPane.OK_OPTION);
				}
				else
				{
					JOptionPane.showMessageDialog(this,"You don't have enough Balance","Failed",JOptionPane.ERROR_MESSAGE);
				}
			}
			else
			{
				JOptionPane.showMessageDialog(this,"Sorry, Given Account not Found","Failed",JOptionPane.ERROR_MESSAGE);
			}
			
			
		}
		catch(NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(this,"Enter valid amount","Failed",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	void withdraw()
	{
		try
		{
			double amount=Double.parseDouble(Amount.getText());
			int t=ac.withdrawMoney(amount);
			if(t==0)
				JOptionPane.showMessageDialog(this,"Successfully Withdrawn","Success",JOptionPane.OK_OPTION);
			else if(t==UserAccount.INSUFFICIENT_BALANCE)
				JOptionPane.showMessageDialog(this,"You don't have enough Balance","Failed",JOptionPane.ERROR_MESSAGE);
			else if(t==UserAccount.WITHDRAWAL_LIMIT_UNDER)
				JOptionPane.showMessageDialog(this,"Minimum withdrawal amount is: "+ ac.minWithdrawal,"Failed",JOptionPane.ERROR_MESSAGE);
			else if(t==UserAccount.WITHDRAWAL_LIMIT_OVER)
				JOptionPane.showMessageDialog(this,"Maximum Withdrawal amount is: " +ac.maxWithdrawal,"Failed",JOptionPane.ERROR_MESSAGE);
		}
		catch(NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(this,"Enter valid amount","Failed",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	void deposit()
	{
		try
		{
			double amount=Double.parseDouble(Amount.getText());
			ac.depositMoney(amount);
			JOptionPane.showMessageDialog(this,"Successfully Deposited","Success",JOptionPane.OK_OPTION);
		}
		catch(NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(this,"Enter valid amount","Failed",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	void changePIN()
	{
		if(OP.getText().isEmpty() || NP.getText().isEmpty() || renewp.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(this,"Please Fill all the Fields","Failed",JOptionPane.ERROR_MESSAGE);
		}
		else if(!OP.getText().equals(ac.getPIN()))
		{
			JOptionPane.showMessageDialog(this,"Wrong PIN Entered","Failed",JOptionPane.ERROR_MESSAGE);
		}
		else if(!NP.getText().equals(renewp.getText()))
		{
			JOptionPane.showMessageDialog(this,"New PINs doesn't Match","Failed",JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			ac.setPIN(NP.getText());
			JOptionPane.showMessageDialog(this,"PIN successfully changed","Success",JOptionPane.OK_OPTION);
		}
	}

	}


