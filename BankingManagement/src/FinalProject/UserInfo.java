package FinalProject;

import java.util.Date;

public class UserInfo
{
	String firstName;
	String lastName;
	String email;
	String phoneNo;
	String SIN;
	String address;
	String occupation;
	String sex;
	
	Date birthdate;

	public UserInfo(String firstName, String lastName, String email, String phoneNo, String SIN, String address,
			String occupation, String sex, Date birthdate)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNo = phoneNo;
		this.SIN = SIN;
		this.address = address;
		this.occupation = occupation;
		this.sex = sex;
		this.birthdate = birthdate;
	}

	public String toString()
	{
		return  firstName + "\n" + lastName + "\n" + email + "\n"
				+ phoneNo + "\n" + SIN + "\n" + address + "\n" + occupation + "\n" + sex
				+ "\n" + birthdate;
	}
	}
