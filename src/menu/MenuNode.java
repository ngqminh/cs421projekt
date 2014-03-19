package menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

/**
 * Created by jeffrey on 3/19/2014.
 */
public class MenuNode implements MenuItem{

    private String title;
    private String prompt;

    LinkedList<MenuItem> tree;
    MenuLeaf selectedItem;

    public MenuNode() {
        this('A',"Default","Default prompt");
    }

    public MenuNode(String title, String Prompt) {
        this('A',title,Prompt);
    }

    public MenuNode(char label, String title, String prompt) {
        this.setTitle(title);
        this.setPrompt(prompt);

        this.selectedItem = new MenuLeaf();
        selectedItem.setLabel(label);
        selectedItem.setDescription(title);

        this.tree = new LinkedList<MenuItem>();
    }



    //Used when Menu is an item
    public void display() {
        selectedItem.display();
    }

    //Displays prompt
    private void displayPrompt() {
        System.out.println("");
        System.out.print(this.prompt);

    }
    //Displays Menu
    private void displayMenu() {

        System.out.println(this.title);
        for (MenuItem item:tree) {
            item.display();
        }
        displayPrompt();
    }

    private char readChoice() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String choice = br.readLine();
        return choice.charAt(0);
    }

    public void addMenuItem(MenuItem item) {
        tree.add(item);
    }

    public MenuItem askUser() {
        MenuItem chosen = null;
        displayMenu();

        while (chosen == null) {
            char choice = 'Z';
            try {
                choice = readChoice();
            } catch (IOException e) {
                System.out.println("Error reading choice");
            }
            for (MenuItem item : tree) {
                char label = Character.toLowerCase(item.getLabel());
                if (label == Character.toLowerCase(choice)) {
                    chosen = item;
                    break;
                }
            }

            if (chosen == null) {
                System.out.println("This was an invalid choice");
                displayPrompt();
            }
        }

        return chosen;
    }

    public char getLabel() {
        return selectedItem.getLabel();
    }

    public void setLabel(char label) {
        selectedItem.setLabel(label);
    }

    public String getDescription() {
        return selectedItem.getDescription();
    }

    public void setDescription(String desc) {
        selectedItem.setDescription(desc);
    }

    public boolean isNode() {
        return true;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
