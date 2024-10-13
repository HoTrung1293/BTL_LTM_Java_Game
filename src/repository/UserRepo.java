package repository;

import interfaces.IUserRepo;
import entity.*;
import java.util.ArrayList;
public class UserRepo implements IUserRepo{
    DatabaseConnection dbc;
	
	public UserRepo()
	{
		dbc = new DatabaseConnection();
	}
	
	public void insertInDB(User u)
	{
                String query1 = "INSERT INTO users (id, username, password, status) VALUES ('"+u.getId()+"','"+u.getUsername()+"','"+u.getPassword()+"','"+u.getStatus()+"');";
                String query2 = "INSERT INTO userinfo (id, gender, rating, avg_competitor, avg_time, total_match, win, lose, draw) VALUES ('"+u.getId()+"','"+u.getGender()+"','"+u.getRating()+"','"+u.getAvgCompetitor()+"','"+u.getAvgTime()+"','"+u.getTotalMatch()+"','"+u.getWin()+"','"+u.getLose()+"','"+u.getDraw()+"');";

		try
		{
			dbc.openConnection();
			dbc.st.execute(query1);
                        dbc.st.execute(query2);
			dbc.closeConnection();
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	public void deleteFromDB(String id)
	{
		String query = "DELETE from users WHERE Id='"+id+"';";
		try
		{
			dbc.openConnection();
			dbc.st.execute(query);
			dbc.closeConnection();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	public void updateInDB(User u)
	{
		String query = "UPDATE userinfo SET gender='"+u.getGender()+"', rating= '"+u.getRating()+"', avg_competitor = '"+u.getAvgCompetitor()+"', avg_time= '"+u.getAvgTime()+"', total_match= '"+u.getTotalMatch()+"', win= '"+u.getWin()+"', lose= '"+u.getLose()+"', draw= '"+u.getDraw()+"' WHERE id='"+u.getId()+"'";
		try
		{
			dbc.openConnection();
			dbc.st.executeUpdate(query);
			dbc.closeConnection();
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	public User searchUser(String id)
	{
		User u = null;
		String query = "SELECT `gender`, `rating`, `avg_competitor`, `avg_time`, `total_match`, `win`, `lose`, `draw` FROM `userinfo` WHERE `id`='"+id+"';";     
		try
		{
		
			dbc.openConnection();
			dbc.result = dbc.st.executeQuery(query);
			while(dbc.result.next())
			{
				
				String gender = dbc.result.getString("gender");
				int rating= dbc.result.getInt("rating");
                                int avg_competitor= dbc.result.getInt("avg_competitor");
				int avg_time= dbc.result.getInt("avg_time");
                                int total_match=dbc.result.getInt("total_match");
                                int win=dbc.result.getInt("win");
                                int lose=dbc.result.getInt("lose");
                                int draw=dbc.result.getInt("draw");
				u = new User();
				u.setId(id);
                                u.setGender(gender);
                                u.setRating(rating);
                                u.setAvgCompetitor(avg_competitor);
                                u.setAvgTime(avg_time);
                                u.setTotalMatch(total_match);
                                u.setWin(win);
                                u.setLose(lose);
                                u.setDraw(draw);
			}
			
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		dbc.closeConnection();
		return u;
	}
	public String[][] getAllUser()
	{
		ArrayList<User> ar = new ArrayList<User>();
		String query = "SELECT * FROM users;";  
		try
		{
			dbc.openConnection();
			dbc.result = dbc.st.executeQuery(query);
			while(dbc.result.next())
			{
				String id = dbc.result.getString("id");
				String username=dbc.result.getString("username");
                                String status=dbc.result.getString("status");
				User u = new User();
				u.setId(id);
                                u.setUsername(username);
                                u.setStatus(status);
				ar.add(u);
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		dbc.closeConnection();
		
		
		Object obj[] = ar.toArray();
		String data[][] = new String[ar.size()][2];
		
		for(int i=0;i<obj.length;i++)
		{
			data[i][0] = ((User)obj[i]).getUsername();
			data[i][1] = ((User)obj[i]).getStatus();
		}
		return data;
                
	}public User getUserLogin(String username, String password)
	{
		User u = null;
		String query = "Select * from users where username = '"+username+"' AND password = '"+password+"';";
		try
		{
			System.out.println(query);
			dbc.openConnection();
			dbc.result = dbc.st.executeQuery(query);
		
			while(dbc.result.next())
			{
				u = new User();
				u.setId(dbc.result.getString("id"));  /*String uid = dbc.result.getString("userId");user.setUserId(uid);*/
                                u.setUsername(dbc.result.getString("username"));
				u.setPassword(dbc.result.getString("password"));
				u.setStatus(dbc.result.getString("status"));
			}
		}
        catch(Exception ex)
		{
			System.out.println("Exception1 : " +ex.getMessage());
        }
		dbc.closeConnection();
		return u;
	}
        
        public User getUserInfo(String username)
	{
		User u = null;
                String id = null;
                String query1 = "Select `id`, `username` from users where `username`='"+username+"';";	   
		try
		{
		
			dbc.openConnection();
                        dbc.result = dbc.st.executeQuery(query1);
                        while(dbc.result.next())
			{
				u=new User();
				id = dbc.result.getString("id");
                                u.setId(id);
                                u.setUsername(dbc.result.getString("username"));
			}
                        String query = "SELECT `gender`, `rating`, `avg_competitor`, `avg_time`, `total_match`, `win`, `lose`, `draw` FROM `userinfo` WHERE `id`='"+id+"';";  
   			dbc.result = dbc.st.executeQuery(query);
			while(dbc.result.next())
			{
				
				String gender = dbc.result.getString("gender");
				int rating= dbc.result.getInt("rating");
                                int avg_competitor= dbc.result.getInt("avg_competitor");
				int avg_time= dbc.result.getInt("avg_time");
                                int total_match=dbc.result.getInt("total_match");
                                int win=dbc.result.getInt("win");
                                int lose=dbc.result.getInt("lose");
                                int draw=dbc.result.getInt("draw");
				//u.setId(id);
                                u.setGender(gender);
                                u.setRating(rating);
                                u.setAvgCompetitor(avg_competitor);
                                u.setAvgTime(avg_time);
                                u.setTotalMatch(total_match);
                                u.setWin(win);
                                u.setLose(lose);
                                u.setDraw(draw);
			}                      
			
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		dbc.closeConnection();
		return u;
	}
}
