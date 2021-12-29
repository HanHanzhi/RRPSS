package order;

import java.util.Date;
import java.util.ArrayList;

import menu.MenuItem;
import menu.Promotion;

/**
 * Represents an Order placed in the restaurant
 * 
 * @author Hanzhi
 * @version 1.0
 * @since 2021-11-13
 */
public class Order {
	/**
	 * The list of MenuItems ordered in this Order
	 */
	private ArrayList<MenuItem> itemList = new ArrayList<MenuItem>();
	/**
	 * The list of Promotion ordered in this Order
	 */
	private ArrayList<Promotion> promoList = new ArrayList<Promotion>();
	/**
	 * The Table number assigned to this Order
	 */
	private int tableNo;
	/**
	 * The reference number for this Order
	 */
	private int referenceNo;
	/**
	 * The date this Order was made
	 */
	private Date dateReceived; //(dd/MM/yyyy HH:mm)
	/**
	 * The status of whether this Order is completed or not
	 */
	private boolean fulfilled;
	/**
	 * The ID number of the Staff that took this Order
	 */
	private int staff; //staffID
	/**
	 * The Subtotal of this Order
	 */
	private double subtotal = 0; //without member discount
	/**
	 * The Subtotal of this Order with membership discount
	 */
	private double discountSubtotal=0;
	/**
	 * The total bill of this Order including tax
	 */
	private double total = 0; //with tax
	/**
	 * The status of whether the guest of this Order is a member or not
	 */
	private boolean member;
	/**
	 * The member discount value
	 */
	private static final double DISCOUNTED = 0.8;
	/**
	 * The GST value
	 */
	private static final double TAX = 1.07;

	/**
	 * Creates a default new Order with current date with reference number -1.
	 */
	public Order() {
		setSubtotal(0);
		this.dateReceived = new Date();
		this.fulfilled = true;
		this.staff = -1;
		this.tableNo = -1;
		this.member = false;
		this.referenceNo = -1;
	}
	/**
	 * Create a new Order with given date, reference number and table number.
	 * @param dateRec		This Order's received date
	 * @param referenceNo	This Order's reference number
	 * @param tableno		This Order's assigned Table number
	 * @param staff			This Order's staff ID number
	 * @param fulfill		This Order's fulfilled status
	 * @param memberStatus	This Order's guest membership status
	 */
	public Order(Date dateRec, int referenceNo, int tableno, int staff, boolean fulfill, boolean memberStatus){
		setSubtotal(0);
		this.dateReceived = dateRec;
		this.fulfilled = fulfill;
		this.staff = staff;
		this.tableNo = tableno;
		this.member = memberStatus;
		this.referenceNo = referenceNo;
	}
	
	/**
	 * Get the Reference number of this Order
	 * @return this Order's Reference number
	 */
	public int getReferenceNo() {
		return referenceNo;
	}
	/**
	 * Change the reference number of this Order
	 * @param ref The Order's new Reference number
	 */
	public void setReferenceNo(int ref) {
		this.referenceNo = ref ;
	}
	/**
	 * Get the Received date of this Order
	 * @return this Order's Received date
	 */
	public Date getDate() {
		return dateReceived;
	}
	/**
	 * Change the Received date of this Order
	 * @param date The Order's new Received date
	 */
	public void setDate(Date date) {
		this.dateReceived = date;
	}
	/**
	 * Get the Staff ID that took this Order
	 * @return the staff ID that took this Order
	 */
	public int getStaff() {
		return staff;
	}
	/**
	 * Change the Staff ID that took this Order
	 * @param staff The Order's new Staff ID
	 */
	public void setStaff(int staff) {
		this.staff = staff;
	}
	/**
	 * Change the Table ID assigned to this Order
	 * @return this Order's assigned Table ID
	 */
	public int getTable() {
		return tableNo;
	}
	/**
	 * Change the Table ID assigned to this Order
	 * @param table The Order's new Table ID
	 */
	public void setTable(int table) {
		this.tableNo = table;
	}
	/**
	 * Get the membership status of this Order
	 * @return this Order's membership status
	 */
	public boolean getMember(){
		return member;
	}
	/**
	 * Change the membership status of this Order
	 * @param member The Order's new membership status
	 */
	public void setMember(boolean member){
		this.member = member;
	}
	/**
	 * Get the fulfilled status of this Order
	 * @return	this Order's fulfilled status
	 */
	public boolean getFulfilled() {
		return this.fulfilled;
	}
	/**
	 * Change the fulfilled status of this Order
	 * @param fulfill The Order's new fulfilled status
	 */
	public void setFulfilled(boolean fulfill) {
		this.fulfilled = fulfill;
	}
	/**
	 * Get the subtotal of this Order
	 * @return this Order's subtotal
	 */
	public double getSubtotal() {
		return subtotal;
	}
	/**
	 * Change the subtotal of this Order
	 * @param subtotal The Order's new subtotal
	 */
	private void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	/**
	 * Add to the subtotal of this Order
	 * @param value The value to add to subtotal
	 */
	private void addSubtotal(double value){
		this.subtotal += value;
	}
	/**
	 * Get the GST charges of this Order
	 * @return	this Order's GST charges
	 */
	public double getGST(){
		if(member) 
			return discountSubtotal*(TAX-1);

		return subtotal*(TAX-1);
	}
	/**
	 * Get the total bill of this Order
	 * @return this Order's total bill
	 */
	public double getTotal(){
		if(member){
			discountSubtotal = DISCOUNTED * subtotal;
			total = discountSubtotal * TAX;	
		}
		else total = this.subtotal * TAX;
		return total;
	}
	/**
	 * Get the subtotal of this Order with membership discount
	 * @return	this Order's subtotal with membership discount
	 */
	public double getDiscountSubtotal(){
		discountSubtotal = DISCOUNTED * subtotal;
		return discountSubtotal;
	}
	/**
	 * Add either MenuItem or Promotion to this Order
	 * @param o The Order's MenuItem or Promotion to be added
	 */
	public void addToOrder(Object o) {
		if (o instanceof MenuItem){
			MenuItem item = (MenuItem) o;
			itemList.add(item);
			addSubtotal(item.getPrice());
		}
		else if (o instanceof Promotion) {
			Promotion promo = (Promotion) o;
			promoList.add(promo);
			addSubtotal(promo.getPromoPrice());
		}
		else{
			System.out.println(" - Unable to add this to Order -");
		}
	}
	/**
	 * Remove either MenuItem or Promotion from this Order
	 * @param o	The Order's MenuItem or Promotion to be removed
	 */
	public void removeFromOrder(Object o)
	{
		if (o instanceof MenuItem){
			MenuItem item = (MenuItem) o;

			if(itemList.remove(item))
				addSubtotal((-1)*item.getPrice());
		}
		else if (o instanceof Promotion) {
			Promotion promo = (Promotion) o;
			if(promoList.remove(promo))
				addSubtotal((-1)*promo.getPromoPrice());
		}
		else{
			System.out.println(" - Unable to Remove this from Order -");
		}
	}
	/**
	 * Get the list of MenuItems on this Order
	 * @return	The Order's ArrayList of MenuItem
	 */
	public ArrayList<MenuItem> getItemList() {
		return itemList;
	}
	/**
	 * Change the list of MenuItems on this Order
	 * @param itemList The Order's new ArrayList of MenuItem
	 */
	public void setMenuItemList(ArrayList<MenuItem> itemList) {
		this.itemList = itemList;
		for(MenuItem item : itemList){
			addSubtotal(item.getPrice());
		}
	}
	/**
	 * Get the list of Promotion on this Order
	 * @return	The Order's ArrayList of Promotion
	 */
	public ArrayList<Promotion> getPromoList() {
		return promoList;
	}
	/**
	 * Change the list of Promotion on this Order
	 * @param promoList The Order's new ArrayList of Promotion
	 */
	public void setPromoList(ArrayList<Promotion> promoList) {
		this.promoList = promoList;
		for(Promotion promo : promoList){
			addSubtotal(promo.getPromoPrice());
		}
	}
	/**
	 * Print all the MenuItems and Promotion on this Order
	 */
	public void viewOrderItems()
	{	
		if(itemList.size()>0){
			System.out.println("Items in Order No."+referenceNo+": ");
			for (MenuItem item : itemList) {
				System.out.println("ID:"+ item.getMenuItemID() +" "+ item.getName()+"\t $"+item.getPrice());	 
			}
		}
		if(promoList.size()>0){
			System.out.println("Promotion in Order No."+referenceNo+": ");
			for (Promotion promo : promoList) {
				System.out.println("ID:"+ promo.getName()+"\t $"+promo.getPromoPrice());
			}
		}
	}
}
