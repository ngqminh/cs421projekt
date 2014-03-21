package models;

import java.sql.*;

public class Ticket {
	
	private String email;
	private int ticket;
	private Connection con;
	private PreparedStatement state; 
	
	public Ticket (int ticket, String email, Connection con)
	{
		this.ticket = ticket;
		this.email = email;	
		this.con = con;
	}

	public void buyTicket() throws SQLException
	{
		try
		{
			/*state = con.createStatement();
			String query = "UPDATE ticket SET email=" + email +" WHERE ticket_id=" + ticket;
			state.executeUpdate(query);*/
			
			state = con.prepareStatement("UPDATE ticket SET email = ? WHERE ticket_id = ?");
			state.setString(1, email);
			state.setInt(2, ticket);
			state.executeUpdate();
			SQLWarning warn = state.getWarnings();
			if(warn != null)
			{
				if(warn.getSQLState().equals("02000"))
					System.out.println("Ticket was not found \n");
				else
					System.out.println(warn.getMessage());
			}
		}
			
		catch (SQLException e)
		{
			int sqlCode = e.getErrorCode();
			String sqlState = e.getSQLState();
			if(sqlCode == -530 && sqlState.equals("23503"))
				System.out.println("User was not found \n");
		}
		finally
		{
			state.close();
		}
	}

}
