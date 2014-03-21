package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jeffrey on 3/21/2014.
 */
public class Venue {

    private Connection con;
    private int venueID;
    public Venue(int venueID, Connection con) {
        this.venueID = venueID;
        this.con = con;

    }

    public boolean isExist() throws SQLException{
        PreparedStatement pState = con.prepareStatement("SELECT venue_id FROM venue where venue_id=?");
        pState.setInt(1, this.venueID);
        ResultSet rs = pState.executeQuery();
        while (rs.next()) {
            if (rs.getInt(1) == this.venueID) {
                return true;
            }
        }
        return false;
    }
}
