package order;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;
import java.util.InputMismatchException;

import util.DataFilePath;
import util.DateUtil;

import menu.MenuController;
import menu.MenuItem;
import menu.Promotion;
import reservation.Reservation;
import reservation.ReservationController;
import staff.StaffController;
import table.TableController;

/**
 * Order Controller manages Order
 * 
 * @author Nicholas
 * @version 1.0
 * @since 2021-11-13
	*/

public class OrderController {

	private static final DecimalFormat df = new DecimalFormat("0.00");
	static OrderController orderControllerInstance;

	private StaffController staffC;
	private MenuController menuC;
	private TableController tableC;
	/**
	 * The last used reference number for creating Order
	 */
	private int lastReferenceNo;
	/**
	 * Store list of Order made
	 */
	private ArrayList<Order> orderList;

	/**
	 * Constructs a OrderController
	 */
	public OrderController(){
		orderList = new ArrayList<Order>();
		this.staffC = StaffController.getInstance();
		this.menuC = MenuController.getInstance();
		this.tableC = TableController.getInstance();

		lastReferenceNo = 0;
		readOrderData();
	}
	/**
	 * Get shared singleton instance
	 * @return
	 */
	public static OrderController getInstance(){  //singleton
		if(orderControllerInstance==null){
			orderControllerInstance = new OrderController();
		}
		return orderControllerInstance;
	}

	/**
	 * Create a new Order by requesting user for input
	 * @return true if successful, false otherwise
	 */
	public boolean createOrder(){
		Scanner sc = new Scanner(System.in);
		try{ 
			staffC.printStaff();
			System.out.print(" Input Staff ID: ");
			int staff = sc.nextInt();

			int table=-1;

			System.out.print(" Has Reservation (Y/N)? ");
			char hasRes = sc.next().charAt(0); sc.nextLine();
			if(hasRes=='Y'||hasRes=='y'){
				System.out.print(" Input Guest Contact No.: ");
				String contact = sc.nextLine();
				Reservation res = ReservationController.getInstance().getByContact(contact);
				if(res!=null){
					Date date = new Date();
					if( date.before(res.getDate()) ) {
						System.out.println(" - Guest too early! Please come at Reservation Time - ");
						return false;
					}
					table = res.getTableNo();
					ReservationController.getInstance().completeReservation(res);
				}
				else{
					System.out.print(" Input Pax: ");
					int pax = sc.nextInt(); sc.nextLine();
					table = tableC.getVacantTable(new Date(), pax);
				}
			}
			else{
				System.out.print(" Input Pax: ");
				int pax = sc.nextInt(); sc.nextLine();
				table = tableC.getVacantTable(new Date(), pax);
			}

			if(table!=-1){
				tableC.occupyTable(table);
				System.out.println(" - Table No. "+table+" assigned -");
			}
			else if(table==-1){
				System.out.println(" - No Tables Avaliable - ");
				return false;
			}

			boolean memberStatus;
			System.out.print(" Has Membership (Y/N)? ");
			char memberInput = sc.next().charAt(0); sc.nextLine();
			if(memberInput=='Y'||memberInput=='y') memberStatus = true;
			else memberStatus = false;

			Date dateTimeNow = new Date();
			
			Order order = new Order(dateTimeNow, ++lastReferenceNo, table, staff, false, memberStatus);
			
			orderList.add(order);

			menuC.printMenu();
			int choice=0;
			while(choice!=3){
				System.out.println(
					" - - - - - - - - - - - - - - - - - - \n" + 
					" (1)MenuItem\t (2)Promotion\t (3)Done\n");
				System.out.print("Enter Choice: ");
				choice = sc.nextInt();
				if(choice==1){
					System.out.print("Input itemID: ");
					MenuItem item = menuC.findMenuItem(sc.nextInt());
					order.addToOrder(item);
				}
				else if(choice==2){
					System.out.print("Input PromoID: ");
					Promotion promo = menuC.findPromotion(sc.nextInt());
					order.addToOrder(promo);
				}
				else if (choice==3){
					System.out.println(" - Order Created - ");
				}
				else{
					System.out.println(" - Invalid Choice - ");
				}
			}
			writeOrderData();
			return true;
		}catch(InputMismatchException e){
			sc.nextLine();
			System.out.println(" - Invalid Input - ");
		}
		return false;
	}
	/**
	 * Print Order details of given Order
	 * @param order The Order to be printed
	 * @return true if successful, false otherwise
	 */
	public boolean viewOrder(Order order){
		if(order!=null){
			System.out.println(
				"----------------------------------\n" +
				"| Order Reference No. #"+ order.getReferenceNo()+"         |\n" +
				"| Date: " + DateUtil.getDateTime(order.getDate())+"         |\n" +
				"| Table: " + order.getTable()+"                       |\n" +
				"| Served by: " + staffC.findStaff(order.getStaff()).getName()+"                |");
			if(order.getItemList().size()>0){
				System.out.println("|                                |\n"+"| MenuItems:                     |");
				for(MenuItem item : order.getItemList()){
					System.out.println("| ID:"+item.getMenuItemID()+" "+item.getName()+"\t"+df.format(item.getPrice()));
				}
			}
			if(order.getPromoList().size()>0){
				System.out.println("|                                |\n"+"| Promotion:                     |");
				for(Promotion promo : order.getPromoList()){
					System.out.println("| ID:"+promo.getPromoID()+" "+promo.getName()+"\t"+df.format(promo.getPromoPrice()));
				}
			}
			System.out.println("|                 Subtotal: "+ df.format(order.getSubtotal()));
			if(order.getMember()) System.out.println("|         Member Discount: -"+ df.format(order.getSubtotal()-order.getDiscountSubtotal()));	
			System.out.println("|                  GST(7%):  "+ df.format(order.getGST()));
			System.out.println("|                  TOTAL:  $"+ df.format(order.getTotal()));
			if(order.getFulfilled()) System.out.println("| Status: PAID                   |");
			else System.out.println("| Status: NOT PAID               |");
			System.out.println("----------------------------------");
			return true;
		}
		return false;
	}
	/**
	 * Print all Orders that are still active and unpaid
	 */
	public void viewActiveOrder(){
		boolean haveOrder = false;
		for(Order order : orderList){
			if(!order.getFulfilled()){
				viewOrder(order);
				haveOrder = true;
			}
		}
		if(!haveOrder) System.out.println("\n"+"- No Acive Orders to view -"+"\n");
		return;
	}
	/**
	 * Add/Remove MenuItem or Promotion from Order by requesting user input
	 */
	public void manageOrder(){
		viewActiveOrder();
		Scanner sc = new Scanner(System.in);
		try{
			System.out.print(" Enter Order Reference No.#: ");
			int refNo = sc.nextInt();
			for(Order order : orderList){
				if(order.getReferenceNo() ==  refNo){
					System.out.println("  (1)Add to Order\t (2)Remove from Order");
					System.out.print("Enter Choice: ");
					int choice = sc.nextInt();
					int type;
					switch(choice){
						case 1:
							menuC.printMenu();
							System.out.println("--(1)MenuItem\t (2)Promotion");
							System.out.print("Enter Choice: ");
							type = sc.nextInt();
							if(type==1){
								System.out.print("Input itemID: ");
								MenuItem item = menuC.findMenuItem(sc.nextInt());
								order.addToOrder(item);
							}
							else if(type==2){
								System.out.print("Input PromoID: ");
								Promotion promo = menuC.findPromotion(sc.nextInt());
								order.addToOrder(promo);
							}
							else{
								System.out.println(" - Invalid Choice - ");
								return;
							}
							break;
						case 2:
							if(order.getItemList().size()==0&&order.getPromoList().size()==0){
								System.out.println(" No Item in Order!");
								return;
							}
							order.viewOrderItems();
							System.out.println("--(1)MenuItem\t (2)Promotion");
							System.out.print("Enter Choice: ");
							type = sc.nextInt();
						if(type==1){
							System.out.print("Input itemID: ");
							MenuItem item = menuC.findMenuItem(sc.nextInt());
							order.removeFromOrder(item);
						}
						else if(type==2){
							System.out.print("Input PromoID: ");
							Promotion promo = menuC.findPromotion(sc.nextInt());
							order.removeFromOrder(promo);
						}
						else{
							System.out.println(" - Invalid Choice - ");
							return;
						}
							break;
					}
				}
			} 
		}catch(InputMismatchException e){
			sc.nextLine();
			System.out.println(" - Invalid Input -");
		}
		return;
	}
	/**
	 * Print Order Invoice and set order as fulfilled/paid
	 */
	public void printInvoice(){
		viewActiveOrder();
		Scanner sc = new Scanner(System.in);
		System.out.print(" Get INVOICE of Order Reference No.#: ");
		int refNo = sc.nextInt();
		for(Order order : orderList){
			if(order.getReferenceNo() ==  refNo){
				order.setFulfilled(true);
				TableController.getInstance().freeTable(order.getTable());
				System.out.println(
					"==================================\n"+
					"|             INVOICE            |");
				viewOrder(order);
				writeOrderData();
				return;
			}
		}
		System.out.println("- Order not found -");
	}

	/**
	 * Print Daily/Monthy sales revenue report by requesting user input
	 */
	public void generateSalesReport(){
		Scanner sc = new Scanner(System.in);

		double totalRevenue=0;
		double totalRevenueWTax=0;
		try{
			System.out.println("Generate By:\n  (1)Day\t (2)Month");
			System.out.print("Enter Choice: ");;
			int choice = sc.nextInt();sc.nextLine();
			String period = "00/00/0000";
			if(choice==1){
				System.out.print(" Input period (dd/MM/yyyy): ");
				period = sc.nextLine();
			}
			else if(choice==2){
				System.out.print(" Input period (MM/yyyy): ");
				period = sc.nextLine();
			}
			else{
				System.out.println(" - Invalid Choice -");
				return;
			}
			String orderDate = "00/00/0000";
			for(Order order : orderList){
				if(choice==1) orderDate = DateUtil.getDate(order.getDate());
				else if(choice==2) orderDate = DateUtil.getMonth(order.getDate());

				if(period.equals(orderDate) && order.getFulfilled()){
					viewOrder(order);
					if(order.getMember()) totalRevenue +=order.getDiscountSubtotal();
					else totalRevenue += order.getSubtotal();
					
					totalRevenueWTax += order.getTotal();
				}
			}
			System.out.println(
				"-------Sales Revenue Report-------\n"+
				"| Period: "+period+"\n"+
				"| \n"+
				"|           Revenue: "+df.format(totalRevenue)+"\n"+
				"| Revenue incl. TAX: "+df.format(totalRevenueWTax)+"\n"+
				"----------------------------------\n");
		}catch(InputMismatchException e){
			sc.nextLine();
			System.out.println(" - Invalid Input - ");
		}
		return;
	}

	/**
	 * load Order data from file to ArrayList
	 * @return true if successful, false otherwise
	 */
	private boolean readOrderData(){
		try{
            Scanner sc = new Scanner(new BufferedReader(new FileReader(DataFilePath.ORDER_PATH)));
			sc.nextLine();
            while(sc.hasNext()){
                String cur[] = sc.nextLine().split(";");
				int id = Integer.parseInt(cur[1]);
				if(id>lastReferenceNo) lastReferenceNo = id;
				Date orderDate = DateUtil.stringToDateTime(cur[0]);
                Order order = new Order(orderDate,id,Integer.parseInt(cur[2]),Integer.parseInt(cur[3]),Boolean.parseBoolean(cur[4]),Boolean.parseBoolean(cur[5]));
				int itemCount = Integer.parseInt(cur[6]);
				int promoCount = Integer.parseInt(cur[7]);
				for(int i=0;i<itemCount;i++)
					order.addToOrder(menuC.findMenuItem(Integer.parseInt(cur[8+i])));
				for(int i=0;i<promoCount;i++)
					order.addToOrder(menuC.findPromotion(Integer.parseInt(cur[8+itemCount+i])));

                orderList.add(order);
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;

	}
	/**
	 * write Order data to file from ArrayList
	 * @return true if successful, false otherwise
	 */
	private boolean writeOrderData(){
		try{
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(DataFilePath.ORDER_PATH, false)));
			out.println("dd/MM/yyyy HH:mm;refNo;tableNo;staffId;fufill;member;itemCount;promoCount;{itemIds};{promoIds};");
            for(Order order : orderList){
                out.print(DateUtil.getDateTime(order.getDate())+";"+order.getReferenceNo()+";"+order.getTable()+";"+order.getStaff()+";"+order.getFulfilled()+";"+order.getMember()+";"+order.getItemList().size()+";"+order.getPromoList().size()+";");
                for(MenuItem item : order.getItemList()){
                    out.print(item.getMenuItemID()+";");
                }
				for(Promotion promo : order.getPromoList()){
                    out.print(promo.getPromoID()+";");
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