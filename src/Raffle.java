import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class Raffle {
    public static void main (String [] args) throws SQLException
    {
// Unique table names.  Either the user supplies a unique identifier as a command line argument, or the program makes one up.
        String tableName = "";
        int sqlCode=0;      // Variable to hold SQLCODE
        String sqlState="00000";  // Variable to hold SQLSTATE
        String username = "cs421g32";
        String your_password = "[orange]22";

        // Register the driver.  You must register the driver before you can use it.
        try {
            DriverManager.registerDriver ( new com.ibm.db2.jcc.DB2Driver() ) ;
        } catch (Exception cnfe){
            System.out.println("Class not found");
        }

        // This is the url you must use for DB2.
        //Note: This url may not valid now !
        String url = "jdbc:db2://db2.cs.mcgill.ca:50000/cs421";
        Connection con = DriverManager.getConnection (url,username,your_password) ;
        Statement statement = con.createStatement ( ) ;
        
        
        ArrayList<String> attendees = new ArrayList<String>();
        
        
        // Querying a table
        try {
            String querySQL = "SELECT email from Attendee";
            java.sql.ResultSet rs = statement.executeQuery ( querySQL ) ;
            while ( rs.next ( ) ) {
                attendees.add(rs.getString(1));
            }
        } catch (SQLException e)
        {
            sqlCode = e.getErrorCode(); // Get SQLCODE
            sqlState = e.getSQLState(); // Get SQLSTATE

            // Your code to handle errors comes here;
            // something more meaningful than a print would be good
            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
        }

        //print out a random winner! how exciting!
        
        Random rand = new Random();
        int winnerIdx = rand.nextInt(attendees.size());
        System.out.println("Raffle winner is: " + attendees.get(winnerIdx));
        


        // Finally but importantly close the statement and connection
        statement.close ( ) ;
        con.close ( ) ;

    }
}
