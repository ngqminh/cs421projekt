package models;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by jeffrey on 3/19/2014.
 */
public class Account {

    private String email;
    private Statement state;

    int newAccountResult;

    public Account(String email, Statement state)
    {
        this.email = email;
        this.state = state;
    }


    public void createNewAccount(String pw)
    {
        try {
            String query = "INSERT INTO account VALUES('" + this.email + "','" + pw +"')"; // THis is query to make new account
            newAccountResult = state.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            int error = e.getErrorCode();
            if (error == -803) {
                System.out.println("User Already Exists");
            } else {
                System.out.println("Error with Creating Account");
            }
        }
    }

    public void createAttendeeAccount(String fname, String lname, String phone, String home, String billing)
    {
        try {
            String query = "INSERT INTO attendee VALUES('" + this.email + "','" + fname + "','" + lname+ "','" + phone+ "','" + home + "','" + billing+"')"; // THis is query to make new account
            newAccountResult = state.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            int error = e.getErrorCode();
            if (error == -803) {
                System.out.println("Attendee User Already Exists");
            } else {
                System.out.println("Error with Creating Organizer Account");
            }
        }
        System.out.println("Successfully Created Attendee");
    }
    public void createOrganizerAccount(String name, String logo, String about, String website)
    {
        try {
            String query = "INSERT INTO organizer VALUES('" + this.email + "','" + name + "','" + logo + "','" + about+ "','" + website+"')"; // THis is query to make new account
            newAccountResult = state.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            int error = e.getErrorCode();
            if (error == -803) {
                System.out.println("Organizer User Already Exists");
            } else {
                System.out.println("Error with Creating Organizer Account");
            }
        }
    }

}
