
import menu.*;
import models.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jeffrey on 3/19/2014.
 */

public class Client {

    MenuNode mainMenu;
    Boolean running;
    static Connection con;
    Statement state;

    Boolean verified;
    String verifiedEmail;

    public Client() {
        createMainMenu();
    }

    public static void main(String[] args) {
        Client myClient = new Client();
        myClient.clientEngine();

    }

    private void clientEngine()
    {
        running = true;
        verified = false;
        connect();
        while (running) {
            if (verified) {
                System.out.println("Logged in as "+ verifiedEmail);
            }
            MenuItem selected = mainMenu.askUser();
            char choice = selected.getLabel();
            System.out.println("\nYou selected: " + choice);
            HandleSelection(choice);
        }
    }

    private void createMainMenu(){
        MenuNode mainMenu = new MenuNode("Event DB",">");

        mainMenu.addMenuItem(new MenuLeaf('1',"Create A New User"));
        mainMenu.addMenuItem(new MenuLeaf('2',"Create A New Event"));
        mainMenu.addMenuItem(new MenuLeaf('3',"Purchase a Ticket"));
        mainMenu.addMenuItem(new MenuLeaf('4',"List Attending Events For User"));
        mainMenu.addMenuItem(new MenuLeaf('5',"List Created Events For User"));
        mainMenu.addMenuItem(new MenuLeaf('6',"Quit"));

        this.mainMenu = mainMenu;

    }

    private void HandleSelection(char select)
    {
        switch (select) {
            case '1': query1(); break;
            case '2': query2(); break;
            case '3': query3(); break;
            case '4': query4(); break;
            case '5': query5(); break;
            case '6': query6(); break;
            default: break;
        }
    }

    private void query1()
    {
        MenuNode Query1menu = new MenuNode("New Account Menu",">");
        Query1menu.addMenuItem(new MenuLeaf('1',"New Account"));
        Query1menu.addMenuItem(new MenuLeaf('2',"Existing Account"));

        MenuNode Query1SubMenu = new MenuNode("Select Type of Account to Create",">");
        Query1SubMenu.addMenuItem(new MenuLeaf('1',"Attendee"));
        Query1SubMenu.addMenuItem(new MenuLeaf('2',"Organizer"));

        MenuItem selected = Query1menu.askUser();

        String newUserEmail;
        String newUserPassword;
        Account acc;
        System.out.print("Please Insert your Email: ");
        try {
            newUserEmail = readInput();
            char choice = selected.getLabel();
            acc = new Account(newUserEmail,con);
            acc.checkAccount();


            if (choice == '1') {
                if (acc.isExist()) {
                    System.out.println("Account already Exists. Back to Main Menu.");
                    return;
                }
                System.out.print("Please Insert your desired pw: ");
                newUserPassword = readInput();
                acc.createNewAccount(newUserPassword);

            }

            if (choice == '1'|| choice == '2') {
                if (!acc.isExist()) {
                    System.out.println("Account doesn't exist. Create New Account first.");
                    return;
                }
                MenuItem selected2 = Query1SubMenu.askUser();
                char choice2 = selected2.getLabel();


                if (choice2 == '1') {
                    if (acc.isAttendee()) {
                        System.out.println("Attendee already exists. Back to Main Menu.");
                        return;
                    }
                    System.out.print("Please Insert your First Name: ");
                    String fname = readInput();
                    System.out.print("Please Insert your Last Name: ");
                    String lname = readInput();
                    System.out.print("Please Insert your Phone Number: ");
                    String phone = readInput();
                    System.out.print("Please Insert your Home Address: ");
                    String home = readInput();
                    System.out.print("Please Insert your Billing Address: ");
                    String billing = readInput();

                    acc.createAttendeeAccount(fname,lname,phone,home,billing);
                } else if (choice2 == '2') {
                    if (acc.isOrganizer()) {
                        System.out.println("Organizer already exists. Back to Main Menu.");
                        return;
                    }
                    System.out.print("Please Insert your Name: ");
                    String name = readInput();
                    System.out.print("Please Insert your Logo URL: ");
                    String logo = readInput();
                    System.out.print("Please Insert your Description: ");
                    String about = readInput();
                    System.out.print("Please Insert your Website URL: ");
                    String website = readInput();

                    acc.createOrganizerAccount(name, logo, about, website);
                } else {
                    System.out.println("Error Handing Choice");
                }


            } else {
                System.out.println("Error Handing Choice");
            }

        } catch (IOException e) {
            System.out.println("Error Handling Input. Back to Main Menu");
        } catch (SQLException e) {
            System.out.println("SQL Error code: " + e.getErrorCode() + ". Back to Main Menu.");
        }



    }
    private void query2()
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            //System.out.print("Please Input Organizer Email: ");
            String organizerEmail =login();
            // TODO: Check if Organizer exists
            Account acc = new Account(organizerEmail,con);
            acc.checkAccount();
            if (!acc.isOrganizer()) {
                System.out.println("Organizer does not exist. Back to Main Menu.");
                return;
            }

            System.out.print("Please Input Venue ID: ");
            int venueID = Integer.parseInt(readInput());
            // TODO: Check if venue exists
            Venue ven = new Venue(venueID,con);
            if (!ven.isExist()) {
                System.out.println("Venue does not exist. Back to Main Menu.");
                return;
            }

            //Create event
            Event nEvent = new Event(con);

            // Get next available event ID
            nEvent.generateEventID();

            // Fill in Event Data
            System.out.print("Please Input Event Title: ");
            String title = readInput();
            System.out.print("Please Input Event Logo: ");
            String logo = readInput();
            System.out.print("Please Input Event Description: ");
            String desc = readInput();
            System.out.print("Please Input Event Category: ");
            String cat = readInput();
            System.out.print("Please Input Event Start Date(dd/MM/yyyy): ");
            Date sDate = df.parse(readInput());
            System.out.print("Please Input Event End Date(dd/MM/yyyy): ");
            Date eDate = df.parse(readInput());

            nEvent.insertEventData(venueID,organizerEmail,title,logo,desc,cat,sDate,eDate);
        } catch (IOException e) {
            System.out.println("Error Handling Input");
        } catch (ParseException e) {
            System.out.println("Error Handling Input");
        } catch (SQLException e) {
            System.out.println("SQL Error code: " + e.getErrorCode() + ". Back to Main Menu.");
        } catch (verificationException e) {
            System.out.println("Incorrect Password");
        }



    }
    
    private void query3()
    {
    	String userEmail;
    	int ticketID;
    	Ticket bTicket;
        Transaction trans;
        double processingFee = 1.00;
    	
    	try
    	{
    		//System.out.println("Please enter a user email: ");
    		userEmail = login();
    		System.out.println("Please enter the ticket ID: ");
    		ticketID = Integer.parseInt(readInput());
    		bTicket = new Ticket(ticketID, userEmail, con);
    		bTicket.buyTicket();
            trans = new Transaction(con,ticketID,processingFee);
            trans.create(userEmail);

    	}
    	catch (IOException e) {
    		System.out.println("Error Handling Input");
    	}
    	catch(NumberFormatException e) {
    		System.out.println("Invalid Ticket ID \n");
    	}
    	catch(SQLException e) {
    		System.out.println("SQL Error code: " + e.getErrorCode() + ". Back to Main Menu.");
    	} catch (verificationException e) {
            System.out.println("Incorrect Password");
        }
    }
    
    private void query4()
    {
    	String userEmail;

       	//System.out.println("Please enter a user email: ");
    	
    	try 
    	{
    		userEmail = login();
            Account acc = new Account(userEmail,con);
            acc.getAttendingEvents();
    	} catch (SQLException e) {
            System.out.println("SQL Error code: " + e.getErrorCode() + ". Back to Main Menu.");
        } catch (verificationException e) {
            System.out.println("Incorrect Password");
        }
    }
    private void query5()
    {
    	String userEmail;

    	//System.out.println("Please enter a user email: ");
    	
    	try
    	{
    		userEmail = login();
            Account acc = new Account(userEmail,con);
            acc.getCreatedEvents();
    	} catch (SQLException e) {
            System.out.println("SQL Error code: " + e.getErrorCode() + ". Back to Main Menu.");
        } catch (verificationException e) {
            System.out.println("Incorrect Password");
        }
    }
    private void query6()
    {
        System.out.println("You are now quitting");
        running = false;
    }

    public void connect()
    {
        try {
            DriverManager.registerDriver ( new com.ibm.db2.jcc.DB2Driver() ) ;
        } catch (Exception cnfe){
            System.out.println("Unable to register db2 driver");
            return;
        }
        String url = "jdbc:db2://db2.cs.mcgill.ca:50000/cs421";
        try {
            con = DriverManager.getConnection(url, "cs421g32", "[orange]22") ;
            state = con.createStatement ( ) ;
        } catch (SQLException e) {
            System.out.println("Can't connect");
        }
    }

    public static Connection getConnection()
    {
        try {
            DriverManager.registerDriver ( new com.ibm.db2.jcc.DB2Driver() ) ;
        } catch (SQLException e){
            System.out.println("Unable to register db2 driver");
            return null;
        }
        String url = "jdbc:db2://db2.cs.mcgill.ca:50000/cs421";
        try {
            con = DriverManager.getConnection(url, "cs421g32", "[orange]22") ;
        } catch (SQLException e) {
            System.out.println("Can't connect. Closing Client");
            System.exit(0);
        }

        return con;
    }

    private String readInput() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine();
    }

    private String login() throws verificationException{
        String pw;
        if (this.verified) {
            return this.verifiedEmail;
        }
        System.out.println("Please Input your Email: ");
        try {
            this.verifiedEmail = readInput();
            System.out.println("Please Input your Password: ");
            pw = readInput();
        } catch (IOException e) {
            throw new verificationException();
        }

        Account acc = new Account(this.verifiedEmail,con);
        try {
            if (acc.isAuthorized(pw)) {
                this.verified = true;
                return this.verifiedEmail;
            } else {
                throw new verificationException();
            }
        } catch (SQLException e) {
            throw new verificationException();
        }
    }

}
