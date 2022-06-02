package pos.db.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pos.db.Database;


public class Order
{
	private int orderid;
	private int restaurant;
	private String datetime;
	private List<OrderProduct> products;
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	public int getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(int restaurant) {
		this.restaurant = restaurant;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public List<OrderProduct> getProducts() {
		return products;
	}
	public void setProducts(List<OrderProduct> products) {
		this.products = products;
	}
	
	
	public Order(int restaurant, List<OrderProduct> l)
	{
		this.restaurant = restaurant;
		orderid=-1;
		products = l;
		Date date = Calendar.getInstance().getTime();  
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
		String strDate = dateFormat.format(date);  
		datetime = strDate;
	}
	
	
	public void save()
	{
		Database.db().insert(this);
	}
	
	
		public static List<Order> getAll(int restaurant)
		{
			return Database.db().getOrders(restaurant);
		}
	}
