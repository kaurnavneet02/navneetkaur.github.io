package pos.db.models;

import java.util.List;

import pos.db.Database;

import com.google.gson.*;

public class Product {
	
	private int product;
	private int category;
	private String name;
	private double price;
	
	private String image;
	public int getProduct() {
		return product;
	}
	public void setProduct(int product) {
		this.product = product;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	
	
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public void save()
	{
		if(product==-1)
		{
			Database.db().insert(this);
		}
		else
		{
//			Database.db().update(this);
		}
	}
	
	
	public Product(int category, String name, double price, String image) {
		
		super();
		this.category = category;
		this.name = name;
		this.price = price;
		this.image = image;
		this.product = -1;
		
		
	}
	
	
	public static List<Product> getAll(int category)
	{
		return Database.db().getProducts(category);
	}
	
	public static String getAll(int category, boolean json)
	{
		List<Product> p =  Database.db().getProducts(category);
		
		return new GsonBuilder().setPrettyPrinting().create().toJson(p);
	}

	
	public static Product getProduct(int product)
	{
		return Database.db().getProduct(product);
	}

}
