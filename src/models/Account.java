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

            PreparedStatement pState2 = con.prepareStatement("SELECT email FROM attendee where email=?");
            pState2.setString(1,this.email);
            ResultSet rs2 = pState2.executeQuery();
            while (rs2.next()) {
                if (rs2.getString(1).equals(this.email)) {
                    attendee = true;
                } else {
                    attendee = false;
                }
            }


            PreparedStatement pState3 = con.prepareStatement("SELECT email FROM organizer where email=?");
            pState3.setString(1,this.email);
            ResultSet rs3 = pState3.executeQuery();
            while (rs3.next()) {
                if (rs3.getString(1).equals(this.email)) {
                    organizer = true;
                } else {
                    organizer = false;
                }
            }

            pState.close();
            pState2.close();
            pState3.close();

        } catch (SQLException e) {
            System.out.println("Error Verifying account");
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
