package FinalProject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DataStore
{
	private static DataStore instance;
	ArrayList<UserAccount> account = new ArrayList<UserAccount>();
	
	
	public static DataStore getInstance()
	{
		if(instance==null)
		{
			instance=new DataStore();
		}
		return instance;
	}
	
	UserAccount getAccount(String n)
	{
		for(int i=0;i<account.size();i++)
		{
			if(account.get(i).getAccuntNo().equals(n))
			{
				return account.get(i);
			}
		}
		
		return null;
	}
	
	UserAccount getAccount(String n, String p)
	{
		for(int i=0;i<account.size();i++)
		{
			if(account.get(i).getAccuntNo().equals(n) && account.get(i).getPIN().equals(p))
			{
				return account.get(i);
			}
		}
		
		return null;
	}
	
	void addNewAccount(UserAccount ac)
	{
		this.account.add(ac);
	}
	
	boolean isAccountNumberUnique(String n)
	{
		for(int i=0;i<account.size();i++)
		{
			if(account.get(i).getAccuntNo().equals(n))
			{
				return false;
			}
		}
		
		return true;
	}
	
	void saveData()
	{
		System.out.println("saved");
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("AccountList.txt")));
			for(int i=0;i<account.size();i++)
			{
				bw.write(String.valueOf(account.get(i)));
			}
			bw.close();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	void printAccounts()
	{
		for(int i=0;i<account.size();i++)
		{
			System.out.println(account.get(i).getAccuntNo() +" "+ account.get(i).getPIN());
		}
	}
	
	void loadData()
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(new File("AccountList.txt")));
			String type;
			DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss zzz yyyy");
			
			while((type=br.readLine()) != null)
			{
				UserAccount ac;
				
				if(type.equals(String.valueOf(UserAccount.SAVING_ACCOUNT)))
				{
					ac=new SavingAccount(br.readLine(),br.readLine(),Double.parseDouble(br.readLine()),
							new UserInfo(br.readLine(), br.readLine(), br.readLine(), br.readLine(), br.readLine(),
									br.readLine(), br.readLine(), br.readLine(), df.parse(br.readLine())));
					ac.isActivated=Boolean.getBoolean(br.readLine());
				}
				else
				{
					ac=new ChequingAccount(br.readLine(),br.readLine(),Double.parseDouble(br.readLine()),
							new UserInfo(br.readLine(), br.readLine(), br.readLine(), br.readLine(), br.readLine(),
									br.readLine(), br.readLine(), br.readLine(), df.parse(br.readLine())));
					ac.isActivated=Boolean.getBoolean(br.readLine());
				}
				addNewAccount(ac);
				
			}
			br.close();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (NumberFormatException e)
		{	
			e.printStackTrace();
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
	}
}
