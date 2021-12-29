package table;

/**
 *  Represent each Table in the restaurant.
 *  
 * @author Hanzhi
 * @version 1.0
 * @since 2021-11-13
 */
public class Table {
	/**
	 * The ID of this Table
	 */
	private int tableNo;
	/**
	 * The Capacity of this Table
	 */
	private int tableCapacity;
	/**
	 * The availability of this Table
	 */
	private boolean isFree;
	
	/**
	 * Creates a default new table with no capacity and not available.
	 */
	public Table() {
		this.tableNo=-1;
		this.tableCapacity=0;
		this.isFree=false;
	};
	/**
	 * Creates a new Table with given Table number and capacity.
	 * @param tableNo			This Table's number.
	 * @param tableCapacity		This Table's capacity.
	 * @param isFree			This Table's availability.
	 */
	public Table( int tableNo, int tableCapacity, boolean isFree) {
		this.tableNo = tableNo;
		this.tableCapacity = tableCapacity;
		this.isFree = isFree;
	}
	
	/**
	 * Get the number of this Table
	 * @return this Table's number
	 */
	public int getTableNo() {
		return this.tableNo;
	}
	/**
	 * Change the number of this Table
	 * @param tableNo the Table's new number
	 */
	public void setTableNo(int tableNo) {
		this.tableNo = tableNo;
	}
	/**
	 * Get the capacity of this Table
	 * @return this Table's capacity
	 */
	public int getTableCapacity() {
		return this.tableCapacity;
	}
	/**
	 * Change the capacity of this Table
	 * @param tableCapacity the Table's new capacity
	 */
	public void setTableCapacity(int tableCapacity) {
		this.tableCapacity = tableCapacity;
	}

	/**
	 * Get the availability of this Table
	 * @return true if available, false otherwise
	 */
	public boolean getIsFree(){
		return this.isFree;
	}
	/**
	 * Set the availability of this Table
	 * @param status true if available, false otherwise
	 */
	public void setIsFree(boolean status){
		this.isFree = status;
	}
	
}
