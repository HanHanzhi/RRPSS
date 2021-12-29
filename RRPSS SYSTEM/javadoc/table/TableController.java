package table;

import util.DataFilePath;
import util.DateUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import reservation.Reservation;
import reservation.ReservationController;

/**
 *   TableController manages Table
 * 
 * @author Nicholas
 * @version 1.0
 * @since 2021-11-13
 */

public class TableController {

	static TableController tableControllerInstance;
	/**
	 *  Store list of Tables in restaurant
	 */
	private ArrayList<Table> tableList;
	
	/**
	 *  Constructs a TableController
	 */
	public TableController() {
		tableList = new ArrayList<Table>();
		readTableData();
	}
	/**
     * Get shared singleton instance
     * @return singleton staffcontroller
     */
	public static TableController getInstance(){  //singleton
		if(tableControllerInstance==null){
			tableControllerInstance = new TableController();
		}
		return tableControllerInstance;
	}
	
	/**
     *  Get Table object using ID
     * 
     * @param id Table ID number
     * @return reuqested Table Object
     */
	private Table getTable(int tableNo) {
		for (Table table : tableList) {
			if (table.getTableNo() == tableNo) {
				return table;
			}
		}
		return null;
	}
	/**
	 * Get List of Tables in restaurant
	 * @return the ArrayList of Tables in restaurant
	 */
	public ArrayList<Table> getTableList(){
		return this.tableList;
	}
	/**
	 * Get an avaliable vacant table that does not clash with any reservation
	 * @param date	when the table is needed
	 * @param pax	the number of pax for the table
	 * @return index of vacant table, if no avalible table returns -1.
	 */
	public int getVacantTable(Date date, int pax){
		int tableID = -1;
		for(Table table : tableList){
			if(tableID != -1) 			// if already found, no need to look further.
				return tableID;
			
			if(table.getTableCapacity()>=pax){	// find table that fits
				tableID = table.getTableNo();

				ArrayList<Reservation> reservationList = ReservationController.getInstance().getReservationList();
				for(Reservation res : reservationList){
					if( res.getTableNo()==table.getTableNo() ){   //check if has other reservation
						
						if( date.before(res.getDate()) || res.getEndAt().before(date) ) 
							continue;
						else 
							tableID = -1;
					}
				}
				if(date.before(DateUtil.addHoursToDate(new Date(), 1)) && table.getIsFree()){			// check if requesting for table NOW
					return tableID;
				}
				else if (date.before(DateUtil.addHoursToDate(new Date(), 1)) && !table.getIsFree() ) {
					tableID = -1;
				}
			}

		}
		return tableID;
	}

	/**
	 * Set Table as occupied
	 * @param tableNo Table ID to occupy
	 * @return	true if successful, false otherwise
	 */
	public boolean occupyTable(int tableNo){
		Table table = getTable(tableNo);
		if(table!=null && table.getIsFree()){
			table.setIsFree(false);
			writeTableData();
			return true;
		}
		else{
			System.out.println(" - Table Not Avaliable -");
		}
		return false;
	}
	/**
	 * Set Table as vacant
	 * @param tableNo Table ID to free
	 * @return true if successful, false otherwise
	 */
	public boolean freeTable(int tableNo){
		Table table = getTable(tableNo);
		if(table!=null){
			table.setIsFree(true);
			writeTableData();
			return true;
		}
		else{
			System.out.println(" - Table Not Found -");
		}
		return false;
	}
	/**
	 * Print the list of table, it's capacity and current vacancy
	 */
	public void checkTables(){
		System.out.println(
			"- - - - - Check Table - - - - -\n"+
			"NO.\tCAPACITY\tVACANCY");
		for(Table table : tableList){
			String occupancy = "vacant";
			if(!table.getIsFree()) occupancy = "occupied";
			System.out.println(" "+table.getTableNo()+"\t   "+table.getTableCapacity()+"\t\t"+occupancy);
		}
		System.out.println("- - - - - - - - - - - - - - - -");
	}
    /**
     *  load Table data from file to ArrayList
     * @return true if successful, false otherwise
     */
	private boolean readTableData(){
		try {
			Scanner sc = new Scanner(new BufferedReader(new FileReader(DataFilePath.TABLE_PATH)));
			sc.nextLine();
			while(sc.hasNext()) {
				String cur[] = sc.nextLine().split(";");
				Table table = new Table(Integer.parseInt(cur[0]),Integer.parseInt(cur[1]),Boolean.parseBoolean(cur[2]));
				tableList.add(table);
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
		 *  write Table data to file from ArrayList
		 * @return true if successful, false otherwise
		 */
	private boolean writeTableData(){
		try{
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(DataFilePath.TABLE_PATH, false)));
			out.println("tableNo;tableCapacity;isFree");
            for(Table table : tableList){
                out.println(table.getTableNo()+";"+table.getTableCapacity()+";"+table.getIsFree()+";");
            }
            out.close();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
	}
	
}
