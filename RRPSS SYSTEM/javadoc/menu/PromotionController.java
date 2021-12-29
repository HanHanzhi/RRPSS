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
 * The Promotion Controller manages Promotion
 * 
 * @author Nicholas
 * @version 1.0
 * @since 2021-11-13
 */
public class PromotionController {

    static PromotionController promotionControllerInstance;
    /**
     * The last used ID number for Promotion
     */
    private int lastUsedID;
    /**
     * Construct a PromotionController
     */
    public PromotionController(){
        Menu m = Menu.getInstance();
        this.lastUsedID = 0;
        readPromoData(m);
    }
    /**
     * Get shared singleton instance
     * @return singleton PromotionController
     */
    public static PromotionController getInstance(){
		if(promotionControllerInstance==null){
			promotionControllerInstance = new PromotionController();
		}
		return promotionControllerInstance;
	}

    /**
     * Get MenuItem from restaurant Menu
     * @param m the restaurnt Menu
     * @param menuItemID    the MenuItem ID on Menu
     * @return the MenuItem if successful, othwerise null
     */
    private MenuItem findMenuItem(Menu m, int menuItemID){
        ArrayList<MenuSection> sections = m.getMenuSections();
        for(MenuSection s : sections){
            ArrayList<MenuItem> menuItems = s.getMenuItems();
            for(MenuItem item : menuItems){
                if(item.getMenuItemID() == menuItemID)
                    return item;
            }
        }
        return null;
    }
    /**
     * Get Promotion from restaurant Menu
     * @param m the restaurant Menu
     * @param promoID   the Promotion ID on Menu
     * @return the Promotion if successful, otherwise null
     */
    private Promotion findPromotion(Menu m, int promoID){
        ArrayList<Promotion> promotions = m.getMenuPromotions();
        for(Promotion p : promotions){
            if(p.getPromoID() == promoID)
                return p;
        }
        return null;
    }
    /**
     * Create a new Promotion by requesting for user input
     * @return true if successful, otherwise false
     */
    public boolean createPromotion(){
        Scanner sc = new Scanner(System.in);
        try{
            Menu m = Menu.getInstance();
            
            m.printMenuSection();
            System.out.println("- - - - - - - - Create Promotion - - - - - - - -");

            System.out.print(" Promo Name: ");
            String name = sc.nextLine();

            System.out.print(" Promo Price: ");
            double promoPrice = sc.nextDouble(); sc.nextLine();

            System.out.print(" Promo Description: ");
            String description = sc.nextLine();


            Promotion promotion = new Promotion(++lastUsedID, name, description, promoPrice);
            m.addPromotion(promotion);

            System.out.print("Input No. of items to Add in promo: ");
            int count = sc.nextInt();
            for(int i=0;i<count; i++){
                System.out.print(" Input Item's ID: ");
                int itemID = sc.nextInt();
                MenuItem item = findMenuItem(m, itemID);

                if(item!=null)
                    promotion.addPromoItem(item);
                else{
                    System.out.println(" - Invalid Item -");
                }
            }
            System.out.println(" - Promotion Created -");
            writePromoData(m);
            return true;
        } catch(InputMismatchException e){
            sc.nextLine();
            System.out.println(" - Invalid Input - ");
        }
        return false;
    }
    /**
     * Update Promotion details by requesting user input
     * @return true if successful, otherwise false
     */
    public boolean updatePromotion(){
        Scanner sc = new Scanner(System.in);
        try{
            Menu m = Menu.getInstance();
            
            m.printPromotions();
            System.out.println("- - - - - - - - Update Promotion - - - - - - - -");

            System.out.print(" Input Promotion ID: ");
            int promoID = sc.nextInt();

            Promotion promo = findPromotion(m, promoID);
            if(promo==null) return false;

            System.out.println("Choose detail to update: \n" + "(1)Name  (2)Description  (3)Price  (4)RemoveItem  (5)AddItem");
            System.out.print("Choice: ");
            int choice = sc.nextInt(); sc.nextLine();
            
            switch(choice){
                case 1:
                    System.out.print(" Input new Name: ");
                    promo.setName(sc.nextLine());
                    if(writePromoData(m)) return true;
                    break;
                case 2:
                    System.out.print(" Input new Description: ");
                    promo.setDescription(sc.nextLine());
                    if(writePromoData(m)) return true;
                    break;
                case 3:
                    System.out.print(" Input new Price: ");
                    promo.setPromoPrice(sc.nextDouble());
                    if(writePromoData(m)) return true;
                    break;
                case 4:
                    System.out.print(" Input ItemID to remove: ");
                    int id = sc.nextInt();
                    ArrayList<MenuItem> itemList = promo.getMenuItems();
                    MenuItem itemToRemove = findMenuItem(m, id);
                    if(itemToRemove!=null){
                        itemList.remove(itemToRemove);
                        writePromoData(m);
                        return true;
                    }
                    else {
                        System.out.println(" - No Item Removed - ");
                    }
                    break;
                case 5:
                    m.printMenuSection();
                    System.out.print(" Input itemID to add: ");
                    int itemID = sc.nextInt();
                    MenuItem item = findMenuItem(m, itemID);
                    if(item!=null){
                        promo.addMenuItem(item);
                        writePromoData(m);
                        return true;
                    }
                    else{
                        System.out.println(" - No Item Added - ");
                    }
                    break;
                default:
                    System.out.println(" - Invalid Choice - ");
                    break;
            }
        } catch(InputMismatchException e){
            sc.nextLine();
            System.out.println(" - Invalid Input - ");
        }
        return false;
    }
    /**
     * Remove Promotion from restaurant Menu by requesting user input
     * @return true if successful, otherwise false
     */
    public boolean removePromotion(){
        Scanner sc = new Scanner(System.in);
        try{
            Menu m = Menu.getInstance();
            m.printPromotions();
            System.out.println("- - - - - - - - Remove Promotion - - - - - - - -");

            System.out.print("Input Promotion ID: ");
            int promoID = sc.nextInt();

            Promotion promo = findPromotion(m, promoID);

            if(promo!=null){
                m.removePromotion(promo);
                writePromoData(m);
                return true;
            }
            else{
                System.out.println(" - No Promotion Removed - ");  
            }
        } catch(InputMismatchException e){
            sc.nextLine();
            System.out.println(" - Invalid Input - ");
        }
        return false;
    }
    /**
     * load Promotion data from file to Menu
     * @param m the Menu to load onto
     * @return true if successful, otherwise false
     */
    private boolean readPromoData(Menu m){
        try{
            Scanner sc = new Scanner(new BufferedReader(new FileReader(DataFilePath.PROMOTION_PATH)));
            while(sc.hasNext()){
                String cur[] = sc.nextLine().split(";");
                Promotion promo = new Promotion(Integer.valueOf(cur[0]),cur[1],cur[2],Double.parseDouble(cur[3]));
                for(int i=4;i<cur.length;i++){
                    MenuItem item = findMenuItem(m,Integer.parseInt(cur[i]));
                    if(item!=null) promo.addMenuItem(item);
                }
                m.addPromotion(promo);
                if(promo.getPromoID()>this.lastUsedID) this.lastUsedID = promo.getPromoID(); //get the last used ID value
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;

    }
    /**
     * write Promotion data to file from Menu
     * @param m the Menu to write from
     * @return true if successful otherwise false
     */
    private boolean writePromoData(Menu m){
        try{
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(DataFilePath.PROMOTION_PATH, false)));
            ArrayList<Promotion> promoList = m.getMenuPromotions();
            for(Promotion promo : promoList){
                out.print(promo.getPromoID()+";"+promo.getName()+";"+promo.getDescription()+";"+promo.getPromoPrice()+";");
                for(MenuItem item : promo.getMenuItems()){
                    out.print(item.getMenuItemID()+";");
                }
                out.print("\n");
            }
            out.close();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
