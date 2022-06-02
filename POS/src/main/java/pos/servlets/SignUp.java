package pos.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import pos.db.Database;
import pos.db.models.*;
/**
 * Servlet implementation class SignUp
 */
@WebServlet("/SignUp/")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp() {
    	
        super();
        

    	System.out.println("HEE");
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
//		PrintWriter out = response.getWriter();
//		response.setContentType("application/json");
//		response.setCharacterEncoding("UTF-8");
//		out.print("{'status':'OK', 'message':'Account Created Successfully'}");
//		
//		out.flush();
		
		
		response.getWriter().append("S ");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String name = request.getParameter("rname");
		String email = request.getParameter("email");
		String password = request.getParameter("pass");
		
		Database.db().insert(new Restaurant(name, email, password));
		
//		PrintWriter out = response.getWriter();
//		response.setContentType("application/json");
//		response.setCharacterEncoding("UTF-8");
//		out.print("{'status':'OK', 'message':'Account Created Successfully'}");
//		
//		out.flush();
		
		
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		//doGet(request, response);
	}

}
