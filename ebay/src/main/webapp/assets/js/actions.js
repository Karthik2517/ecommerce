/**
 * 
 */
jQuery(document).ready(function($){
	$("#editName").click(function(event){
		
		
		if($("#editName").text()=="Edit")
		{
			$("#name").removeAttr("disabled");
			$("#name").focus();
			$("#editName").text("save");
			$("#editName").css("background-color","green");
			
		}
		else
		{
			var Data={  roll_no : sessionStorage.getItem("rollNumber"), 
						name : $("#name").val() 
					};
			$.ajax(
					{ url:"http://localhost:8080/ebay/webapi/myresource/updateName",
					  type:"POST",
					  data:JSON.stringify(Data),
					  success: function(data){
						  $("#editName").text("Edit");
							$("#editName").css("background-color","blue");
							$("#name").attr('disabled', 'disabled');
					}
			});
			 

		}
		
			
	});
	$("#editMarks").click(function(event){
		
		
		if($("#editMarks").text()=="Edit")
		{
			$("#marks").removeAttr("disabled");
			$("#marks").focus();
			$("#editMarks").text("save");
			$("#editMarks").css("background-color","green");
			
		}
		else
		{
			var Data={  roll_no : sessionStorage.getItem("rollNumber"), 
						marks : $("#marks").val() 
					};
			$.ajax(
					{ url:"http://localhost:8080/ebay/webapi/myresource/updateMarks",
					  type:"POST",
					  data:JSON.stringify(Data),
					  success: function(data){
						  $("#editMarks").text("Edit");
							$("#editMarks").css("background-color","blue");
							$("#marks").attr('disabled', 'disabled');
							location.reload();
					}
			});
			
			 

		}
		
			
	});
	
	$("#pic_btn").click(function(event){
		$("#my_file").toggle();
		$("#submit_btn").toggle();	
	});
	$("#submit_btn").click(function(event){
		
		var file = $('input[name="upload_file"]').get(0).files[0];
	    var formData = new FormData();
	    formData.append("file",file);
	    formData.append("rollNumber",sessionStorage.getItem("rollNumber"));
		$.ajax({
			url:"http://localhost:8080/ebay/webapi/myresource/updateUserPicture",
			type:"POST",
			data: formData,
			cache:false,
			contentType:false,
			processData: false,
	        success : function(data){
	        	
	        	if(data=="success")
	        	{
	        		$("#loader").show();
	        		//$("#profile").hide();
	        		setTimeout(function(){
	        			location.reload();
	        		},7000);
	    
	        	}
	        	else
	        		alert("failed to upload");
	        },
	        error : function(data){
	        	alert("failed to upload file!");
	        }
	        
		});
	});
	
});
		