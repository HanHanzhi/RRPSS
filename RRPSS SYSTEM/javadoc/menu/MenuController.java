package menu;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * MenuController coordinates ItemController and PromotionController
 * 
 * @author Nicholas
 * @version 1.0
 * @since 2021-11-13
 */
public class MenuController {
    
    static MenuController menuControllerInstance;
    /**
     * The restaurant food Menu
     */
    private Menu menu;
    /**
     * The ItemController
     */
    private ItemController ic;
    /**
     * The PromotionControler
     */
    private PromotionController pc;
    
    /**
     * Constructs a MenuController
     */
    public MenuController(){
        this.menu = Menu.getInstance();
        this.ic = ItemController.getInstance();
        this.pc = PromotionController.getInstance();

    }
    /**
     * Get shared singleton instance
     * @return singleton MenuController
     */
    public static MenuController getInstance(){
		if(menuControllerInstance==null){
			menuControllerInstance = new MenuController();
		}
		return menuControllerInstance;
	}
    /**
     * Print restaurant Menu
     */
    public void printMenu(){
        this.menu.printMenu();
    }
    /**
     * Create/Update/Remove MenuItems from restaurant Menu by requesting user input
     */
    public void manageMenuItems(){
        Scanner sc = new Scanner(System.in);
        try{
            System.out.println(
                " - - - -Manage Menu Item- - - -\n"+
                "(1)Create  (2)Update  (3)Remove");
            System.out.print("Enter Choice: ");
            int choice = sc.nextInt();
            switch(choice){
                case 1:
                    ic.createMenuItem();
                    break;
                case 2:
                    ic.updateMenuItem();
                    break;
                case 3:
                    ic.removeMenuItem();
                    break;
                default:
                    System.out.println(" - Invalid Choice - ");
                    break;
            }
        } catch(InputMismatchException e){
            sc.nextLine();
            System.out.println(" - Invalid Input - ");
        }
        return;
    }
    /**
     * Create/Update/Remove Promotions from restaurant Menu by requesting user input
     */
    public void managePromotions(){
        Scanner sc = new Scanner(System.in);
        try{
            System.out.println(
                " - - - -Manage Promotion- - - -\n"+
                "(1)Create  (2)Update  (3)Remove");
            System.out.print("Enter Choice: ");
            int choice = sc.nextInt();
            switch(choice){
                case 1:
                    pc.createPromotion();
                    break;
                case 2:
                    pc.updatePromotion();
                    break;
                case 3:
                    pc.removePromotion();
                    break;
                default:
                    System.out.println(" - Invalid Choice - ");
                    break;
            }
        } catch(InputMismatchException e){
            sc.nextLine();
            System.out.println(" - Invalid Input - ");
        }
        return;
    }
    /**
     * Get MenuItem from Menu
     * @param menuItemID the MenuItem ID number
     * @return the MenuItem if successful, otherwise null
     */
    public MenuItem findMenuItem(int menuItemID){
        return menu.findMenuItem(menuItemID);
    }
    /**
     * Get Promotion from Menu
     * @param promoID the Promotion ID number
     * @return  the Promotion if successful, otherwise null
     */
    public Promotion findPromotion(int promoID){
        return menu.findPromotion(promoID);
    }

    


}
