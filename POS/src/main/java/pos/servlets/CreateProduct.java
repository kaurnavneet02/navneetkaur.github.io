package pos.servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import pos.db.models.Product;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;



/**
 * Servlet implementation class CreateProduct
 */
@WebServlet("/CreateProduct/")

public class CreateProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SAVE_DIR = "static";
	// location to store file uploaded
    private static final String UPLOAD_DIRECTORY = "upload";
 
    // upload settings
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
 
 
    public CreateProduct() {
        super();
        // TODO Auto-generated constructor stub
    }

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		String base = "C:\\Users\\user\\Desktop\\POS\\src\\main\\webapp\\images";
		// checks if the request actually contains upload file
        if (!ServletFileUpload.isMultipartContent(request)) {
            // if not, we stop here
            PrintWriter writer = response.getWriter();
            writer.println("Error: Form must has enctype=multipart/form-data.");
            writer.flush();
            return;
        }
        
        Product p = new Product(0,"",1.0,"");
 
        // configures upload settings
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // sets memory threshold - beyond which files are stored in disk
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // sets temporary location to store files
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
 
        ServletFileUpload upload = new ServletFileUpload(factory);
         
        // sets maximum size of upload file
        upload.setFileSizeMax(MAX_FILE_SIZE);
         
        // sets maximum size of request (include file + form data)
        upload.setSizeMax(MAX_REQUEST_SIZE);
 
        // constructs the directory path to store upload file
        // this path is relative to application's directory
        String uploadPath = base;
         
        // creates the directory if it does not exist
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
            System.out.println("New directory created");
        }
        else
        {
        	System.out.println("Directory already ecist");
        }
 
        try {
            // parses the request's content to extract file data
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);
            
            Product product = new Product(1, "", 1.0,"");
            if (formItems != null && formItems.size() > 0) {
                // iterates over form's fields
            	
                for (FileItem item : formItems) {
                    // processes only fields that are not form fields
                	
                    if (!item.isFormField()) {
                    	
                        String fileName = new File(item.getName()).getName();
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
                        product.setImage(fileName);
                        
                        if(!storeFile.exists())
                        {
                        	// saves the file on disk
                            item.write(storeFile);
                        }
                        System.out.println("FILE UPLOADED AT: "+filePath);
                    }
                    else
                    {
                    	System.out.println(item.getFieldName().toUpperCase());
                    	if(item.getFieldName().equals("product_name"))
                    	{
                    		String pn = item.getString();
                    		System.out.println(pn);
                    		//product.setCategory(Integer.parseInt(pn));
                    		product.setName(pn);
                    	}
                    	else if (item.getFieldName().equals("category"))
                    	{
                    		String c = item.getString();
                    		System.out.println(c);
                    		
                    		product.setCategory(Integer.parseInt(c));
                    	}
                    	else if (item.getFieldName().equals("price"))
                    	{
                    		String pr = item.getString();
                    		System.out.println(pr);
                    		product.setPrice(Double.parseDouble(pr));
                    	}
                    }
                }
            }
            
            product.save();
            System.out.println("Product Saved");
        } catch (Exception ex) {
        	ex.printStackTrace();
            request.setAttribute("message",
                    "There was an error: " + ex.getMessage());
        }
        // redirects client to message page
        System.out.println("GOING TO GET");
        
        response.sendRedirect("/POS/menu-management.jsp");
        doGet(request, response);
		

	}
	
	
}
