package org.iiitb.ebay.models;

import java.sql.DriverManager;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class DatabaseConnection {
	Statement statement;
	ResultSet resultSet;
	Connection connection = null;
	String query = null;

	// Constructor for opening the Database Connection

	public DatabaseConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver Found");
		}

		catch (ClassNotFoundException e) {
			System.out.println("Driver Not Found: " + e);
		}

		String url = "jdbc:mysql://localhost/student_info?verifyServerCertificate=false&useSSL=true";
		
		String user = "root";
		String password = "1589";
		connection = null;

		try {
			connection = (Connection) DriverManager.getConnection(url, user, password);
			System.out.println("Successfully Connected to Database");
		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e);
		}

	}
	
	//API to authenticate 
	
	public String authenticate(String rollnumber, String pwd) throws JSONException{
		java.sql.PreparedStatement preparedStatement = null;
		try {
			query = "select student_id, pic_location, YEAR(dob) as year, MONTH(dob) as month, DAY(dob) as day, name,roll_no from student where roll_no=? and password=?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, rollnumber);
			preparedStatement.setString(2, pwd);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				System.out.println(resultSet.toString());
				return "success";
			}
			else {
				return "fail";
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		return "exception";
			
	}
	
	// To get user details
	public String userDetails(String rollnumber) throws JSONException {

		java.sql.PreparedStatement preparedStatement = null;
		JSONObject user = new JSONObject();
		String grade="";
		try {
			query = "select  pic_location, YEAR(dob) as year, MONTH(dob) as month, DAY(dob) as day, name,roll_no,marks from student where roll_no=?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, rollnumber);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				user.put("name", resultSet.getString("name"));
				user.put("dob", resultSet.getString("day")+"-"+resultSet.getString("month")+"-"+resultSet.getString("year"));
				user.put("pic_location", resultSet.getString("pic_location"));
				user.put("roll_no", resultSet.getString("roll_no"));
				user.put("marks", resultSet.getFloat("marks"));
				user.put("result", "success");
				
				int m=(int)resultSet.getFloat("marks");
				grade = calculateGrade(m);
				user.put("grade", grade);

			}
			else {
				user.put("result", "fail");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(user.toString());
		return user.toString();

	}
	
	//API to create a new user
	public String createUser(JSONObject user) throws Exception{
		java.sql.PreparedStatement preparedStatement = null;
		try {
			query = "insert into student(student_id, roll_no,name, password, dob,marks, pic_location) values(student_id,?,?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, user.getString("roll_no"));
			preparedStatement.setString(2, user.getString("name"));
			preparedStatement.setString(3, user.getString("password"));
			preparedStatement.setString(4, user.getString("dob"));
			preparedStatement.setFloat(5, (float)user.getDouble("marks"));
			preparedStatement.setString(6, user.getString("pic_location"));
			preparedStatement.execute();
			return "success";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "fail";
	}
	
	
	//API to update name of user
	
	public String updateName(JSONObject data) throws Exception{
		java.sql.PreparedStatement preparedStatement = null;
		try {
			query = "update student set name = ? where roll_no =?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, data.getString("name"));
			preparedStatement.setString(2, data.getString("roll_no"));
			preparedStatement.executeUpdate();
			return "success";
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "fail";

	}
	
	//API to update pic_location of user
	
	public String updatePicture(JSONObject data) throws Exception{
		java.sql.PreparedStatement preparedStatement = null;
		try {
			query = "update student set pic_location = ? where roll_no =?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, data.getString("pic_location"));
			preparedStatement.setString(2, data.getString("roll_no"));
			preparedStatement.executeUpdate();
			return "success";
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "fail";

	}
	
	//API to update marks
	
	public String updateMarks(JSONObject data) throws Exception{
		java.sql.PreparedStatement preparedStatement = null;
		try {
			query = "update student set marks = ? where roll_no =?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setFloat(1, (float)data.getDouble("marks"));
			preparedStatement.setString(2, data.getString("roll_no"));
			preparedStatement.executeUpdate();
			return "success";
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "fail";

	}
	
	//API to update password
	
	public String updatePassword(JSONObject data) throws Exception{
		java.sql.PreparedStatement preparedStatement = null;
		try {
			query = "update student set password = ? where roll_no =?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, data.getString("password"));
			preparedStatement.setString(2, data.getString("roll_no"));
			preparedStatement.executeUpdate();
			return "success";
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "fail";

	}
	
	public String calculateGrade(int m)
	{
		String grade="";
		if(m >=90) 
		{
			grade="A";
		}
		else if(m<90 && m >=80) 
		{
			grade="B";
		}
		else if(m<80 && m >=70)
		{
			grade="C";
		}
		else if(m<70) 
		{
			grade="D";
		}
		return grade;
	}
	//To get the list of the users between the given roll numbers
		public String getUsers(String r1, String r2,int present_count) throws Exception{
			java.sql.PreparedStatement preparedStatement=null;
			JSONObject result = new JSONObject();
			try {
				// SELECT * FROM `msgtable` WHERE `cdate`='18/07/2012' LIMIT 10 OFFSET 10
				//query = "select pic_location, YEAR(dob) as year, MONTH(dob) as month, DAY(dob) as day, name,roll_no,marks from student"
				//		+ " where roll_no >= ? and roll_no <= ? order by roll_no ASC LIMIT ?,5";
				query = "select pic_location, YEAR(dob) as year, MONTH(dob) as month, DAY(dob) as day, name,roll_no,marks from student"
						+ " where roll_no >= ? and roll_no <= ? order by roll_no ASC";
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, r1);
				preparedStatement.setString(2, r2);
				//preparedStatement.setInt(3,present_count);
				resultSet = preparedStatement.executeQuery();
				JSONArray users = new JSONArray();
				while(resultSet.next()) {
					JSONObject user = new JSONObject();
					user.put("roll_no",resultSet.getString("roll_no"));
					user.put("name",resultSet.getString("name"));
					user.put("marks",resultSet.getString("marks"));
					user.put("grade",calculateGrade(resultSet.getInt("marks")));
					user.put("pic_location",resultSet.getString("pic_location"));
					user.put("dob",resultSet.getString("day")+"-"+resultSet.getString("month")+"-"+resultSet.getString("year"));
					users.put(user);
				}
				result.put("result", "success");
				result.put("users", users);
				return result.toString();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			result.put("result", "fail");
			return result.toString();
		}
		
		//To get the list of the users between the given roll numbers ordered by name
		public String getUsersOrderByName(String r1, String r2,int present_count) throws Exception{
			java.sql.PreparedStatement preparedStatement=null;
			JSONObject result = new JSONObject();
			try {
				// SELECT * FROM `msgtable` WHERE `cdate`='18/07/2012' LIMIT 10 OFFSET 10
				//query = "select pic_location, YEAR(dob) as year, MONTH(dob) as month, DAY(dob) as day, name,roll_no,marks from student"
				//		+ " where roll_no >= ? and roll_no <= ? order by name ASC LIMIT ?,5";
				query = "select pic_location, YEAR(dob) as year, MONTH(dob) as month, DAY(dob) as day, name,roll_no,marks from student"
						+ " where roll_no >= ? and roll_no <= ? order by name ASC";
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, r1);
				preparedStatement.setString(2, r2);
				//preparedStatement.setInt(3,present_count);
				resultSet = preparedStatement.executeQuery();
				JSONArray users = new JSONArray();
				while(resultSet.next()) {
					JSONObject user = new JSONObject();
					user.put("roll_no",resultSet.getString("roll_no"));
					user.put("name",resultSet.getString("name"));
					user.put("marks",resultSet.getString("marks"));
					user.put("grade",calculateGrade(resultSet.getInt("marks")));
					user.put("pic_location",resultSet.getString("pic_location"));
					user.put("dob",resultSet.getString("day")+"-"+resultSet.getString("month")+"-"+resultSet.getString("year"));
					users.put(user);
				}
				result.put("result", "success");
				result.put("users", users);
				return result.toString();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			result.put("result", "fail");
			return result.toString();
		}
		
		//To get the list of the users between the given roll numbers ordered by marks
		public String getUsersOrderByMarks(String r1, String r2,int present_count) throws Exception{
			java.sql.PreparedStatement preparedStatement=null;
			JSONObject result = new JSONObject();
			try {
				// SELECT * FROM `msgtable` WHERE `cdate`='18/07/2012' LIMIT 10 OFFSET 10
				//query = "select pic_location, YEAR(dob) as year, MONTH(dob) as month, DAY(dob) as day, name,roll_no,marks from student"
				//		+ " where roll_no >= ? and roll_no <= ? order by marks ASC LIMIT ?,5";
				query = "select pic_location, YEAR(dob) as year, MONTH(dob) as month, DAY(dob) as day, name,roll_no,marks from student"
						+ " where roll_no >= ? and roll_no <= ? order by marks DESC";
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, r1);
				preparedStatement.setString(2, r2);
				//preparedStatement.setInt(3,present_count);
				resultSet = preparedStatement.executeQuery();
				JSONArray users = new JSONArray();
				while(resultSet.next()) {
					JSONObject user = new JSONObject();
					user.put("roll_no",resultSet.getString("roll_no"));
					user.put("name",resultSet.getString("name"));
					user.put("marks",resultSet.getString("marks"));
					user.put("grade",calculateGrade(resultSet.getInt("marks")));
					user.put("pic_location",resultSet.getString("pic_location"));
					user.put("dob",resultSet.getString("day")+"-"+resultSet.getString("month")+"-"+resultSet.getString("year"));
					users.put(user);
				}
				result.put("result", "success");
				result.put("users", users);
				return result.toString();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			result.put("result", "fail");
			return result.toString();
		}
		
		//To get the list of the users between the given roll numbers ordered by dob
		public String getUsersOrderByDOB(String r1, String r2,int present_count) throws Exception{
			java.sql.PreparedStatement preparedStatement=null;
			JSONObject result = new JSONObject();
			try {
				// SELECT * FROM `msgtable` WHERE `cdate`='18/07/2012' LIMIT 10 OFFSET 10
				//query = "select pic_location, YEAR(dob) as year, MONTH(dob) as month, DAY(dob) as day, name,roll_no,marks from student"
				//		+ " where roll_no >= ? and roll_no <= ? order by dob ASC LIMIT ?,5";
				query = "select pic_location, YEAR(dob) as year, MONTH(dob) as month, DAY(dob) as day, name,roll_no,marks from student"
						+ " where roll_no >= ? and roll_no <= ? order by dob ASC";
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, r1);
				preparedStatement.setString(2, r2);
				//preparedStatement.setInt(3,present_count);
				resultSet = preparedStatement.executeQuery();
				JSONArray users = new JSONArray();
				while(resultSet.next()) {
					JSONObject user = new JSONObject();
					user.put("roll_no",resultSet.getString("roll_no"));
					user.put("name",resultSet.getString("name"));
					user.put("marks",resultSet.getString("marks"));
					user.put("grade",calculateGrade(resultSet.getInt("marks")));
					user.put("pic_location",resultSet.getString("pic_location"));
					user.put("dob",resultSet.getString("day")+"-"+resultSet.getString("month")+"-"+resultSet.getString("year"));
					users.put(user);
				}
				result.put("result", "success");
				result.put("users", users);
				return result.toString();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			result.put("result", "fail");
			return result.toString();
		}
		//Get messages received by an user when his roll number is given
		public String getMsgs(int s_id) throws Exception{
			java.sql.PreparedStatement preparedStatement=null;
			JSONObject result = new JSONObject();
			try {
				query = "select m.msg, m.sender_id, m.receiver_id, r.name, s.name from message m, student r, student s where (m.sender_id=? OR m.receiver_id=? ) and m.sender_id = s.student_id and m.receiver_id = r.student_id";
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, s_id);
				preparedStatement.setInt(2, s_id);
				resultSet = preparedStatement.executeQuery();
				JSONArray msgs = new JSONArray();
				while(resultSet.next()) {
					
					JSONObject msg = new JSONObject();
					int sender_id = resultSet.getInt("m.sender_id");
					int reciever_id = resultSet.getInt("m.receiver_id");
					String message = resultSet.getString("m.msg");
					msg.put("message", message);
					if(sender_id==s_id)
					{
						msg.put("dir", "sent");
						msg.put("name",resultSet.getString("r.name"));
						
					}
					else
					{
						msg.put("dir", "recieved");
						msg.put("name",resultSet.getString("s.name"));

					}
					msgs.put(msg);
				}
				result.put("result", "success");
				result.put("msgs", msgs);
				return result.toString();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			result.put("result", "fail");
			return result.toString();
		}

		//Add messages received by a sender when the roll number of the receiver and the message are given
		public String addMsg(String sender_rn, String recv_rn, String msg) throws Exception{
			java.sql.PreparedStatement preparedStatement=null;
			JSONObject result = new JSONObject();
			try {
				query = "insert into message values(message_id,?,?,?)";
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, getStudent_id(sender_rn));
				preparedStatement.setInt(2, getStudent_id(recv_rn));
				preparedStatement.setString(3, msg);
				preparedStatement.execute();
				result.put("result", "success");
				return result.toString();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			result.put("result", "fail");
			return result.toString();
		}	
		
		public int getStudent_id(String roll_no) throws Exception{
			java.sql.PreparedStatement preparedStatement=null;
			int result=-1;
			try {
				query = "select student_id from student where roll_no=?";
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, roll_no);
				resultSet = preparedStatement.executeQuery();
				if(resultSet.next()) {
					result = resultSet.getInt("student_id");
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			return result;
		}
		
		public String getRoll_no(int student_id) throws Exception{
			java.sql.PreparedStatement preparedStatement=null;
			String result=null;
			try {
				query = "select roll_no from student where student_id=?";
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, student_id);
				resultSet = preparedStatement.executeQuery();
				if(resultSet.next()) {
					result = resultSet.getString("roll_no");
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			return result;

		}
		
		public String getName(int student_id) throws Exception{
			java.sql.PreparedStatement preparedStatement=null;
			String result=null;
			try {
				query = "select name from student where student_id=?";
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, student_id);
				resultSet = preparedStatement.executeQuery();
				if(resultSet.next()) {
					result = resultSet.getString("name");
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			return result;

		}
		
		
}
