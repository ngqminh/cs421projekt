package models;

import java.sql.*;
 
public class CreatedEvents
{
	private String email;
	private Connection con;
	
	public CreatedEvents(String email, Connection con)
	{
		this.email = email;
		this.con = con; 
	}
	
	public void getCreatedEvents()
	{
		try
		{
			Statement state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String query = "SELECT title FROM event WHERE email='" + email + "'";
			
			ResultSet events = state.executeQuery(query);
			
			if(events.first() == false)
				System.out.println("No events found for " + email + "\n");
			else
			{	
				events.beforeFirst();
				System.out.println("The events that " + email + " is attending: \n");
				while(events.next())
				{
					System.out.println(events.getString("TITLE"));
				}
				System.out.println();
			}
			events.close();
		}
		
		catch (SQLException e)
		{
			int sqlCode = e.getErrorCode();
			String sqlState = e.getSQLState();
			System.out.println("SQL Code is " + sqlCode + " and state is: " + sqlState);
		}
	}
}
