package models;

import javax.xml.transform.Result;
import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created by jeffrey on 3/23/2014.
 */
public class Transaction {

    double processingFee;

    private Connection con;
    private int ticketID;
    private PreparedStatement pState;

    private int transactionID;

    public Transaction(Connection con,int ticketID, double fee) {
        this.con = con;
        this.ticketID = ticketID;
        this.processingFee = fee;
    }

    public void create(String email) throws SQLException{
        try {

            //Need to handle big decimal.
            pState = con.prepareStatement("SELECT price from ticket WHERE ticket_ID=?");
            pState.setInt(1, this.ticketID);
            BigDecimal price = BigDecimal.ZERO;
            ResultSet rs = pState.executeQuery();
            while (rs.next()) {
                price = rs.getBigDecimal(1);
            }
            generateTransactionID();
            pState.close();

            BigDecimal b = new BigDecimal(processingFee, MathContext.DECIMAL64);

            Date sqlDate = new Date((new java.util.Date()).getTime());
            pState = con.prepareStatement("INSERT INTO transaction VALUES(?, ?, ?, ? ,?, ?)");
            pState.setInt(1,this.transactionID);
            pState.setInt(2,this.ticketID);
            pState.setString(3,email);
            pState.setDate(4,sqlDate);
            pState.setBigDecimal(5, price);
            pState.setBigDecimal(6, b);
            pState.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error Creating new Transaction");
            throw e;
        } finally {
            pState.close();
        }

    }

    private void generateTransactionID() throws SQLException{
        try {
            pState=con.prepareStatement("SELECT MAX(Transaction_ID) from transaction");
            ResultSet rs = pState.executeQuery();
            int id = 0;
            while (rs.next()) {
                id = rs.getInt(1);
            }
            this.transactionID = ++id;
        } catch (SQLException e) {
            System.out.println("Error with Generating Transaction ID");
            throw e;
        } finally {
            pState.close();
        }
    }


}
