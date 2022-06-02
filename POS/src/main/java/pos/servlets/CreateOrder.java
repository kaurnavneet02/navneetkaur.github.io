package pos.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import pos.db.models.Order;
import pos.db.models.OrderProduct;
import pos.servlets.messages.Message;

/**
 * Servlet implementation class CreateOrder
 */
@WebServlet("/CreateOrder/")
public class CreateOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String jsonString = request.getReader().readLine();
		
		Gson gson = new Gson();
		
		OrderProduct op[] = gson.fromJson(jsonString, OrderProduct[].class);
		Date d = new Date();
		
		
		new Order(op[0].getRestaurant(), new ArrayList<OrderProduct>(Arrays.asList(op))).save();
		
		String message = new Gson().toJson(new Message("OK","Restaurant Order Created"));
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(message);
		out.flush();
	}

}
