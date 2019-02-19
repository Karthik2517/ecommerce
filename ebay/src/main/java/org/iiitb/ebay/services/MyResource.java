package org.iiitb.ebay.services;

import javax.ws.rs.GET;

import javax.ws.rs.Path;


import java.io.*;
import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.POST;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.iiitb.ebay.models.DatabaseConnection;
import org.json.JSONObject;


/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
	@Path("userInfo")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String userInfo(String request) throws Exception{
      
    	JSONObject data = new JSONObject(request);
    	String roll_no = data.getString("roll_no");
    	
    	DatabaseConnection dc = new DatabaseConnection();
    	return dc.userDetails(roll_no);
    }
	
	@Path("authenticate")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String authenticateUser(String request) throws Exception{
      
    	JSONObject user_data = new JSONObject(request);
    	String roll_no = user_data.getString("roll_no");
    	String pwd = user_data.getString("pwd");
    	
    	System.out.println(user_data.toString());
    	
    	String url = "http://localhost:8080/ebay/viewProfile.html";
    	URI uri=new URI(url);
    	DatabaseConnection dc = new DatabaseConnection();
    	if(dc.authenticate(roll_no, pwd).equals("success"))
    	{
    		System.out.println(roll_no + " is authenticated");
    		return "success";
    	}
   		return "fail";
    	
	}
	
	@Path("deleteUser")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteUser(String request) throws Exception{
      
    	JSONObject user_data = new JSONObject(request);
    	String roll_no = user_data.getString("roll_no");
    	
    	System.out.println(user_data.toString());
    	DatabaseConnection dc = new DatabaseConnection();
    	if(dc.deleteUser(roll_no).equals("success"))
    	{
    		return "success";
    	}
   		return "fail";
    	
	}
	
	//Get student list given two roll numbers
			@Path("getUserList")
			@POST
			@Produces(MediaType.TEXT_PLAIN)
			public String getUserList(String data) throws Exception{
				//TODO (Deepika, Sravya)
				JSONObject roll_nos = new JSONObject(data);
				String roll_no1 = roll_nos.getString("start_rollno");
				String roll_no2 = roll_nos.getString("end_rollno");
				int present_count = roll_nos.getInt("count");
				
				DatabaseConnection dc = new DatabaseConnection();
				return dc.getUsers(roll_no1, roll_no2,present_count);
			}
			
			//Get student list given two roll numbers ordered by name
			@Path("getUserListOrderByName")
			@POST
			@Produces(MediaType.TEXT_PLAIN)
			public String getUserListOrderByName(String data) throws Exception{
				//TODO (Deepika, Sravya)
				JSONObject roll_nos = new JSONObject(data);
				String roll_no1 = roll_nos.getString("start_rollno");
				String roll_no2 = roll_nos.getString("end_rollno");
				int present_count = roll_nos.getInt("count");
						
				DatabaseConnection dc = new DatabaseConnection();
				return dc.getUsersOrderByName(roll_no1, roll_no2,present_count);
			}
			
			//Get student list given two roll numbers ordered by name
			@Path("getUserListOrderByMarks")
			@POST
			@Produces(MediaType.TEXT_PLAIN)
			public String getUserListOrderByMarks(String data) throws Exception{
				//TODO (Deepika, Sravya)
				JSONObject roll_nos = new JSONObject(data);
				String roll_no1 = roll_nos.getString("start_rollno");
				String roll_no2 = roll_nos.getString("end_rollno");
				int present_count = roll_nos.getInt("count");
						
				DatabaseConnection dc = new DatabaseConnection();
				return dc.getUsersOrderByMarks(roll_no1, roll_no2,present_count);
			}
			
			//Get student list given two roll numbers ordered by dob
			@Path("getUserListOrderByDOB")
			@POST
			@Produces(MediaType.TEXT_PLAIN)
			public String getUserListOrderByDOB(String data) throws Exception{
				//TODO (Deepika, Sravya)
				JSONObject roll_nos = new JSONObject(data);
				String roll_no1 = roll_nos.getString("start_rollno");
				String roll_no2 = roll_nos.getString("end_rollno");
				int present_count = roll_nos.getInt("count");
						
				DatabaseConnection dc = new DatabaseConnection();
				return dc.getUsersOrderByDOB(roll_no1, roll_no2,present_count);
			}
	@Path("updateName")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String updateUserName(String request) throws Exception{
      
    	JSONObject data = new JSONObject(request);
    	DatabaseConnection dc = new DatabaseConnection();
    	if(dc.updateName(data).equals("success"))
    	{
    		return "success";
    	}
   		return "fail";
    	
	}
	
	@Path("updateMarks")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String updateUserMarks(String request) throws Exception{
      
    	JSONObject data = new JSONObject(request);
    	DatabaseConnection dc = new DatabaseConnection();
    	if(dc.updateMarks(data).equals("success"))
    	{
    		return "success";
    	}
   		return "fail";
    	
	}
	
	@Path("updateUserPicture")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateUserPicture(
    			@FormDataParam("file") InputStream uploadedInputStream,
    			@FormDataParam("file") FormDataContentDisposition fileDetail,
    			@FormDataParam("rollNumber") String roll_no
    			) throws Exception{
		
		try{
			String uploadedFileLocation = "/Users/pranithreddy/git/ebay/src/main/webapp/images/"+ fileDetail.getFileName();
		
			writeToFile(uploadedInputStream, uploadedFileLocation);
			
			JSONObject data = new JSONObject();
			data.put("roll_no",roll_no);
			data.put("pic_location"," images/"+fileDetail.getFileName());
			DatabaseConnection dc = new DatabaseConnection();
			if(dc.updatePicture(data).equals("success"))
			{
				return "success";
			}
			return "fail";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "fail";
		}
    	
	}
	
	@Path("createUser")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response createUser(
                  @FormDataParam("path") InputStream uploadedInputStream,
                  @FormDataParam("path") FormDataContentDisposition fileDetail,
                  @FormDataParam("rollnumber") String roll_no,
                  @FormDataParam("psw") String password,
                  @FormDataParam("name") String name,
                  @FormDataParam("marks") Float marks,
                  @FormDataParam("dob") String dob
                  ) throws Exception{
                              
          DatabaseConnection dc = new DatabaseConnection();
          System.out.println(uploadedInputStream);
          //String relPath = "images/" + name + "_" + fileDetail.getFileName();
          
          String uploadedFileLocation = "/Users/pranithreddy/git/ebay/src/main/webapp/images/"+ fileDetail.getFileName();
          System.out.println("Upload loc:"+uploadedFileLocation);
          
          writeToFile(uploadedInputStream, uploadedFileLocation);
          
          /*
           * Code to store image in path
           */
          JSONObject user_info = new JSONObject();
          user_info.put("roll_no",roll_no);
          user_info.put("password", password);
          user_info.put("marks",marks);
          user_info.put("name",name);
          user_info.put("dob", dob);
          user_info.put("pic_location"," images/"+fileDetail.getFileName());
          
          String url = "http://localhost:8080/ebay/login.html";
          URI uri=new URI(url);
          dc.createUser(user_info);
          return Response.status(Status.MOVED_PERMANENTLY).location(uri).build();

    }
	
	
	
	//Send message from one user to the other
	@Path("sendMsg")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String sendMsg(String data) throws Exception{
				//TODO (Pranith, Sowmya)
				JSONObject msg_details = new JSONObject(data);
				String sender_rn = msg_details.getString("s_roll_no");
				String recv_rn = msg_details.getString("r_roll_no");
				String msg = msg_details.getString("message");
				
				DatabaseConnection dc = new DatabaseConnection();
				return dc.addMsg(sender_rn, recv_rn, msg);
			}
			
	//Retrieve messages of a user
	@Path("retrieveMsg")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String retrieveMsg(String data) throws Exception{
				//TODO (Pranith, Sowmya)
				JSONObject recv_details = new JSONObject(data);
				String recv_rn = recv_details.getString("roll_no");
				
				DatabaseConnection dc = new DatabaseConnection();
				int student_id = dc.getStudent_id(recv_rn);
				return dc.getMsgs(student_id);
	}
	
	 private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation)
     {
             try
             {
                     OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
                     int read = 0;
                     byte[] bytes = new byte[1024];

                     out = new FileOutputStream(new File(uploadedFileLocation));
                     while ((read = uploadedInputStream.read(bytes)) != -1)
                     {
                             out.write(bytes, 0, read);
                     }
                     out.flush();
                     out.close();
             }catch (IOException e)
             {

                     e.printStackTrace();
             }

     }
	
}
	
