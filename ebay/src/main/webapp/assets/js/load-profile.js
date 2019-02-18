/**
 * 
 */
jQuery(document).ready(function($){
		$("#loader").hide();
		$("#user_profile").hide();
		$("#saveName").hide();
		$("#my_file").hide();
		$("#submit_btn").hide();
		var  req_data= {
				roll_no 	: sessionStorage.getItem("rollNumber"),
		};
		
		$.ajax({url:"http://localhost:8080/ebay/webapi/myresource/userInfo", type:"POST",
			data: JSON.stringify(req_data),
			dataType: "json",
		 	async: true,
		 	
			success: function(data) {

				if(data.result=="success"){
					$("#roll_no").text("Roll Number : " + data.roll_no);
					$("#name").val(data.name);
					$("#dob").text("DOB : " + data.dob);
					$("#user_image").attr("src",data.pic_location);
					$("#marks").val(data.marks);
					$("#grade").text("Grade : "+data.grade);
					$("#user_profile").show();   
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