package menu;

import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * Represents a Menu Section containing Menu Items of the same type
 * 
 * @author Hanzhi
 * @version 1.0
 * @since 2021-11-13
 */
public class MenuSection {

    /**
     *  The ID number of this MenuSection
     */
    private int menuSectionID;
    /**
     *  The name of this MenuSection
     */
    private String name;
    /**
     * The list of MenuItems in this MenuSection
     */
    private ArrayList<MenuItem> menuItems;

    /**
     * Creates a default new MenuSection with ID -1 and no details.
     */
    public MenuSection(){
        this.menuSectionID = -1;
        this.name = "no section name";
        this.menuItems = new ArrayList<MenuItem>();
    }
    /**
     * Creates a new MenuSection with given ID and name.
     * @param menuSectionID     This MenuSection's ID number.
     * @param name              This MenuSection's name.
     */
    public MenuSection(int menuSectionID, String name){
        this.menuSectionID = menuSectionID;
        this.name = name;
        this.menuItems = new ArrayList<MenuItem>();
    }

    /**
     * Get the ID number of this MenuSection
     * @return this MenuSection ID number
     */
    public int getMenuSectionID(){
        return this.menuSectionID;
    }
    /**
     * Change the ID number of this MenuSection
     * @param menuSectionID the MenuSection new ID number
     * @return true when successful, othwerwise false
     */
    public boolean setMenuSectionID(int menuSectionID){
        try{
            this.menuSectionID = menuSectionID;
            return true;
        }  catch(InputMismatchException e){
            return false;
        }
    }
    /**
     * Get the name of this MenuSection
     * @return this MenuSection's name
     */
    public String getName(){
        return this.name;
    }
    /**
     * Change the name of this MenuSection
     * @param name the MenuSection new name
     */
    public void setName(String name){
        this.name = name;
    }
    /**
     * Get the list of MenuItems in this MenuSection
     * @return  the ArrayList of MenuItems in this MenuSection
     */
    public ArrayList<MenuItem> getMenuItems(){
        return this.menuItems;
    }
    /**
     * Add MenuItem to this MenuSection
     * @param menuItem the MenuItem to add into the MenuSection
     * @return true when successful, othwerwise false
     */
    public boolean addMenuItem(MenuItem menuItem){
        for(MenuItem item : menuItems)
            if(item == menuItem)
                return false;

        menuItems.add(menuItem);
        return true;
    }
    /**
     * Remove the MenuItem from this MenuSection
     * @param menuItem the MenuItem to remove from the MenuSection
     * @return true when successful, othwerwise false
     */
    public boolean removeMenuItem(MenuItem menuItem){
        for(MenuItem item : menuItems){
            if(item == menuItem){
                this.menuItems.remove(menuItem);
                return true;
            }
        }
        return false;
    }



}
