package FinalProject;

public class ChequingAccount extends UserAccount
{
	public ChequingAccount(UserInfo y)
	{
		super(y);
		setMinBalance(100);
		setWithdrawalLimit(50, 4000);
		setBalance(100);
	}
	
	public ChequingAccount(String an, String pin,double balance, UserInfo y)
	{
		this(y);
		super.setAccountNo(an);
		super.setPIN(pin);
		super.setBalance(balance);
	}

	void setMinBalance(double a)
	{
		minBalance=a;
	}

	void setWithdrawalLimit(double l,double h)
	{
		minWithdrawal=l;
		maxWithdrawal=h;
	}
	
	int getAccountType()
	{
		return UserAccount.CHEQUING_ACCOUNT;
	}
}



