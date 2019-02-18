/**
 * 
 */
	var roll_nos
	var url_api = "getUserList"
	var previous_sort_by="";
	$(document).ready(function(){
		var counter = 0;
		var user_no=0;
		var page =0;
		$('#submit_btn').click(function(){
			render_data();
		});
		
		function render_data(){
			
			roll_nos = {
					start_rollno : $('#rollno_1').val(),
					end_rollno : $('#rollno_2').val(),
					count : counter,
			}
			
			sort_by = get_sort();
			
			if(previous_sort_by != sort_by){
				if(sort_by=="roll_no"){
					url_api = "getUserList";
				}
				
				else if(sort_by=="name"){
					url_api = "getUserListOrderByName";
				}
					
				else if(sort_by=="marks"){
					url_api = "getUserListOrderByMarks";
				}
					
				else{
					url_api = "getUserListOrderByDOB";
				}
				previous_sort_by = sort_by;
				get_list(roll_nos,url_api);
			}
			
		}
		
		function get_list(roll_nos,url_api){
			
			
			removeUsers();			
			
			user_no = 0;
			
			$.ajax({url:"http://localhost:8080/ebay/webapi/myresource/"+url_api,
				data: JSON.stringify(roll_nos),
				type:"POST",
				dataType : "json",
				async: true,
		 	
				success: function(data) {
				
				/*if(data.result=="success"){
					console.log(data);
					var student_list = data.users;
					for(var i=0;i<student_list.length;i++){
						user_no++;
						var rollNumber = student_list[i].roll_no;
						var username = student_list[i].name;
						var dob = student_list[i].dob;
						var marks = student_list[i].marks;
						var grade = student_list[i].grade;
						var image_location = student_list[i].pic_location;
						
						var student = "<div id=" + "user_profile_" +user_no+ " class='card'>"+
										"<div id=" + "image_" + user_no +" class='image' align'right'>" +
						  					"<img src = 'images/sample.jpg' id=" + "user_image_"+ user_no + " width=100 height=100 align='left'>" +
					  					"</div>" +
					  					"<div id=" +  "details_"+user_no + " class='details' align='left'>"+
					  						"<label id=" + "username_" + user_no + " >Username</label><br>" +
					  						"<label id=" + "rollNumber_" + user_no + " >RollNumber : IMT2015014</label><br>" +
					  						"<label id=" + "dob_" +user_no  + " >DOB : 11-06-1997</label><br>" +
					  						"<label id=" + "marks_"+ user_no + " >Marks : 100</label><br>"+
					  						"<label id=" + "grade_"+ user_no+ " >Grade : A</label><br>"+	
					  					"</div>"+
							 		   "</div>";
							 		   
						$("body").append(student);	 		   
						$("#rollNumber_"+user_no).text("Roll Number : " + student_list[i].roll_no);
						$("#username_"+user_no).text("Name : "+student_list[i].name);
						$("#dob_"+user_no).text("DOB : " + student_list[i].dob);
						$("#user_image_"+user_no).attr("src",student_list[i].pic_location);
						$("#marks_"+user_no).text("Marks : "+student_list[i].marks);
						$("#grade_"+user_no).text("Grade : "+student_list[i].grade);
						$("#user_profile_"+user_no).show();
					}				   
				}*/
				if(data.result=="success"){
					console.log(data);
					var student_list = data.users;
					var start_table = "<table id='user_table'>" +					
								"<tr>" +
									"<th>S.No</th>" +
									"<th>Picture</th>" +
									"<th>Username</th>" +
									"<th>Roll Number</th>" +
									"<th>Date of birth</th>" +
									"<th>Marks</th>" +
									"<th>Grade</th>" +
								"</tr>" +
								"</table>";
					$("body").append(start_table);
					for(var i=0;i<student_list.length;i++){
						user_no++;
						var rollNumber = student_list[i].roll_no;
						var username = student_list[i].name;
						var dob = student_list[i].dob;
						var marks = student_list[i].marks;
						var grade = student_list[i].grade;
						var image_location = student_list[i].pic_location;
							
						var student = "<tr id=" + "user_profile_"+user_no+">" +
										"<td>"+user_no+"</td>"+
										"<td> <a target='_blank' href="+image_location+"> <img src="+image_location+" height=70 width=70></a></td>"+
										"<td>"+username+"</td>"+
										"<td>"+rollNumber+"</td>"+
										"<td>"+dob+"</td>"+
										"<td>"+marks+"</td>"+
										"<td>"+grade+"</td>"+
									  "</tr>";
						$("#user_table").append(student);	 		   
						$("#user_profile_"+user_no).show();
					}	
					//var end_table = "</table>";
					//$("body").append(end_table);
				}
				else{
					alert("failed not success");
				}
			},
			error: function(data) {
				alert("failed");
			}
		 	
			});
		}
		
		function get_sort(){	
			
			var selected_sort = $('#sort_menu').val();
			return selected_sort;
		}
		
		$("#sort_menu").click(function(){
			render_data();
		});
		
		$("#home_btn").click(function(){
			window.location = "viewProfile.html";
		});
		
		function removeUsers(){
			$('#user_table').remove();
/*			for(var i=1;i<user_no+1;i++){
				$("#user_profile_"+i).remove();
			}
*/
		}
		
	});