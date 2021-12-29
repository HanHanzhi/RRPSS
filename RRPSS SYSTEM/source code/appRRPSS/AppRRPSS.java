package appRRPSS;

import java.util.InputMismatchException;
import java.util.Scanner;

import menu.MenuController;
import order.OrderController;
import reservation.ReservationController;
import table.TableController;

/**
 * AppRRPSS is the main Application a restaurant staff interacts with
 * 
 * @author Hanzhi
 * @version 1.0
 * @since 2021-11-13
 */
public class AppRRPSS {

    /**
     * Displays the main interface a restaurant staff interacts with
     * @param args
     */
    public static void main(String args[]){

        Scanner sc = new Scanner(System.in);

        MenuController menuC = MenuController.getInstance();
        ReservationController reservationC = ReservationController.getInstance();
        OrderController orderC = OrderController.getInstance();
        TableController tableC = TableController.getInstance();

        int choice = 0;
        while(true){
            try{
                System.out.println(
                    "=============================================================================\n" +
                    "|               Restaurant Reservation & Point Of Sale System               |\n" +
                    "|---------------------------------------------------------------------------|\n" +
                    "| (1)Display Menu         (2)Manage MenuItems          (3)Manage Promotions |\n" +
                    "| (4)Create Order         (5)View Order                (6)Manage Order      |\n" +
                    "| (7)Create Reservation   (8)Manage Reservation        (9)Check Tables      |\n" +
                    "| (10)Print OrderInvoice  (11)Print SaleRevenueReport  (12)QUIT             |\n" +
                    "=============================================================================" );
                System.out.print("Enter Choice: ");
                choice = sc.nextInt();
                
                switch(choice){
                    case 1:
                        menuC.printMenu();
                        break;
                    case 2:
                        menuC.manageMenuItems();
                        break;
                    case 3:
                        menuC.managePromotions();
                        break;
                    case 4:
                        orderC.createOrder();
                        break;
                    case 5:
                        orderC.viewActiveOrder();
                        break;
                    case 6:
                        orderC.manageOrder();
                        break;
                    case 7:
                        reservationC.createReservation();
                        break;
                    case 8:
                        reservationC.manageReservation();
                        break;
                    case 9:
                        tableC.checkTables();
                        break;
                    case 10:
                        orderC.printInvoice();
                        break;
                    case 11:
                        orderC.generateSalesReport();
                        break;
                    case 12:
                        System.out.println("\nThank you!\nRRPSS Terminating...\n");
                        System.exit(0);
                        break;
                    default:
                        System.out.println(" - Invalid Choice - ");
                        break;
                } 
            }catch (InputMismatchException  e){
                sc.nextLine();
                System.out.println(" - Invalid Choice - ");
            }
        }
    }
}
