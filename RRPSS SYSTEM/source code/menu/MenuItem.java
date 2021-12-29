package menu;

import java.util.InputMismatchException;

/**
 * Represents a Menu Item in the Restaurant
 * 
 * @author Hanzhi
 * @version 1.0
 * @since 2021-11-13
 */
public class MenuItem {

    /**
     * The ID number of this MenuItem.
     */
	private int menuItemID;
    /**
     * The name of this MenuItem.
     */
	private String name;
    /**
     * The description of this MenuItem.
     */
	private String description;
    /**
     * The price of this MenuItem
     */
	private double price;

    /**
     * Creates a default new MenuItem with ID number -1 and no details.
     */
    public MenuItem(){
        this.menuItemID = -1;
        this.name = "no item name";
        this.description = "no description";
        this.price = 0.0;
    }
    /**
     * Create a new MenuItem with given ID, name and price.
     * @param menuItemID    This MenuItem's ID number.
     * @param name          This MenuItem's name.
     * @param description   This MenuItem's description.
     * @param price         This MenuItem's price.
     */
    public MenuItem(int menuItemID, String name, String description, double price){
        this.menuItemID = menuItemID;
        this.name = name;
        this.description = description;
        this.price = price;
    }
    /**
     * Get the ID number of this MenuItem
     * @return this MenuItem ID number
     */
    public int getMenuItemID(){
        return this.menuItemID;
    }
    /**
     * Change the ID number of this MenuItem
     * @param menuItemID the MenuItem new ID number
     * @return true when successful, othwerwise false
     */
    public boolean setMenuItemID(int menuItemID){
        try{
            this.menuItemID = menuItemID;
            return true;
        } catch(InputMismatchException  e){
            return false;
        }
    }
    /**
     * Get the name of this MenuItem
     * @return this MenuItem's name
     */
    public String getName(){
        return this.name;
    }
    /**
     * Change the name of this MenuItem
     * @param name the MenuItem new name
     */
    public void setName(String name){
        this.name = name;
    }
    /**
     * Get the description of this MenuItem
     * @return  the MenuItem's description
     */
    public String getDescription(){
        return this.description;
    }
    /**
     * Change the description of this MenuItem
     * @param description the MenuItem new description
     */
    public void setDescription(String description){
        this.description = description;
    }
    /**
     * Get the price of this MenuItem
     * @return the MenuItem's price
     */
    public double getPrice(){
        return this.price;
    }
    /**
     * Change the price of this MenuItem
     * @param price the MenuItem new price
     * @return
     */
    public boolean setPrice(double price){
        if(price >= 0){
            this.price = price;
            return true;
        }
        return false;
    }
    
    

}
