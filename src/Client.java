
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
        connect();
        while (running) {

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
        mainMenu.addMenuItem(new MenuLeaf('3',"Create A New Venue"));
        mainMenu.addMenuItem(new MenuLeaf('4',"List Attending Events For User"));
        mainMenu.addMenuItem(new MenuLeaf('5',"List Created Events For User"));
        mainMenu.addMenuItem(new MenuLeaf('6',"Modify Event"));
        mainMenu.addMenuItem(new MenuLeaf('7',"Quit"));

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
            case '7': query7(); break;
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
                    return;
                }


            } else {
                System.out.println("Error Handing Choice");
                return;
            }

        } catch (IOException e) {
            System.out.println("Error Handling Input. Back to Main Menu");
            return;
        } catch (SQLException e) {
            System.out.println("SQL Error. Back to Main Menu");
        }



    }
    private void query2()
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            System.out.print("Please Input Organizer Email: ");
            String organizerEmail = readInput();
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
            return;
        } catch (ParseException e) {
            System.out.println("Error Handling Input");
            return;
        } catch (SQLException e) {
            System.out.println("SQL Error. Back to Main Menu");
            return;
        }



    }
    private void query3()
    {

    }
    private void query4()
    {
    	String userEmail;

    	System.out.println("Please enter a user email: ");
    	
    	try 
    	{
    		userEmail = readInput();
            Account acc = new Account(userEmail,con);
            acc.getAttendingEvents();
    	} catch (IOException e) {
    		System.out.println("Error Handling Input");
    	} catch (SQLException e) {
            System.out.println("SQL Error. Back to Main Menu");
        }
    }
    private void query5()
    {
    	String userEmail;

    	System.out.println("Please enter a user email: ");
    	
    	try
    	{
    		userEmail = readInput();
            Account acc = new Account(userEmail,con);
            acc.getCreatedEvents();
    	}
    	catch (IOException e) {
    		System.out.println("Error Handling Input");
    	} catch (SQLException e) {
            System.out.println("SQL Error. Back to Main Menu.");
        }
    }
    private void query6()
    {

    }
    private void query7()
    {
        System.out.println("You are now quitting");
        running = false;
        return;
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
            return;
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
            System.out.println("Can't connect");
            return null;
        }

        return con;
    }

    private String readInput() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String choice = br.readLine();
        return choice;
    }

}
