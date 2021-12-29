package menu;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import util.DataFilePath;

/**
 * Item Controller manages the MenuItems and MenuSection
 * 
 * @author Hanzhi
 * @version 1.0
 * @since 2021-11-13
 */
public class ItemController {
    
    static ItemController itemControllerInstance;
    /**
     * The last used ID number for MenuItem
     */
    private int lastUsedID;

    /**
     * Constructs an ItemController
     */
    public ItemController(){
        lastUsedID = 0;

        Menu m = Menu.getInstance();
        m.addMenuSection(new MenuSection(1, "Appetiser"));
        m.addMenuSection(new MenuSection(2, "Mains"));
        m.addMenuSection(new MenuSection(3, "Dessert"));
        m.addMenuSection(new MenuSection(4, "Beverages"));

        readMenuData(m);
    }
    /**
     * Get shared singleton instance
     * @return singleton ItemController
     */
    public static ItemController getInstance(){
		if(itemControllerInstance==null){
			itemControllerInstance = new ItemController();
		}
		return itemControllerInstance;
	}
    /**
     * Get MenuSection from Menu
     * @param m the Menu
     * @param sectionID the MenuSection ID number
     * @return the MenuSection if successful, otherwise null
     */
    private MenuSection findMenuSection(Menu m, int sectionID){
        ArrayList<MenuSection> menuSection = m.getMenuSections();
        for(MenuSection section : menuSection){
            if(section.getMenuSectionID() == sectionID){
                return section;
            }
        }
        System.out.println(" - MenuSection Not Found -");
        return null;
    }
    /**
     * Get the MenuItem from MenuSection
     * @param s the MenuSection
     * @param menuItemID the MenuItem ID number
     * @return the MenuItem if successful, otherwise null
     */
    private MenuItem findMenuItem(MenuSection s, int menuItemID){
        ArrayList<MenuItem> menuItems = s.getMenuItems();
        for(MenuItem item : menuItems){
            if(item.getMenuItemID() == menuItemID)
                return item;
        }
        System.out.println(" - MenuItem Not Found -");
        return null;
    }
    /**
     * Create a new MenuItem by requesting for user input
     * @return true if successful, otherwise false
     */
    public boolean createMenuItem(){
        Scanner sc = new Scanner(System.in);
        try{
            Menu m = Menu.getInstance();
            System.out.println("- - - - - - - - Create Menu Item - - - - - - - -");
            
            ArrayList<MenuSection> menuSections = m.getMenuSections();
            for(MenuSection s : menuSections){
                System.out.print("("+s.getMenuSectionID()+")"+s.getName()+"  ");    // prints avaliable types on menu
            }
            System.out.print("\nChoose Section: ");
            int type = sc.nextInt(); sc.nextLine();         //new item's type 
            
            MenuSection section = findMenuSection(m, type);
            if(section==null) return false;

            System.out.print(" Item Name: ");
            String name = sc.nextLine();
            System.out.print(" Item Description: ");
            String description = sc.nextLine();
            System.out.print(" Price: ");
            double price = sc.nextDouble();

            MenuItem newItem = new MenuItem(++lastUsedID, name, description, price);
            
            if(newItem.getMenuItemID()!=-1){
                section.addMenuItem(newItem);
                writeMenuData(m);
                return true;
            }
            System.out.println(" - MenuItem Not Created - ");
            return false;
        } catch(InputMismatchException e){
            sc.nextLine();
            System.out.println(" - Input Invalid - ");
            return false;
        }
    }
    /**
     * Update a MenuItem by requesting for user input
     * @return true if successful, otherwise false
     */
    public boolean updateMenuItem(){
        Scanner sc = new Scanner(System.in);
        try{
            Menu m = Menu.getInstance();
            
            m.printMenuSection();
            System.out.println("- - - - - - - - Update Menu Item - - - - - - - -");

            ArrayList<MenuSection> menuSections = m.getMenuSections();
            for(MenuSection s : menuSections){
                System.out.print("("+s.getMenuSectionID()+")"+s.getName()+"  ");
            }
            System.out.print("\nChoose Section: ");
            int type = sc.nextInt(); sc.nextLine();
            
            MenuSection originalSection = findMenuSection(m, type);
            if(originalSection==null) return false;

            System.out.print(" Input Item ID: ");
            int itemID = sc.nextInt(); sc.nextLine();

            MenuItem item = findMenuItem(originalSection, itemID);
            if(item==null) return false;

            System.out.println("Choose detail to update:\n" + "(1)Name  (2)Description  (3)Price  (4)Section(Type)");
            System.out.print(" Choice: ");
            int choice = sc.nextInt(); sc.nextLine();

            switch(choice){
                case 1:
                    System.out.print(" Input new Name: ");
                    item.setName(sc.nextLine());
                    if(writeMenuData(m)) return true;
                    break;
                case 2:
                    System.out.print(" Input new Description: ");
                    item.setDescription(sc.nextLine());
                    if(writeMenuData(m)) return true;
                    break;
                case 3:
                    System.out.print(" Input new Price: ");
                    item.setPrice(sc.nextDouble());
                    if(writeMenuData(m)) return true;
                    break;
                case 4:
                    for(MenuSection s : menuSections){
                        System.out.print("("+s.getMenuSectionID()+")"+s.getName()+"  ");
                    }
                    System.out.println(" Input new Section(Type): ");
                    int newSectionID = sc.nextInt();
                    MenuSection newSection = findMenuSection(m, newSectionID);
                    if(newSection!=null){
                        newSection.addMenuItem(item);
                        originalSection.removeMenuItem(item);
                        writeMenuData(m);
                        return true;
                    }
                    break;
                default:
                    System.out.println(" - Invalid Choice - ");
                    break;
            }
            System.out.println(" - MenuItem Not Updated - ");
            return false;

        }catch(InputMismatchException e){
            sc.nextLine();
            System.out.println(" - Invalid Input - ");
            return false;
        }
    }
    /**
     * Remove MenuItem from Menu
     * @return true if successful, otherwise false
     */
    public boolean removeMenuItem(){
        Scanner sc = new Scanner(System.in);
        try{
            Menu m = Menu.getInstance();
            m.printMenuSection();
            System.out.println("- - - - - - - - Remove Menu Item - - - - - - - -");

            ArrayList<MenuSection> menuSections = m.getMenuSections();
            for(MenuSection s : menuSections){
                System.out.print("("+s.getMenuSectionID()+")"+s.getName()+"  ");
            }
            System.out.print("\nChoose Section: ");
            int type = sc.nextInt(); sc.nextLine();
            
            MenuSection section = findMenuSection(m, type);
            if(section == null) return false;

            System.out.print("Input Item ID to remove: ");
            int itemID = sc.nextInt(); sc.nextLine();

            MenuItem item = findMenuItem(section, itemID);

            if(item!=null){
                section.removeMenuItem(item);
                writeMenuData(m);
                return true;
            }
            System.out.println(" - MenuItem Not Removed -");
            return false;
        } catch(InputMismatchException e){
            sc.nextLine();
            System.out.println(" - Invalid Input - ");
            return false;
        }
    }
    /**
     * load MenuItem data from files to Menu
     * @param m the Menu to load onto
     * @return  true if successful, otherwise false
     */
    private boolean readMenuData(Menu m){
        try{
            String[] path = {DataFilePath.APPETISER_PATH,DataFilePath.MAINS_PATH,DataFilePath.DESSERT_PATH,DataFilePath.BEVERAGES_PATH};
            
            for(int i=0;i<path.length;i++){
                Scanner sc = new Scanner(new BufferedReader(new FileReader(path[i])));
                while(sc.hasNext()){
                String cur[] = sc.nextLine().split(";");
                MenuItem item = new MenuItem(Integer.valueOf(cur[0]),cur[1],cur[2],Double.parseDouble(cur[3]));
                m.getMenuSections().get(i).addMenuItem(item);
                if(item.getMenuItemID()>lastUsedID) lastUsedID = item.getMenuItemID();
                }
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    /**
     * write MenuItem data to files from Menu
     * @param m the Menu to write from
     * @return true if successful, otherwise false
     */
    private boolean writeMenuData(Menu m){
        try{
            String[] path = {DataFilePath.APPETISER_PATH,DataFilePath.MAINS_PATH,DataFilePath.DESSERT_PATH,DataFilePath.BEVERAGES_PATH}; 
            
            for(int i=0;i<path.length;i++){
                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path[i], false)));

                ArrayList<MenuItem> itemList = m.getMenuSections().get(i).getMenuItems();
                for(MenuItem item : itemList){
                    out.println(item.getMenuItemID()+";"+item.getName()+";"+item.getDescription()+";"+item.getPrice()+";");
                }
                out.close();
            }
            return true;
        } catch(Exception e){ 
            e.printStackTrace();
        return false;
        }
    }


}
