package menu;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Represents the Food Menu in Restaurant
 * 
 * @author Hanzhi
 * @version 1.0
 * @since 2021-11-13
 */
public class Menu {

    private static final DecimalFormat df = new DecimalFormat("0.00");
    static Menu menuInstance;
    /**
     * Store list of MenuSections
     */
    private ArrayList<MenuSection> menuSections;
    /**
     * Store list of Promotions
     */
    private ArrayList<Promotion> promotions;
    
    /**
     * Construct a Menu
     */
    public Menu(){
        this.menuSections = new ArrayList<MenuSection>();
        this.promotions = new ArrayList<Promotion>();
    }
    /**
     * Get shared singleton instance
     * @return singleton restaurant Menu
     */
    public static Menu getInstance(){
		if(menuInstance==null){
			menuInstance = new Menu();
		}
		return menuInstance;
	}
    /**
     * Get the list of MenuSections on Menu
     * @return  the ArrayList of MenuSections
     */
    public ArrayList<MenuSection> getMenuSections(){
        return this.menuSections;
    }
    /**
     * Get the list of Promotion on Menu
     * @return the ArrayList of Promotions
     */
    public ArrayList<Promotion> getMenuPromotions(){
        return this.promotions;
    }
    /**
     * Get a MenuItem from Menu
     * @param menuItemID The MenuItem's ID on Menu
     * @return MenuItem if successful, otherwise null
     */
    public MenuItem findMenuItem(int menuItemID){
        for(MenuSection s : menuSections){
            ArrayList<MenuItem> menuItems = s.getMenuItems();
            for(MenuItem item : menuItems){
                if(item.getMenuItemID() == menuItemID)
                    return item;
            }
        }
        return null;
    }
    /**
     * Get Promotion from Menu
     * @param promoID The Promotion's ID on Menu
     * @return  Promotion if successful, otherwise null
     */
    public Promotion findPromotion(int promoID){
        for(Promotion promo : promotions)
            if(promo.getPromoID() ==  promoID)
                return promo;
                
        return null;
    }
    /**
     * Add a MenuSection to Menu
     * @param newSection The MenuSection to be added
     * @return true if successsful, false otherwise
     */
    public boolean addMenuSection(MenuSection newSection){
        for(MenuSection section : menuSections)
            if(section == newSection){
                System.out.println(" - Section Already Exist - ");
                return false;
            }

        menuSections.add(newSection);
        return true;
    }
    /**
     * Add a Promotion to Menu
     * @param newPromo The Promotion to be added
     * @return true if successsful, false otherwise
     */
    public boolean addPromotion(Promotion newPromo){
        for(Promotion promo : promotions)
            if(promo == newPromo){
                System.out.println(" - Promotion Already Exist - ");
                return false;
            }

        promotions.add(newPromo);
        return true;
    }
    /**
     * Remove a Promotion from Menu
     * @param p The Promotion to be removed
     * @return true if successsful, false otherwise
     */
    public boolean removePromotion(Promotion p){
        for(Promotion promo : promotions){
            if(promo == p){
                promotions.remove(p);
                return true;
            }
        }
        return false;
    }
    /**
     * Print restraurnt entire Menu
     */
    public void printMenu(){
        this.printMenuSection();
        this.printPromotions();
        return;
    }
    /**
     * Print MenuSections and the MenuItems on Menu
     */
    public void printMenuSection(){
        System.out.println(
            "---~----------~----------~-MENU ITEMS-~----------~----------~---");
        for(MenuSection section : menuSections){
            System.out.println("--(" +section.getMenuSectionID()+ ")" +section.getName());
            for(MenuItem item : section.getMenuItems()){
                System.out.println("ID: " +item.getMenuItemID()+ "\t  Name: " +item.getName()+ "\t  $" +df.format(item.getPrice())+ "\t  Description: " +item.getDescription());
            }
        }
        System.out.println();
        return;
    }
    /**
     * Print Promotions on Menu
     */
    public void printPromotions(){
        System.out.println(
            "---*----------*----------*-PROMOTIONS-*----------*----------*---");
        for(Promotion promo : promotions){
            System.out.println("--(ID:" +promo.getPromoID()+ ")" +promo.getName()+" $" +df.format(promo.getPromoPrice())+"\tDescription: " + promo.getDescription());
            for(MenuItem item : promo.getMenuItems()){
                System.out.println("Item(ID:"+item.getMenuItemID()+"): " +item.getName()+ "\t  Description: " +item.getDescription());
            }
            System.out.println();
        }
        return;
    }
}
