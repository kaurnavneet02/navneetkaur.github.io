<!DOCTYPE html>
<html lang="en">
<head>
    
   	<jsp:include page="pos/links.html" />
   	<%@include file="pos/config.jsp"  %>
   	
   	<%@ page import="pos.db.models.Restaurant" %>
   	<%
   	
   			new Restaurant("Tim Hortons","tim@gmail.com","Hello@123").save();
   	%>
    <title>Home - Restaurant Application</title>
    <style>
        .red
        {
            border-color:red;
            color:red;
        }
        .red::placeholder
        {
            color:rgb(255, 155, 155);
        }
        .alert
        {
            display: none;
        }
       
    </style>
</head>
<body class="bg-primary">
    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4 p-5">
            <div class="card mt-5">
                <div class="card-body">
                  <h5 class="card-title">Welcome to <% out.print(BRAND_NAME);%> </h5>
                  <h6 class="card-subtitle mb-2 text-muted">Please enter your credentials to start using services</h6>
                
                    <form onsubmit="return false;">
                        <input type="email" name="email" placeholder="Enter your email here." class="form-control mb-2 mt-3"/>
                        <input type="password" name="pass" placeholder="Enter your password here." class="form-control mb-2"/>
                        <input type="submit" value="Login" class="btn btn-primary mb-2" style="float:right"/>
                    </form>
                    
                  <a href="@" class="card-link">Forgot Password ?</a>
                  <br/>
                  <p>New Here ? <a href="signup.jsp" class="card-link">Create a new account.</a></p>
                  <div class="alert">

                </div>
              
                </div>
                <div class="card-footer text-center">
                    &copy; Copyright 2021 <% out.print(BRAND_NAME);%>
                </div>
              </div>
              
        </div>
        <div class="col-md-4"></div>
    </div>


    <script>
            


            var allTrue = ()=>{

                return $("input[name=email]").val() != "" &&
            $("input[name=pass]").val() != "" ;

            }

            $("input[name=email]").focusout(function(){
            if($(this).val() == "")
            {   
                $(this).addClass('red');
                $("input[type=submit]").attr("disabled","disabled");
                
            }
            else
            {
                $(this).removeClass('red');
                if(allTrue())
                {
                    $("input[type=submit]").removeAttr("disabled");
                }
            }
        

        });


        $("input[name=pass]").focusout(function(){
            if($(this).val() == "")
            {   
                $(this).addClass('red');
                $("input[type=submit]").attr("disabled","disabled");
                
            }
            else
            {
                $(this).removeClass('red');
                if(allTrue())
                {
                    $("input[type=submit]").removeAttr("disabled");
                }
            }
        

        });


            $('form').submit(()=>{
                


                $.ajax({

                    url:"/POS/SignIn/",
                    data:{
                        "email":$('input[name=email]').val(),
                        "pass":$('input[name=pass]').val()
                    },
                    method:"POST",
                    success:(data)=>{
                        if(data.status == "OK"){
                            
                            
                            window.open("/POS/dashboard.jsp", "_parent");
                            

                        }
                        else{
                            
                            $("div.alert").css("display","block");
                            $("div.alert").addClass("alert-danger");
                            $("div.alert").html("Invalid email/password combinatin for <% out.print(BRAND_NAME); %>");
                        }
                    },
                    error:(err)=>{
                        alert("Login Failed");
                    }

                });



            });



    </script>

</body>
</html>