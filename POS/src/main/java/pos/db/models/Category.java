package pos.db.models;

import java.util.List;

import pos.db.Database;

public class Category {
	
	private int category;
	private int restaurant;
	private String name;
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public int getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(int restaurant) {
		this.restaurant = restaurant;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	private Category()
	{
		category = -1;
		
	}
	public Category(int restaurant, String name)
	{
		this();
		this.restaurant = restaurant;
		this.name = name;
	}
	
	public void save()
	{
		if(category==-1)
		{
			Database.db().insert(this);
		}
		else
		{
			//Database.db().update(this);
		}
	}
	
	
	public static List<Category> getAll(int restaurant)
	{
		return Database.db().getCategories(restaurant);
	}

}
