package menu;

/**
 * Created by jeffrey on 3/19/2014.
 */
public interface MenuItem {

    //Display Item
    public void display();

    //Display Label of item
    public char getLabel();

    //Set Label of item
    public void setLabel(char label);

    //Set
    public String getDescription();

    public void setDescription(String desc);

    //Checks if I am a sub-menu
    public boolean isNode();
}
