package reservation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import table.TableController;

import java.util.Date;
import java.util.InputMismatchException;

import util.DateUtil;
import util.DataFilePath;

/**
 * 	Reservation Controller manages Reservation
 * 
 * @author Nicholas
 * @version 1.0
 * @since 2021-11-13
 */

public class ReservationController {

	static ReservationController reservationControllerInstance;
	/**
	 * Store list of Reservation made
	 */
	private ArrayList<Reservation> reservationList;
	
	/**
	 * Constructs a ReservationController
	 */
	private ReservationController() {
		reservationList = new ArrayList<Reservation>();
		readReservationData();
		clearExpiredReservations();
		writeReservationData();
	}
	/**
	 * Get shared singleton instance
	 * @return	singleton ReservationController
	 */
	public static ReservationController getInstance() {
		if (reservationControllerInstance == null) {
			reservationControllerInstance = new ReservationController();
		}
		return reservationControllerInstance;
	}

	/**
	 * Get the list of Reservations made
	 * @return the ArrayList of Reservations made
	 */
	public ArrayList<Reservation> getReservationList(){
		return this.reservationList;
	}
	/**
	 * Get the Reservation by using contact number
	 * @param contact the guest's contact number
	 * @return Reservation found by contact, otherwise null
	 */
	public Reservation getByContact(String contact) {
		for (Reservation r : reservationList) {
			if (r.getContact().equals(contact)) {
				return r;
			}
		}
		System.out.println(" - No Reservation Found - ");
		return null;
	}
	/**
	 * Check if Reservation date is valid
	 * @param date the Reservation date to check
	 * @return true if successsful, false otherwise
	 */
	private static boolean checkValidReservationDate(Date date){
		Date now = new Date();
		if(date.before(now)){
    		System.out.println(" - Reservation can only be made in advance - ");
    		return false;
		}
		return checkValidWalkInDate(date);
	}
	/**
	 * Check if the Reservation Date is within allowed period
	 * @param date the Reservation date to check
	 * @return true if successsful, false otherwise
	 */
	private static boolean checkValidWalkInDate(Date date){
		boolean validDate = false;
		Date maxBookingDate = DateUtil.addDaysToDate(date, 60);
	    
    	if(date.after(maxBookingDate))
    		System.out.println(" - Earlist Allowed Reservation Only 2 Months In Advance - ");
    	else if (!duringOpen(date))
    		System.out.println(" - Reservation Must Be Within Operation Hours - ");
    	else
    		validDate = true;
	    
		return validDate;
	}
	/**
	 * Check if reservation date is within operating hours
	 * @param date the Reservation date to check
	 * @return true if during open, otherwise false
	 */
	private static boolean duringOpen(Date date){
        try{
			String day = DateUtil.getDate(date);
			String open = day + " 10:00";
			String close = day + " 22:00";
			if (date.before(DateUtil.stringToDateTime(close)) || date.after(DateUtil.stringToDateTime(open)) ){
				return true;
			}
		} catch(Exception e){
			e.printStackTrace();
			System.out.println(" - invalid date - ");
		}
        return false;
    }
	/**
	 * Remove completed Reservation from list
	 * @param reservation the fulfilled reservation
	 */
	public void completeReservation(Reservation reservation){
		reservationList.remove(reservation);
		writeReservationData();
	}
	/**
	 * Removes Reservations that passed expired date
	 */
	private void clearExpiredReservations(){
		ArrayList<Reservation> toRemove = new ArrayList<Reservation>();
		for(Reservation reservation : reservationList){
			if( reservation.getEndAt().before(new Date()) ){
				toRemove.add(reservation);
			}
		}
		for(Reservation reservation : toRemove){
			reservationList.remove(reservation);
		}
	}
	/**
	 * Create a new Reservation by requesting user for input
	 */
	public void createReservation() {
		clearExpiredReservations();
		Scanner sc = new Scanner(System.in);
		try{
			System.out.println("- - - - Create Reservation - - - -");

			System.out.print(" Enter Date (dd/MM/yyyy HH:mm): ");
			String dateS = sc.nextLine();
			Date date;
			try {
				date = DateUtil.stringToDateTime(dateS);
			} catch (Exception e) {
				System.out.println(" - Invalid Date Input - ");
				return;
			}
			System.out.print(" Enter Guest Contact No.: ");
			String contact = sc.nextLine();
			System.out.print(" Enter Guest Name: ");
			String guest = sc.nextLine();
			System.out.print(" Enter No. of Pax: ");
			int pax = sc.nextInt();

			int tableNo = TableController.getInstance().getVacantTable(date, pax);

			if(checkValidReservationDate(date) && tableNo !=-1) {
				Reservation reservation = new Reservation(date,contact,tableNo,pax,guest);  
				reservationList.add(reservation);
				System.out.println("Reservation made successfully.");
				writeReservationData();
			} 
			else{
				System.out.println(" - Reservation Unsuccessful - ");
			} 
		}catch(InputMismatchException e){
			sc.next();
			System.out.println(" - Invalid Choice - ");
		}

	}

	/**
	 *  Checks reservation details by requesting user input
	 */
	public void checkReservation()
	{
		Scanner sc = new Scanner(System.in);
		System.out.print(" Enter Customer Phone No.:");	
		String contact = sc.nextLine();
		Reservation reservation = getByContact(contact);
		if(reservation != null) {	
			System.out.println("---CheckReservation---");
			reservation.displayReservation();
		}
		return;
	}
	
	/**
	 *  Deletes a reservation by requesting user input
	 */
	public void deleteReservation()
	{
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter contact: ");
		String contact = sc.next();
		
		Reservation reservation = getByContact(contact);
		if (reservation == null) {
			System.out.println("Reservation not found.");
		} else {
			reservationList.remove(reservation);
			System.out.println("Reservation removed.");
			writeReservationData();
		}
		return;
	}
	/**
	 *  Menu page for managing reservations
	 */
	public void manageReservation(){
		clearExpiredReservations();
		writeReservationData();
		Scanner sc = new Scanner(System.in);
		System.out.println("- - - - Manage Reservation - - - -");
		System.out.println(" (1)Check (2)Remove (3)Display All ");
		System.out.print(" Enter Choice: ");
		int choice = sc.nextInt();
		switch(choice){
			case 1:
				this.checkReservation();
				break;
			case 2:
				this.deleteReservation();
				break;
			case 3:
				System.out.println("---Reservation List---");
				for(Reservation res : reservationList){
					res.displayReservation();
				}
				break;
		}
	}
	/**
	 *  load Reservation data from file to ArrayList
	 * @return	true if successful, false otherwise
	 */
	private boolean readReservationData(){
		try {
			Scanner sc = new Scanner(new BufferedReader(new FileReader(DataFilePath.RESERVATION_PATH)));
			sc.nextLine();
			while(sc.hasNext()) {
				String cur[] = sc.nextLine().split(";");
				Reservation reservation = new Reservation(DateUtil.stringToDateTime(cur[0]), cur[1],Integer.parseInt(cur[2]),Integer.parseInt(cur[3]),cur[4]);
				reservationList.add(reservation);
			}
			sc.close();
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 *  write Reservation data to file from ArrayList
	 * @return	true if successsful, false otherwise
	 */
	private boolean writeReservationData(){
		try{
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(DataFilePath.RESERVATION_PATH, false)));
			out.println("dd/MM/yyyy HH:mm;contact;tableNo;pax;guest;");
            for(Reservation res : reservationList){
				if(res.getEndAt().after(new Date()))
                	out.println(DateUtil.getDateTime(res.getDate())+";"+res.getContact()+";"+res.getTableNo()+";"+res.getPax()+";"+res.getGuest()+";");
            }
            out.close();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
	}
}

