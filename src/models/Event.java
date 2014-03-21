package models;

import javax.sound.midi.Soundbank;
import java.sql.*;
import java.util.Date;

/**
 * Created by jeffrey on 3/19/2014.
 */
public class Event {

    private int eventID;


    private Connection con;

    public Event(Connection con) {
        this.con = con;
    }


    public void generateEventID() throws SQLException{
        // Get latest Event ID

        try {

            PreparedStatement pState = con.prepareStatement("SELECT Max(event_id) from event");

            String newestEventIDQuery = "SELECT Max(event_id) from event";
            ResultSet rs = pState.executeQuery();
            int id = 0;
            while (rs.next()) {
                id = rs.getInt(1);
            }
            this.eventID = ++id;
        } catch (SQLException e) {
            System.out.println("Error with Generating Event ID");
            throw e;
        }
    }

    public void insertEventData(int venueID, String organizerEmail, String title, String logo, String desc, String cat, java.util.Date sDate, java.util.Date eDate) throws SQLException{

        try {
            PreparedStatement pState = con.prepareStatement("INSERT INTO event VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pState.setInt(1,this.eventID);
            pState.setInt(2,venueID);
            pState.setString(3,organizerEmail);
            pState.setString(4,title);
            pState.setString(5,logo);
            pState.setString(6,desc);
            pState.setString(7,cat);
            pState.setDate(8,new java.sql.Date(sDate.getTime()));
            pState.setDate(9,new java.sql.Date(eDate.getTime()));

            pState.executeUpdate();
            pState.close();
        } catch (SQLException e) {
            System.out.println("Error Inserting Event Data");
            e.printStackTrace();
            throw e;
        }
    }

    public void getEvent() {

    }
}
