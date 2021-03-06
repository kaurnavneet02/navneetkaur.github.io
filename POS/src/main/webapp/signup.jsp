<!DOCTYPE html>
<html lang="en">
<head>
    
    <jsp:include page="pos/links.html"/>
    <%@ include file="pos/config.jsp" %>
    
    
    <title>Create a new account - Restaurant Application</title>
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
                  <h5 class="card-title">Welcome to <% out.print(BRAND_NAME); %></h5>
                  <h6 class="card-subtitle mb-2 text-muted">Please fill up the information to get started with <% out.print(BRAND_NAME); %></h6>
                
                    <form onsubmit="return false;">
                        <input type="text" name="rname" placeholder="Enter the name of the Restaurant" class="form-control mb-2 mt-3"/>
                        <input type="email" name="email" placeholder="Enter your email here." class="form-control mb-2"/>
                        <input type="email" name="cemail" placeholder="Confirm your email here." class="form-control mb-2"/>
                        <input type="password" name="pass" placeholder="Enter your password here." class="form-control mb-2"/>
                        <input type="checkbox" name="cb"  /> <span class="mb-2"> I agree to the  <a href="#">terms & conditions </a>of <% out.print(BRAND_NAME); %> </span>
                        <input type="submit" value="Sign Up" class="btn btn-primary btn-block mb-2 mt-2" />
                    </form>

                    <div class="alert">

                    </div>

                  
                  <a href="#" class="card-link">Forgot Password ?</a>
                  <br/>
                  <p>Already a member ? <a href="/POS/" class="card-link">Sign In</a></p>

                </div>
                <div class="card-footer text-center">
                    &copy; Copyright 2021 <% out.print(BRAND_NAME); %>
                </div>
              </div>
              
        </div>
        <div class="col-md-4"></div>
    </div>



    <!-- Form Submission Starts -->
    <script>

        var allTrue = function(){

            return $("input[name=rname]").val() != "" &&
            $("input[name=email]").val() != "" &&
            $("input[name=cemail]").val() != "" &&
            $("input[name=pass]").val() != "" &&
            $("input[name=cb]").val() == "on" &&
            $("input[name=email]").val() ==
            $("input[name=cemail]").val() ;
        }
        $("input[type=submit]").attr("disabled","disabled");
        $("input[name=rname]").focusout(function(){
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
        $("input[name=cemail]").focusout(function(){
            if($(this).val() == "" || $(this).val() != $("input[name=email]").val())
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

        $("input[name=cb]").change(function(){

            if( 
            allTrue()
            )
            {
                $("input[type=submit]").removeAttr("disabled");
            }

        });




        
        $('form').submit(function(){
            
          
            $('.card').loading({
                circles:3,
                overlay:true,
                width:200
            });

            $.ajax({
                url:'/POS/SignUp/',
                method:'POST',
                data:$(this).serialize(),
                success: function(message){
                    $("div.alert").addClass("alert-success");
                    $("div.alert").css("display","block");
                 
                    $("div.alert").html("Account Created Successfully, Please proceed to <a href='{{url_for('index')}}' classs='text-bold'> Login </a>Now");
                    $('.card').loading({destroy:true});
                },
                error: function(err){
                    $("div.alert").css("display","block");
                    $("div.alert").addClass("alert-danger");
                    $("div.alert").html("Unable to create an account error message: "+err.message);
                
                }
            });

        });
    </script>

    <!-- Form Submission Ends -->

</body>
</html>