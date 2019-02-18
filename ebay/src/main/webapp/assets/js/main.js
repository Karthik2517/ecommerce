$.noConflict();

jQuery(document).ready(function($) {

	$("#signup").click(function(){
		window.location="http://localhost:8080/ebay/SignUp.html";
	});
	
	$('#Login').click( function() {
    var login_details = {
    		
    	roll_no : $("#rollNumber").val(),
    	pwd   : $("#pwd").val(),
    		
    };
	
	$.ajax({
        url: "http://localhost:8080/ebay/webapi/myresource/authenticate",
        data: JSON.stringify(login_details),
        type: "POST",
        success: function(data) {
        	
				if(data=="success"){
				sessionStorage.setItem("rollNumber",$("#rollNumber").val());
				window.location.replace("http://localhost:8080/ebay/viewProfile.html");
				   }
				else	{
					alert("Invalid Password or Roll Number");
				}
			},

    	});
	});
});