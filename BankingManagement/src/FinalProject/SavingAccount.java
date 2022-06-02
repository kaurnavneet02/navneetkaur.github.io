package FinalProject;

public class SavingAccount extends UserAccount
{

	SavingAccount(UserInfo u)
	{
		super(u);
		setMinBalance(500);
		setWithdrawalLimit(50,5000);
		setBalance(500);
	}
	SavingAccount(String an, String pin, double balance, UserInfo u)
	{
		this(u);
		super.setAccountNo(an);
		super.setPIN(pin);
		super.setBalance(balance);
	}

	void setMinBalance(double a)
	{
		minBalance=a;
	}
	
	void setWithdrawalLimit(double l, double h)
	{
		minWithdrawal=l;
		maxWithdrawal=h;
	}
	int getAccountType()
	{
		return UserAccount.SAVING_ACCOUNT;
	}


}


