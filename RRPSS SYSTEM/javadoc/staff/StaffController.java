package staff;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import util.DataFilePath;

/**
 *  StaffController manages Staff
 */
public class StaffController {
    
    static StaffController staffControllerInstance;
    /**
     * Stores list of Staff in restaurant
     */
    private ArrayList<Staff> staffList;

    /**
     *  Constructs a StaffController 
     */
    public StaffController(){
        staffList = new ArrayList<Staff>();
        readStaffData();
    }
    /**
     * Get shared singleton instance
     * @return singleton staffcontroller
     */
    public static StaffController getInstance(){
		if(staffControllerInstance==null){
			staffControllerInstance = new StaffController();
		}
		return staffControllerInstance;
	}

    /**
     * Prints all staff working in restaurant
     * 
     */
    public void printStaff(){
        System.out.println("------------------STAFF------------------");
        for(Staff staff : staffList){
            System.out.print("ID:"+staff.getID()+" "+staff.getName()+"\t");
            if(staff.getID()%3==0) System.out.println();
        }
        System.out.println("-----------------------------------------");
    }
    /**
     *  Get Staff object using ID
     * 
     * @param id Staff ID number
     * @return reuqested Staff Object
     */
    public Staff findStaff(int id){
        for(Staff staff : staffList){
            if(staff.getID()==id)
                return staff;
        }
        return new Staff();
    }

    /**
     *  load Staff data from file to arraylist
     * @return true if successful, false otherwaise
     */
    private boolean readStaffData(){
        try{
            Scanner sc = new Scanner(new BufferedReader(new FileReader(DataFilePath.STAFF_PATH)));
            sc.nextLine();
            while(sc.hasNext()){
                String cur[] = sc.nextLine().split(";");
                Staff staff = new Staff(Integer.valueOf(cur[0]),cur[1],cur[2],cur[3]);
                staffList.add(staff);
            }
            return true;
        }catch(IOException e){
            e.printStackTrace();
        }
        return false;
    }


}
