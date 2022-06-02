package pos.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import pos.db.models.Restaurant;
import pos.servlets.messages.Message;

/**
 * Servlet implementation class SignIn
 */
@WebServlet("/SignIn/")
public class SignIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignIn() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		
		Restaurant r = Restaurant.exists(request.getParameter("email"), request.getParameter("pass"));
		System.out.print(request.getParameter("pass"));
		if(  r != null)
		{
			String message = new Gson().toJson(new Message("OK","Restaurant Login Successfull"));
			
			request.getSession().setAttribute("email", r.getId());
			
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
	
			out.print(message);
			out.flush();
		}
		else 
		{
			String message = new Gson().toJson(new Message("Failed","Restaurant Login Failed"));
			
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print(message);
			out.flush();
		}
	}

}
