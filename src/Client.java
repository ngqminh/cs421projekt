import menu.MenuItem;
import menu.MenuLeaf;
import menu.MenuNode;

/**
 * Created by jeffrey on 3/19/2014.
 */
public class Client {

    MenuNode mainMenu;
    Boolean running;

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
        while (running) {

            MenuItem selected = mainMenu.askUser();
            System.out.print("\nYou selected: ");
            char choice = selected.getLabel();
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
        }
    }

    private void query1()
    {

    }
    private void query2()
    {

    }
    private void query3()
    {

    }
    private void query4()
    {

    }
    private void query5()
    {

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
}
