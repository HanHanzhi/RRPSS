package util;

import java.util.Date;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * The Date formats used by the restaurant
 */
public class DateUtil {

    static SimpleDateFormat monthFormatter = new SimpleDateFormat("MM/yyyy");
    static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    static SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    static SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
	static Calendar calendar= Calendar.getInstance();

    /**
     * Convert Date to string with the month and year
     * @param date the Date
     * @return month and year in MM/yyyy
     */
    public static String getMonth(Date date){
        return monthFormatter.format(date);
    }
    /**
     * Convert Date to string of date
     * @param date the Date
     * @return date in dd/MM/yyyy
     */
    public static String getDate(Date date){
		return dateFormatter.format(date);
	}
    /**
     * Convert Date to string of date and time
     * @param date the Date
     * @return date and time in dd/MM/yyyy HH:mm
     */
    public static String getDateTime(Date date){
		return dateTimeFormatter.format(date);
	}
    /**
     * Convert string time to Date
     * @param sTime time in HH:mm
     * @return the Date
     * @throws Exception
     */
    public static Date stringToTime(String sTime) throws Exception{  
        return timeFormatter.parse(sTime);
    }
    /**
     * Convert string date and time to Date
     * @param sDate date and time in dd/MM/yyyy HH:mm
     * @return the Date
     * @throws Exception
     */
    public static Date stringToDateTime(String sDate) throws Exception{  
        return dateTimeFormatter.parse(sDate);
    }
    /**
     *  add Hours to Date
     * @param date the Date
     * @param hours the number of Hours to add
     * @return the Date with added Hours
     */
    public static Date addHoursToDate(Date date, int hours) {
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }
    /**
     * add Days to Date
     * @param date the Date
     * @param days the number of Days to add
     * @return the Date with added Days
     */
    public static Date addDaysToDate(Date date, int days) {
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }
    

    
}
