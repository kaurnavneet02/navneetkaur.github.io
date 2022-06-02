package pos.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pos.db.Database;
import pos.db.models.Category;
import pos.db.models.Product;

/**
 * Servlet implementation class DeleteProduct
 */
@WebServlet("/DeleteProduct/")
public class DeleteProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public DeleteProduct() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String product = request.getParameter("product");
		int p = Integer.parseInt(product);
		Product pro = new Product(0, "", 1.0,"");
		pro.setProduct(p);
		Database.db().delete(pro);
		
		response.sendRedirect("/POS/menu-management.jsp");
	}

	

}
