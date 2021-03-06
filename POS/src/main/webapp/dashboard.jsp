<!DOCTYPE html>
<html lang="en">
<head>
  
   	<jsp:include page="pos/links.html" />
   	<%@include file="pos/config.jsp"  %>
   	<%@page  import="java.util.*,pos.db.models.*" %>
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


<%@page isELIgnored="false" %>

    <title>Home - Restaurant Application</title>
    <style>
        .image-holder
        {
            width:120px;
            height:120px;
            overflow:hidden;
            display:flex;
            background:var(--primary);
            align-items:center;
            justify-content:center;
        }
        .image-holder img
        {
            width:100px;
            height:100px;
            display:block;
        }

        .holder{
            display:flex;
             align-items:center;
            justify-content:center;
            flex-direction:column;
            cursor:pointer;
        }

        .holder h6
        {
            font-weight:bold;
        }
        .sidebar-cart
        {
          min-height: 90vh;
          border-radius: 50px;
          background-color: #e2e2e2;
        }

    </style>
</head>
<body>

    <div class="d-flex" id="wrapper">

        
        <jsp:include page="sidebar.html"/>

        <!-- Page Content -->
        <div id="page-content-wrapper">

          <jsp:include page="navbar.html"/>

          <div class="container-fluid">
            <div class="row ">
                <div class="col-md-9 main-content">
                  <h1 class="mt-4">Create An Order</h1>
            <p>You can create an order here. Please start adding the products in the Tray</p>

            
             <%
             
             	Iterator<Category> categories = Category.getAll(restaurant).iterator();
             	while(categories.hasNext())
             	{
             		
             		Category c = categories.next();
             %>
             
             <div class="row">
                  <div class="col-md-12 p-3">
                    <h2 style="display:inline; border-bottom:2px solid black;"><% out.print(c.getName()); %></h2>
                      <div class="row mt-5">
                          
                          <%
                          
                          	Iterator<Product> products = Product.getAll(c.getCategory()).iterator();
                        		  
                        	while(products.hasNext())
                        	{
                        		
                        		Product p = products.next();
                          %>

                          
                           <div class="col-1 col-md-3 col-lg-2 holder" onclick="openAddToCartModal('<% out.print(p.getProduct()); %>','<% out.print(p.getName()); %>', '<% out.print(p.getPrice()); %>', '<% out.print(p.getImage()); %>','<%out.print(p.getCategory()); %>', '<%= restaurant %>');">
                            <div class="image-holder">

                                 
                                  <br/>
                                  <br/>


                            </div>
                               <h6><%out.print(p.getName()); %></h6>
                              $<%out.print(p.getPrice()); %>
                        </div>
                          <% } %>
                      </div>
                  </div>
                  </div>
             
             
             <%	
             	}
             %>
               
               
               
                </div>
                <div class="col-md-3 p-3">
                  <div class="sidebar-cart p-3" style="display: flex; flex-direction: column;">
                    <style>
                      .empty-state
                      {
                          display: flex;
                          align-items: center;
                          padding: 10%;
                          flex-direction: column;
                          color:grey;
                      }
                      .plus-icon
                      {

                        font-size: 72px;
                        color:grey;
                        border:5px dashed grey;

                        padding:10% 20%;
                        border-radius:30px;

                      }

                      .not-active
                      {
                        display: none;
                      }

                      .image-in-cart
                      {
                        height:60px;
                        width: 60px;
                        border-radius: 10px;;
                      }
                    </style>
                      <div class="empty-state ">
                          <span class="plus-icon">+</span>
                          <h4 class="mt-5">Add Items To Tray</h4>
                      </div>
                      <div class="products mt-5" id="products_">

                      </div>
                      <button class="btn btn-primary btn-block" onclick="checkout()" style="align-self: flex-end;">Checkout</button>
                      <br/>
                      <br/>

                  </div>
                </div>
            </div>
        </div>
        </div>
        <!-- /#page-content-wrapper -->

      </div>
      <!-- /#wrapper -->
   <!-- ADD NEW PRODUCT MODAL STARTS -->


      <div class="modal fade" id="addProductToTrayModal" tabindex="-1" role="dialog" aria-labelledby="addProductModalTitle" aria-hidden="true">

        <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
          <div class="modal-content">
            <div class="modal-header bg-primary">
              <h5 class="modal-title text-white" id="exampleModalLongTitle">Add To Tray  </h5>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true" class="text-white">&times;</span>
              </button>
            </div>
            <div class="modal-body">
                
              <p>Quantity</p>
                <div class="text-center" id="num" style="font-size:72px">
                    0
                </div>

                <hr/>
                <div class="row">
                    <div class="col-6">
                        <button type="button"class="btn btn-block btn-secondary"  style="height:100px; font-size:32px; font-weight:bold;" onclick="decrement();" >-</button>
                    </div>  <div class="col-6">
                        <button type="button"class="btn btn-block btn-secondary"  style="height:100px; font-size:32px; font-weight:bold;" onclick="increment()" >+</button>
                    </div>
                </div>

            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary btn-block" id="trayAdder">Add To Tray</button>
            </div>
          </div>
        </div>


      </div>

      <script>




        productsInTray = []
        selectedProduct = undefined
        $("#menu-toggle").click(function(e) {
          e.preventDefault();
          $("#wrapper").toggleClass("toggled");
        });

        var number = 0;

        increment = ()=>{
            number++;
           $("#num").html(number);
        }


        decrement = ()=>{

            if(number>0)
            {
            number--;
               $("#num").html(number);

            }

        }

         renderVariants = (variants) => {
            $("#elements").html("");

            variants.forEach((variant)=>{
                                var element = `
                <input type="radio" name='selectedVariant' id="${variant}" value=${variant} /> <label for="${variant}">${variant}</label> <br/>

                `;

                $("#elements").append(element);})



        }
         
         
        var openAddToCartModal = (productId, productName, productPrice, productImage, category, restaurant) => {
        number = 0;

           
            selectedProduct = {
            		"name":productName, 
            		"price":productPrice,
            		"image":productImage,
            		"category":category,
            		"restaurant":restaurant,
            		"product":productId
            }
           $("#addProductToTrayModal").modal('show');
           $("#num").html(number);
        }




        validateIfVariantSelected = ()=>{

          if($("input[name=selectedVariant]:checked").val() == undefined)
          {
            return false;
          }

          return $("input[name=selectedVariant]:checked").val();
      }

        var qtyInTray = 0;
        $("#trayAdder").click(()=>{

            var variant = validateIfVariantSelected();
              if(true)
              {

                var qty = $("#num").html();

               qty = parseInt(qty)
                console.log(qty);
                qtyInTray += qty;

                console.log(selectedProduct);


                selectedProduct['qty'] = qty;
               

                productsInTray.push(selectedProduct);

                updateTray();

                renderTray();
                $("#addProductToTrayModal").modal("hide");



              }
              else
              {
                $.toast(
                  {text:"Please select a variant", position:"top-center"}
                )
              }
          });


          updateTray = ()=>{

            if(qtyInTray == 0)
            {
                $("#bad").fadeOut();
            }
            else
            {
              $("#bad").fadeIn();
              $("#bad").html(qtyInTray);
            }


          }


          var currentProductCountInCart = 0
          renderTray = () => {

        	  
        	  console.log(productsInTray);
            $("#products_").html("");
              $(".empty-state").addClass("not-active");
              productsInTray.forEach((selectedProduct)=>{

			console.log(selectedProduct);
              var element = productInCartElement(selectedProduct.image, selectedProduct.name, selectedProduct.qty, selectedProduct.price, selectedProduct.variant , selectedProduct.id)
              console.log(element);
              $("#products_").append(element);


              })

          }


           productInCartElement = (image, product, qty, price, variant, id)=>{


              return "<div class='row product'> <div class='col-3'> </div> <div class='col-9'> <div class='product-details'> <h6>"+product+"</h6> <div class='row'> <div class='col-8' style='color: grey; font-weight: dark;'>  Qty: "+qty+" <span onclick=deleteProductFromCart('"+id+"') style='color:grey;'><i class='fas fa-trash-alt'></i></span> </div> <div class='col-4' style='color: grey; font-weight: dark; text-align: right;' >$"+price+"</div> </div> </div> </div> </div> <hr/>"


          }


          deleteProductFromCart = (id)=>{

            indexToDelete = -1;
            for(var i=0;i<productsInTray.length;i++)
            {
              if(productsInTray[i].id == id)
              {
                qtyInTray -= productsInTray[i].qty;
                indexToDelete = i;
              }
            }

            productsInTray.splice(indexToDelete, 1);
            renderTray();

            if(productsInTray.length == 0)
            {
              $(".empty-state").removeClass("not-active");
            }
            updateTray();


          }






          checkout = ()=>{


                if(productsInTray.length == 0)
                {
                    $.toast({
                        text:"please add items in tray before checkout",
                        position:"top-center"
                    });
                }
                else
                {

                    console.log("THIS IS CHECKOUT");
                    productsInTray.forEach((product)=>{

                        delete product['id'];
                        delete product['category'];
                        delete product['is_active'];
                        delete product['restaurant'];
                        delete product['variants'];
                        product['restaurant'] = <%=restaurant%>
                     
                        console.log(product);



                    });

                    
                    
                    $.ajax({
                        url:'/POS/CreateOrder/',
                        method:'POST',
                        dataType:'json',
                        contentType:'application/json',
                        data:JSON.stringify(productsInTray),
                        success:(data)=>{
                            if( data.status == "OK")
                            {
                              $.toast({
                                'text':"Order successfully created. Please serve the order to the customer and then start another order (if any). To see past orders, go to order history.",
                                'position':'top-center'
                              });

                              cleanCart();

                            }
                        },
                        error:(err)=>{}
                    });
                }


                cleanCart = () => {

                  $()
                  productsInTray = []
                  qtyInTray  = 0
                  renderTray()
                  updateTray()

                  if(productsInTray.length == 0)
            {
              $(".empty-state").removeClass("not-active");
            }

                }

          }


      </script>
    
</body>
</html>

<%

		}
%>