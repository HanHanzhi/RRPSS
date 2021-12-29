package staff;

/**
 *  Represent each Staff working in the restaurant.
 *  
 * @author Hanzhi
 * @version 1.0
 * @since 2021-11-13
 */

public class Staff {
	
	/**
	 *  The ID number of this Staff
	 */
	int employeeID;
	/**
	 * 	The Name of this Staff
	 */
	String staffName;
	/**
	 *  The Gender of this Staff
	 */
	String gender;
	/**
	 *  The Positon of this Staff
	 */
	String jobTitle;

	/**
	 * 	Creates a default new staff with no name and unassigned ID number.
	 */
	public Staff(){
		this.employeeID = -1;
		this.staffName = "no name";
	}
	/**
	 * Creates a new Staff with a given ID number and name.
	 * @param employeeid 	This Staff's ID number.
	 * @param staffname		This Staff's name.
	 * @param gender		This Staff's gender.
	 * @param jobtitle		This Staff's position.
	 */
	public Staff(int employeeid, String staffname, String gender, String jobtitle) {
		this.employeeID = employeeid;
		this.staffName = staffname;
		this.gender = gender;
		this.jobTitle = jobtitle;
	}
	/**
	 * Gets the ID number of this Staff.
	 * @return this Staff's ID number.
	 */
	public int getID() {
		return employeeID;
	}
	/**
	 * Change the ID number of this Staff.
	 * @param id the Staff's new ID numner.
	 */
	public void setID(int id){
		this.employeeID = id;
	}
	/**
	 * Get the name of this Staff.
	 * @return this Staff's name.
	 */
	public String getName() {
		return staffName;
	}
	/**
	 * Change the name of this staff.
	 * @param name the Staff's new name.
	 */
	public void setName(String name) {
		this.staffName = name;
	}
	/**
	 * Get the gender of this staff.
	 * @return this Staff's gender.
	 */
	public String getGender() {
		return this.gender;
	}
	/**
	 *	Get the positon of this staff.
	 * @return
	 */
	public String getJobTitle() {
		return jobTitle;
	}
	/**
	 * Change the position of this staff.
	 * @param title this Staff's new position.
	 */
	public void setJobTitle(String title){
		this.jobTitle = title;
	}
}
