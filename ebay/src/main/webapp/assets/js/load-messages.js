/**
 * 
 */
jQuery(document).ready(function($){
		$("#myForm").hide();

		$("#search_btn").click(function(event){
			window.location="http://localhost:8080/ebay/users.html";
		});
		$("#msg_btn").click(function(event){
			$("#myForm").show();
			$("#send").click(function(event){
				var  req_data= {
				s_roll_no 	: sessionStorage.getItem("rollNumber"),
				r_roll_no   : $("#r_roll_no").val(),
				message	    : $("#message").val()
			};
				
			$.ajax({url:"http://localhost:8080/ebay/webapi/myresource/sendMsg", type:"POST",
				data: JSON.stringify(req_data),
				dataType: "json",
				async: false,
				 	
				success: function(data) {

					if(data.result=="success"){
						//$("#r_roll_no").val("");
						//$("#message").val("");
						alert("Successfully sent");
					}
					else{
						alert("Data could not be found");
					}
				},
				error: function(data) {
					alert("failed");
				}
			});		
		});
		});
		$("#close").click(function(event){
			$("#myForm").hide();
			$("#user_profile").show();
		});
		$("#reload_btn").click(function(event){
			recieveMsgs();
		});
		
});