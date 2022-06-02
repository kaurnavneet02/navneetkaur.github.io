package pos.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import pos.db.models.Category;
import pos.db.models.Order;
import pos.db.models.OrderProduct;
import pos.db.models.Product;
import pos.db.models.Restaurant;


// Singular Class. 

public class Database 
{
	
	private static Database db;
	private  Connection connection;
	final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	final String JDBC_URL = "jdbc:mysql://localhost/test";

	final String USERNAME = "root";
	final String PASSWORD = "";
	

	private Database()
	{
		

		try
		{
			Class.forName(JDBC_DRIVER);
			
			
			
				
		}
		catch(Exception e)
		{
			connection = null;
		}
	}
	
	
	private void startConnection()
	{
		try {
			connection = DriverManager.getConnection(JDBC_URL,USERNAME, PASSWORD );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void closeConnection()
	{
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static Database db()
	{
		if(db==null)
		{
			db= new Database();
		}
		return db;
	}
	
	
	
	
	//Create Tables 
	
	public  boolean createTables()
	{
		
		
		startConnection();
		
		
		String restaurantQuery = "CREATE TABLE IF NOT EXISTS `restaurant` ( "+
				"`restaurant` INT(10) NOT NULL AUTO_INCREMENT, " +
				"`name` VARCHAR(50), " +
				"`email` VARCHAR(255)," +
				"`password` VARCHAR(25)," +
			"	UNIQUE KEY `email_unique` (`restaurant`,`email`) USING HASH " +
		"	) ENGINE=InnoDB;" ;
		
		
		try {
			Statement st = connection.createStatement();
			
			System.out.println(st.executeUpdate(restaurantQuery));
			
			
			
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		closeConnection();
		return false;
	}
	
	// Start Inserting Values To The Tables. 
	
	//		Restaurant
	
	public void insert(Restaurant r)
	{
		startConnection();
		try {
			Statement st = connection.createStatement();
			String insertQuery = "INSERT INTO restaurant (NAME, EMAIL, PASSWORd) VALUES ('"+r.getName()+"','"+r.getEmail()+"','"+r.getPassword()+"')";
			int res = st.executeUpdate(insertQuery);
			System.out.println(res);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeConnection();
	}
	
	public void insert(Category c)
	{
		startConnection();
		try {
			Statement st = connection.createStatement();
			String insertQuery = "INSERT INTO categories (restaurant, name) VALUES ('"+c.getRestaurant()+"','"+c.getName()+"')";
			int res = st.executeUpdate(insertQuery);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR: "+e.getMessage());
			//e.printStackTrace();
		}
		
		closeConnection();
	}
	
	
	
	
	public void insert(Product p)
	{
		startConnection();
		try {
			Statement st = connection.createStatement();
			String insertQuery = "INSERT INTO product (category,  name, price, image) VALUES ('"+p.getCategory()+"','"+p.getName()+"','"+p.getPrice()+"','"+p.getImage()+"')";
			int res = st.executeUpdate(insertQuery);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		closeConnection();
	}
	
	
	public void insert(Order o)
	{
		
		int restaurant = o.getProducts().get(0).getRestaurant();
		startConnection();
		try {
			Statement st = connection.createStatement();
			
			String orderQuery = "insert into orders (restaurant, datetime) values ('"+restaurant+"', '"+o.getDatetime()+"')";
			st.executeUpdate(orderQuery, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = st.getGeneratedKeys();
			rs.next();
			int orderid = (int) rs.getLong(1);
			
			List<OrderProduct> l = o.getProducts();
			
			Iterator<OrderProduct> i = l.iterator();
			
			while(i.hasNext())
			{
				OrderProduct p = i.next();
				String productQuery = "INSERT INTO ORDERPRODUCT  (ORDERID, PRODUCTNAME, PRICE, QTY, IMAGE) VALUES ('"+orderid+"','"+p.getName()+"','"+p.getPrice()+"','"+p.getQty()+"','"+p.getImage()+"')";
				System.out.println(productQuery);
				st.executeUpdate(productQuery);
				
			}
//			for(int i=0;i<size();i++)
//			{
//				
//			}
//			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		closeConnection();
	}
	
	
	
	
	
		
	
	public Restaurant exists(Restaurant r)
	{
		startConnection();
		try {
			Statement st = connection.createStatement();
			String checkQuery = "SELECT * FROM RESTAURANT WHERE EMAIL='"+r.getEmail()+"' AND PASSWORD='"+r.getPassword()+"'";
			
			ResultSet res = st.executeQuery(checkQuery);
			if(res.next())
			{
				int id = res.getInt("restaurant");
				String name = res.getString("name");
				
				
				
				r.setId(id);
				r.setName(name);
				return r;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeConnection();
		return null;
	}
	
	
	// End inserting values to the tables
	
	
	
	public void delete(Category c)
	{
		startConnection();
		try {
			Statement st = connection.createStatement();
			String checkQuery = "DELETE FROM categories WHERE category='"+c.getCategory()+"'";
			
			int res = st.executeUpdate(checkQuery);
			System.out.println(res);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeConnection();
	}
	
	

	public void delete(Product c)
	{
		startConnection();
		try {
			Statement st = connection.createStatement();
			String checkQuery = "DELETE FROM product WHERE product='"+c.getProduct()+"'";
			
			int res = st.executeUpdate(checkQuery);
			System.out.println(res);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeConnection();
	}
	
	
	public List<Category> getCategories(int restaurant)
	{
		List<Category> list = new ArrayList<Category>();
		
		startConnection();
		
		try {
			
			Statement st = connection.createStatement();
			
			String searchQuery = "SELECT * FROM CATEGORIES WHERE restaurant='"+restaurant+"'";
			ResultSet res = st.executeQuery(searchQuery);
			
			while(res.next())
			{
				Category c = new Category(res.getInt("restaurant"),res.getString("name"));
				c.setCategory(res.getInt("category"));
				
				list.add(c);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		closeConnection();
		
		return list;
	}
	
	
	
	

	public List<Product> getProducts(int category)
	{
		List<Product> list = new ArrayList<Product>();
		
		startConnection();
		
		try {
			
			Statement st = connection.createStatement();
			
			String searchQuery = "SELECT * FROM product WHERE category='"+category+"'";
			ResultSet res = st.executeQuery(searchQuery);
			
			while(res.next())
			{
				Product c = new Product(res.getInt("category"),res.getString("name"), res.getDouble("price"), res.getString("image"));
				c.setProduct(res.getInt("product"));
				
				list.add(c);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		closeConnection();
		
		return list;
	}
	
	

	

	

	public List<Order> getOrders(int restaurant)
	{
		List<Order> list = new ArrayList<Order>();
		
		startConnection();
		
		try {
			
			Statement st = connection.createStatement();
			
			String searchQuery = "SELECT * FROM orders WHERE restaurant='"+restaurant+"'";
			ResultSet res = st.executeQuery(searchQuery);
			
			while(res.next())
			{
				
				Order o = new Order(restaurant, new ArrayList<>());
				o.setDatetime(res.getString("datetime"));
				o.setOrderid(res.getInt("orderid"));
				
				
				//getting products of that order
				
				String productSearchQuery = "select * from orderproduct where orderid = '"+o.getOrderid()+"'";
				
				Statement st2 = connection.createStatement();
				
				ResultSet res2 = st2.executeQuery(productSearchQuery);
				
				List<OrderProduct> op = new ArrayList<>();
				while(res2.next())
				{
					OrderProduct p = new OrderProduct();
					p.setImage(res2.getString("image"));
					p.setName(res2.getString("productname"));
					p.setPrice(res2.getDouble("price"));
					p.setQty(res2.getInt("qty"));
					
					op.add(p);
					
				}
				
				o.setProducts(op);
				
				
				list.add(o);
				
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		closeConnection();
		
		return list;
	}
	
	

	
	public Product getProduct(int product)
	{
		
		startConnection();
		
		try {
			
			Statement st = connection.createStatement();
			
			String searchQuery = "SELECT * FROM product WHERE product='"+product+"'";
			ResultSet res = st.executeQuery(searchQuery);
			
			res.next();
			Product p = new Product(1, "",0.0, "");
			return p;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		closeConnection();
		return null;
	}
	
	

}
