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
                <div class="col-md-12 p-5 main-content">
                  <h1 class="mt-4">Order History</h1>
                <p>See your previous orders here</p>

				<%
					List<Order> orders = Order.getAll(restaurant);
				
				
				if(orders.size()==0)
				{
					%>
					
					
					 <div class="alert alert-warning">
                    <i class="fas fa-info"></i> There are no past orders yet. Go to <a href="index.jsp">Create An Order</a> to make your first order.
                </div>
					<%
				}
				else
				{
					
						Iterator<Order> i = orders.iterator();
						double orderTotal = 0;
						while(i.hasNext())
						{
							Order o = i.next();
							
						
				%>
          
                  
                    <div class="row my-3 bg-primary-light" style=" padding: 5%; border-radius: 20px;">
                        <div class="col-12" style="color:grey;">
                            Order Date: <%= o.getDatetime()  %> <br/>
                            Order Number: POS<%= o.getOrderid() %>
                            <hr/>
                            
                            <%
                            	Iterator<OrderProduct> newI = o.getProducts().iterator();
                            orderTotal = 0;
                            	while(newI.hasNext())
                            	{
                            		
                            		OrderProduct op = newI.next();
                            		
                            		orderTotal += op.getQty() * op.getPrice();
                           
                            %>
                           
                            <div class="row my-2">
                                <div class="col-md-1" style="display: flex; justify-content: center;">
                                   
                                </div>
                                <div class="col-md-11">
                                    <span class="text-black" style="color: black; font-weight: bolder;"><%= op.getName() %></span><br/>
                                    <span class="text-black" style="color: grey; font-weight: bolder;"> QTY: <%= op.getQty() %></span><br/>
                                    <span class="text-black" style="color: grey; ;"> Price (Each Product): $<%= op.getPrice() %></span><br/>
                                    
                                </div>
                            </div>
                            <hr/>
                            ORDER TOTAL: $<%= orderTotal %>
                           <%
								}
                           %>
                        </div>

                    </div>
            
         
				<%
					}
				}
				
				%>
            
                </div>
                
            </div>
        </div>
        </div>
        <!-- /#page-content-wrapper -->

      </div>

    
</body>
</html>

<%
		}
%>