package menu;

import java.util.LinkedList;

/**
 * Created by jeffrey on 3/19/2014.
 */
public class MenuLeaf implements MenuItem{


    private char label;
    private String description;


    //Constructor
    public MenuLeaf(){
        this('A',"Description");
    }


    public MenuLeaf(char label, String description)
    {
        this.label = label;
        this.description = description;

    }


    public void display() {
        System.out.println(label + " - " + description);
    }

    public char getLabel() {
        return label;
    }

    public void setLabel(char Label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public boolean isNode() {
        return false;
    }
}
