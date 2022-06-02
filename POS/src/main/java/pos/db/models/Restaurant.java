package pos.db.models;

import pos.db.Database;

public class Restaurant 
{

	private String name;
	private int id;
	private String email;
	private String password;
	
	
	private Restaurant()
	{
		name = "";
		id = 0;
	}
	public Restaurant(String name, String email, String password) {
		super();
		this.id = -1;
		this.name = name;
		this.email = email;
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void save()
	{
		if(id==-1)
		{
			Database.db().insert(this);
		}
		else
		{
			//Database.db().update(this);	
		}
	}
	
	
	public static Restaurant exists(String email, String password)
	{
		
		
		Restaurant r = new Restaurant();
		
		r.setEmail(email);
		r.setPassword(password);
		
		
		
		
		
		return Database.db().exists(r);
	}
	
	
}