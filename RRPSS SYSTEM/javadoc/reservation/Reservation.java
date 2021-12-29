package reservation;

import java.util.Date;
import util.DateUtil;
/**
 * Represent a Reservation booking made
 * 
 * @author Nicholas
 * @version 1.0
 * @since 2021-11-13
 */
public class Reservation {
	
	/**
	 * The Date of this Reservation
	 */
	Date date;
	/**
	 * The Date this Reservation expires
	 */
	Date endAt;
	/**
	 * The guest's contact number of this Reservation
	 */
	private String contact;
	/**
	 * The Table number assigned to this Reservation
	 */
	private int tableNo;
	/**
	 * The number of pax for this Reservation
	 */
	private int pax;
	/**
	 * The name of guest that made this Reservation
	 */
	private String guest;
	
	/**
	 * Creates a default new Reservation with current date.
	 */
	public Reservation() {
		this.date = new Date();
		this.contact = "no contact";
		this.tableNo = 0;
		this.pax = 0;
		this.guest = "no guest";
	}
	/**
	 * Creates a new Reservation with given date and pax.
	 * @param date		This Reservation's date.
	 * @param contact	This Reservation's guest contact number.
	 * @param tableNo	This Reservation's assigned Table number.
	 * @param pax		This Reservation's number of pax.
	 * @param guest		This Reservation's guest's name.
	 */
	public Reservation(Date date,String contact,int tableNo, int pax, String guest){
		this.date = date;
		this.endAt = DateUtil.addHoursToDate(date, 1);
		this.contact = contact;
		this.tableNo = tableNo;
		this.pax = pax;
		this.guest = guest;
	}

	/**
	 * Get the date of this Reservation
	 * @return this Reservation's date
	 */
	public Date getDate(){
		return date;
	}
	/**
	 * Change the date of this Reservation
	 * @param date the Reservation new date
	 */
	public void setDate(Date date){
		this.date = date;
		this.endAt = DateUtil.addHoursToDate(date, 1);
	}
	/**
	 * Get when this Reservation expires
	 * @return the Reservation expiry date
	 */
	public Date getEndAt(){
		return this.endAt;
	}
	/**
	 * Get the guest's contact number
	 * @return	this Reservation's guest's contact number
	 */
	public String getContact(){
		return contact;
	}
	/**
	 * Chnage the guest's contact number
	 * @param contact	the guest's new contact number
	 */
	public void setContact(String contact){
		this.contact = contact;
	}
	/**
	 * Get the assigned Table number of this Reservation
	 * @return this Reservation's assigned Table number
	 */
	public int getTableNo(){
		return tableNo;
	}
	/**
	 * Change the assigned Table number of this Reservation
	 * @param tableNo the new assigned Table number
	 */
	public void setTableNo(int tableNo){
		this.tableNo = tableNo;
	}
	/**
	 * Get the number of pax for this Reservation
	 * @return this Reservation's number of pax
	 */
	public int getPax(){
		return this.pax;
	}
	/**
	 * Change the number of pax of this Reservation
	 * @param pax new number of pax
	 */
	public void setPax(int pax){
		this.pax = pax;
	}
	/**
	 * Get the name of Guest for this Reservation
	 * @return	the name of guest of this Reservation
	 */
	public String getGuest(){
		return this.guest;
	}
	/**
	 * Change the name of Guest for this Reservation
	 * @param guest the new name of guest
	 */
	public void setGuest(String guest){
		this.guest = guest;
	}
	/**
	 * Print the details of this Reservation.
	 */
	public void displayReservation(){
		System.out.println("-----------------------");
		System.out.println("| Date: "+ DateUtil.getDateTime(date));
		System.out.println("| Table No.: " + this.tableNo);
		System.out.println("| Contact No.: " + this.contact);
		System.out.println("| Guest: " + this.guest);
		System.out.println("| No. of Pax: " + this.pax);
		System.out.println("-----------------------");
	}
}