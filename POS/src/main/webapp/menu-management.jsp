
<!DOCTYPE html>
<html lang="en">
<head>


<%@page import="pos.db.models.*, java.util.*" %>
<%

		Object obj = request.getSession().getAttribute("email");

		if(obj==null)
		{
			response.sendRedirect("index.jsp");
		}
		else
		{
			
		

	int restaurant = Integer.parseInt(obj.toString());
		
%>

  <jsp:include page="pos/links.html"/>
  <%@ include file="pos/config.jsp" %>
  
 
    <title>Home - Restaurant Application</title>
    <style>
       .card-header > a
      {
          text-decoration:none !important;
      }
      .card-header
      {
        background-color: var(--secondary);
      }
      .card-header > a > h5{
        color: white;
        font-weight: 800px;;
      }
    </style>
</head>
<body>

    <div class="d-flex" id="wrapper">
        <jsp:include page="sidebar.html"/>
    
        <!-- Page Content -->
        <div id="page-content-wrapper">
    
          <jsp:include page="navbar.html"/>
    
          <div class="container-fluid p-5">
            <h1 class="">Menu Management</h1>
            <p>All Your Menu Items Will Appear Here. You can add, update or delete these items.</p>
            
            <form action="/POS/CreateCategory/" method="POST">
                <div class="row mb-3">
                  <div class="col-8">
					<input type="hidden" name="restaurant" value = "<% out.print(restaurant);%>"/>
                    <input type="text" name="name" placeholder="e.g. Starters, Mains, Dessert, etc." class="form-control"/>
                    <small>Enter <b>category</b> name here</small>
                  </div>
                  <div class="col-4">

                    <input type="submit" value="Create Category" class="btn btn-block btn-primary"/>
                  </div>
                </div>
            </form>
            
            
                   <% if (Category.getAll(restaurant).size() == 0)  { %>
                   <div class="alert alert-warning">
                     <i class="fas fa-info-circle"></i> &nbsp; The Menu Not Found. Please Start Creating the menu by creating product categories.
                   </div>
               <% }  %>
            

            <div class="accordion" id="accordionExample">

				<% 
				
					Iterator i = Category.getAll(restaurant).iterator();
					int k =0;
					while(i.hasNext())
					{
						k++;
						Category c = (Category) i.next();
						
				%>
				
				
				<div class="card z-depth-0 bordered">
                <div class="card-header" id="headingOne" style="cursor: pointer; color:white;"  data-toggle="collapse" data-target="#col<%out.print(c.getCategory()); %>"
                aria-expanded="true" aria-controls="col<%out.print(c.getCategory()); %>">
                  <h5 class="mb-0" >

                      <% out.print(c.getName()); %>


                  </h5>
                </div>
                <div id="col<%out.print(c.getCategory()); %>" class="collapse <%if(k==1) { %> show <% } %>" aria-labelledby="headingOne"
                  data-parent="#accordionExample">
                  <div class="card-body">
                    <p><a href="#" id="ap<%out.print(c.getCategory()); %>" class="add-product-selector   class-<%out.print(c.getName());%>">Add Products</a> in <% out.print(c.getName()); %> category, or just completely <a href="/POS/DeleteCategory/?category=<%out.print(c.getCategory());%>">delete it.</a></p>


					<%
					
						List<Product> l = Product.getAll(c.getCategory());
					
						if(l.size() !=0 )
						{
					%>

                  

                    <table class="table table-sm table-light table-striped table-bordered ">
                      <thead class="bg-primary text-white text-center">
                        <tr>
                          <th>Product Name</th>
                          <th>Price</th>
                          <th>Delete</th>
                        </tr>
                      </thead>
                      <tbody>
                        <%
                        
                        	Iterator<Product> newI = l.iterator();
                        
                        	while(newI.hasNext())
                        	{
                        		Product p = newI.next();
                        
                        %>
                        <tr>
                          <td><% out.print(p.getName()); %></td>
                          <td>$<%out.print(p.getPrice()); %></td>
                          
                          
                          <td class="text-center">
                            <a href="/POS/DeleteProduct/?product=<%out.print(p.getProduct());%>"><i class="fas fa-trash-alt text-danger"></i></a>
                          </td>
                        </tr>
                      <% } %>
                      </tbody>
                    </table>

                    <% }
						
						else
						{
					%>
                    <div class="alert alert-warning">
                      <i class="fas fa-info-circle"></i> There are no products for this category as of now. Please starting add products by clicking add products above.
                    </div>
                   <%} %>
                  </div>
                </div>
              </div>
				
				
				
				
				
				
				<% 
					}
				
				
				%>


              

             
            </div>













        </div>
        </div>
        <!-- /#page-content-wrapper -->

      </div>
      <!-- /#wrapper -->




      <!-- ADD NEW PRODUCT MODAL STARTS -->


      <div class="modal fade" id="addProductModal" tabindex="-1" role="dialog" aria-labelledby="addProductModalTitle" aria-hidden="true">
        <form action="/POS/CreateProduct/" method="POST" enctype="multipart/form-data">

        <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
          <div class="modal-content">
            <div class="modal-header bg-primary">
              <h5 class="modal-title text-white" id="exampleModalLongTitle">Add New Product for: <span class="text-secondary" id="productCategoryInModal"></span>  </h5>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true" class="text-white">&times;</span>
              </button>
            </div>
            <div class="modal-body">
              <p>Add a new product here. </p>
                <input type="hidden" name="category" />
                <input type="text" required class="form-control mb-2" name="product_name" placeholder="Enter product name" onblur="xyz()"/>
                <input type="text"  required class="form-control mb-2" name="price" placeholder="Enter price in CAD"/>
               
              <hr/>

            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
              <button type="submit" class="btn btn-secondary">Save changes</button>
            </div>
          </div>
        </div>
      </form>

      </div>





      <!-- ADD NEW PRODUCT MODAL ENDS-->

      <script>
        $("#menu-toggle").click(function(e) {
          e.preventDefault();
          $("#wrapper").toggleClass("toggled");
        });



        $("a.add-product-selector").click((event)=>{

          category = event.target.className.split("   ")[1].split("-").slice(1).join("-");
          $("#productCategoryInModal").text(category);
          console.log();
          $("input[name=category]").val(event.target.id.substring(2));
          $("#addProductModal").modal('show');

        });




        function fileValidation(){
          var fileInput = document.getElementById('file');
          var filePath = fileInput.value;
          var allowedExtensions = /(\.jpg|\.jpeg|\.png|\.gif)$/i;
          if(!allowedExtensions.exec(filePath)){
            $.toast({
    text: "Please upload file having extensions .jpeg/.jpg/.png/.gif only.", // Text that is to be shown in the toast
    heading: 'Error', // Optional heading to be shown on the toast
    icon: 'error', // Type of toast icon
    showHideTransition: 'fade', // fade, slide or plain
    allowToastClose: false, // Boolean value true or false
    hideAfter: 6000, // false to make it sticky or number representing the miliseconds as time after which toast needs to be hidden
    stack: 5, // false if there should be only one toast at a time or a number representing the maximum number of toasts to be shown at a time
    position: 'top-center', // bottom-left or bottom-right or bottom-center or top-left or top-right or top-center or mid-center or an object representing the left, right, top, bottom values
    
    
    
    textAlign: 'left',  // Text alignment i.e. left, right or center
    loader: true,  // Whether to show loader or not. True by default
    loaderBg: '#087E8B',  // Background color of the toast loader
    bgColor: '#FF5a5f'
});
              fileInput.value = '';
              return false;
          }else{
              //Image preview
              if (fileInput.files && fileInput.files[0]) {
                  var reader = new FileReader();
                  reader.onload = function(e) {
                      document.getElementById('imagePreview').innerHTML = '<img src="'+e.target.result+'"/>';
                  };
                  reader.readAsDataURL(fileInput.files[0]);
              }
          }
      }



      var xyz = ()=>{
        $('input[name=variant]').attr('placeholder','Enter the variants of '+$('input[name=product_name]').val());}
      </script>
    
</body>
</html>


<%
	
		}

%>