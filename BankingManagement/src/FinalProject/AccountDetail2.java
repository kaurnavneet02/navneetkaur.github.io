package FinalProject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class AccountDetail2 extends JFrame implements ActionListener
{
	DataStore ds = DataStore.getInstance();
	UserAccount ac;
	
	JLabel welcomeText = new JLabel();
	
	JTextField AccountNo = new JTextField("",15);
	JTextField Amount = new JTextField("",10);
	
	JButton Transfer= new JButton("Transfer Money");
	JButton Withdraw= new JButton("Withdraw Money");
	JButton Deposit= new JButton("Deposit Money");
	JButton BalanceC= new JButton("Balance Check");
	JButton PayBill= new JButton("Pay Bill");
	JButton AccountDetail= new JButton("Account Detail");
	JButton ChangeSetting= new JButton("Change Setting");
	JButton Logout = new JButton("Log Out");
	
	JButton TSB = new JButton("Transfer");
	JButton WSB = new JButton("Withdraw");
	JButton DSB = new JButton("Deposit");
	JButton PayBillSB = new JButton("Pay");
	
	JPanel left = new JPanel();
	JPanel right = new JPanel();
	JPanel top = new JPanel();
	JPanel bottom = new JPanel();
	JPanel centerr = new JPanel();
	JPanel center = new JPanel();
	
	public AccountDetail2(UserAccount ac)
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
		this.setSize(700, 500);
	    this.setVisible(true);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    //this.setLayout(new FlowLayout());
	    
	    TSB.addActionListener(this);
	    WSB.addActionListener(this);
	    PayBillSB.addActionListener(this);
	    DSB.addActionListener(this);
	    
	    
	    this.add(left,BorderLayout.WEST);
	    this.add(right,BorderLayout.EAST);
	    this.add(top,BorderLayout.NORTH);
	    this.add(bottom,BorderLayout.SOUTH);
	    this.add(centerr,BorderLayout.CENTER);
	    
	    
	    welcomeText.setText("Welcome, " + ac.user.firstName + " " + ac.user.lastName);
	    top.add(welcomeText);
	    
	    left.setLayout(new GridLayout(9, 1,5,10));
	    Transfer.addActionListener(this);
	    left.add(Transfer);
	    Withdraw.addActionListener(this);
	    left.add(Withdraw);
	    Deposit.addActionListener(this);
	    left.add(Deposit);
	    BalanceC.addActionListener(this);
	    left.add(BalanceC);
	    PayBill.addActionListener(this);
	    left.add(PayBill);
	    
	    right.setLayout(new GridLayout(9, 1,5,10));
	    right.add(AccountDetail);
	    right.add(ChangeSetting);
	    
	    centerr.setLayout(new FlowLayout(FlowLayout.CENTER));
	    centerr.setBorder(new EmptyBorder(90, 10, 50, 10));
	    centerr.add(center);
	    //center.setBackground(new Color(4,55,4));
	    //centerr.setBackground(new Color(54,55,40));
	    
	    Logout.addActionListener(this);
	    bottom.add(Logout);
	    
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
		else if(e.getActionCommand().equals("Log Out"))
		{
			this.dispose();
			new Login();
		}
		
	}
	
	void generateTransferPanel()
	{
		panelClear();
		center.add(new JLabel("Enter Account: "));
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
		center.add(PayBillSB);
		this.revalidate();
	}
	
	
	void panelClear()
	{
		center.removeAll();
		center.setLayout(new GridLayout(0, 2, 5, 10));
		AccountNo.setText("");
		Amount.setText("");
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
}

