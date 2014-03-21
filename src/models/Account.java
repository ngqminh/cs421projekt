package models;

import java.sql.*;

/**
 * Created by jeffrey on 3/19/2014.
 */
public class Account {

    private String email;
    private Connection con;
    private boolean exist;
    private boolean attendee;
    private boolean organizer;

    public Account(String email, Connection con)
    {
        this.email = email;
        this.con = con;
        this.exist = false;
        this.attendee = false;
        this.organizer = false;
    }


    public void createNewAccount(String pw) throws SQLException
    {
        try {
            PreparedStatement pState = con.prepareStatement("INSERT INTO account VALUES(?, ?)");
            pState.setString(1,this.email);
            pState.setString(2,pw);
            pState.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            int error = e.getErrorCode();
            if (error == -803) {
                System.out.println("User Already Exists");
            } else {
                System.out.println("Error with Creating Account");
            }
            throw e;
        }
    }

    public void createAttendeeAccount(String fname, String lname, String phone, String home, String billing) throws SQLException
    {
        try {
            PreparedStatement pState = con.prepareStatement("INSERT INTO attendee VALUES(?, ?, ?, ?, ?, ?)");
            pState.setString(1,this.email);
            pState.setString(2,fname);
            pState.setString(3,lname);
            pState.setString(4,phone);
            pState.setString(5,home);
            pState.setString(6,billing);
            pState.execute();
            pState.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            int error = e.getErrorCode();
            if (error == -803) {
                System.out.println("Attendee User Already Exists");
            } else {
                System.out.println("Error with Creating Organizer Account");
            }
            throw e;
        }
        System.out.println("Successfully Created Attendee");
    }
    public void createOrganizerAccount(String name, String logo, String about, String website) throws SQLException
    {
        try {
            PreparedStatement pState = con.prepareStatement("INSERT INTO organizer VALUES(?, ?, ?, ?, ?)");
            pState.setString(1,this.email);
            pState.setString(2,name);
            pState.setString(3,logo);
            pState.setString(4,about);
            pState.setString(5,website);
            pState.execute();
            pState.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            int error = e.getErrorCode();
            if (error == -803) {
                System.out.println("Organizer User Already Exists");
            } else {
                System.out.println("Error with Creating Organizer Account");
            }
            throw e;
        }
    }

    public void checkAccount() throws SQLException
    {
        try {
            checkAccount();
            checkAttendee();
            checkOrganizer();

        } catch (SQLException e) {
            System.out.println("Error Verifying account");
            throw e;
        }
    }

    private void checkExist() throws SQLException{
        PreparedStatement pState = con.prepareStatement("SELECT email FROM account where email=?");
        pState.setString(1,this.email);
        ResultSet rs = pState.executeQuery();

        while (rs.next()) {
            if (rs.getString(1).equals(this.email)) {
                exist = true;
            } else {
                exist = false;
            }
        }
        pState.close();
    }

    private void checkAttendee() throws SQLException{
        PreparedStatement pState = con.prepareStatement("SELECT email FROM attendee where email=?");
        pState.setString(1,this.email);
        ResultSet rs = pState.executeQuery();
        while (rs.next()) {
            if (rs.getString(1).equals(this.email)) {
                attendee = true;
            } else {
                attendee = false;
            }
        }
        pState.close();
    }

    private void checkOrganizer() throws SQLException{
        PreparedStatement pState = con.prepareStatement("SELECT email FROM organizer where email=?");
        pState.setString(1,this.email);
        ResultSet rs = pState.executeQuery();
        while (rs.next()) {
            if (rs.getString(1).equals(this.email)) {
                organizer = true;
            } else {
                organizer = false;
            }
        }
        pState.close();
    }

    public void getAttendingEvents() throws SQLException{
        this.checkAttendee();
        if (!attendee) {
            System.out.println("Account is not an Attendee.");
            return;
        }
        try {
            PreparedStatement pState = con.prepareStatement("SELECT title FROM ticket t, event e WHERE (t.event_id=e.event_id) AND (t.email=?)",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            pState.setString(1,this.email);
            ResultSet rs = pState.executeQuery();

            if(rs.first() == false)
                System.out.println("No events found for attendee " + email + "\n");
            else
            {
                rs.beforeFirst();
                System.out.println("The events that " + email + " is attending: \n");
                while(rs.next())
                {
                    System.out.println(rs.getString("TITLE"));
                }
                System.out.println();
            }

            pState.close();
        } catch (SQLException e) {
            System.out.println("Error getting attending events.");
            throw e;
        }
    }

    public void getCreatedEvents() throws SQLException{
        this.checkOrganizer();
        if (!organizer) {
            System.out.println("Account is not an Organizer.");
            return;
        }
        try {
            PreparedStatement pState = con.prepareStatement("SELECT title FROM event WHERE email=?",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            pState.setString(1,this.email);
            ResultSet rs = pState.executeQuery();

            if(rs.first() == false)
                System.out.println("No events found for organizer " + email + "\n");
            else
            {
                rs.beforeFirst();
                System.out.println("The events that " + email + " created are: \n");
                while(rs.next())
                {
                    System.out.println(rs.getString("TITLE"));
                }
                System.out.println();
            }
            pState.close();
        } catch (SQLException e){
            System.out.println("Error getting created Events.");
            throw e;
        }

    }

    public boolean isOrganizer() {
        return organizer;
    }

    public boolean isExist() {
        return exist;
    }

    public boolean isAttendee() {
        return attendee;
    }

}
