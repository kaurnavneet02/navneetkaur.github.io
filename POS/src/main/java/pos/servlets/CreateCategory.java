package pos.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pos.db.Database;
import pos.db.models.Category;
import pos.db.models.Restaurant;

/**
 * Servlet implementation class CreateCategory
 */
@WebServlet("/CreateCategory/")
public class CreateCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateCategory() {
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
		
		
		
		//Database.db().insert(new Category(request.getParameter("name"), request.getParameter("restaurant")));
	
		int restaurant = Integer.parseInt(request.getParameter("restaurant"));
		String name = request.getParameter("name");
		
		new Category(restaurant, name).save();
		
		
		//request.getSession().setAttribute("email", "here");
		
		
		
		
		
		//System.out.println(request.getSession().getAttribute("email"));
		
		
		response.sendRedirect("/POS/menu-management.jsp");
		
		//doGet(request, response);
	}

}
