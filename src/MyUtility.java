import java.util.Date;
import java.util.GregorianCalendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.*;
import java.awt.*;

/**
 * a simple utility class, contains useful functions that are used throughout
 * the entire package
 */
public class MyUtility {
	
    /**
     * turns strings to dates
     * @param date string representing a date
     * @return a date of gregorianCalendar type
     */
    public static GregorianCalendar textToDate(String date) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date dt = df.parse(date);
            GregorianCalendar myDate = new GregorianCalendar();
            myDate.setTime(dt);
            return myDate;

        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }
    
    /**
     * turns dates to strings
     * @param gc date to be converted
     * @return string that represents the date
     */
    public static String dateToText(GregorianCalendar gc) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        df.setCalendar(gc);
        String dateFormatted = df.format(gc.getTime());
        return dateFormatted;
    }
    
    /**
     * validates whether a string representing a date is in the correct format
     * @param data string to validate
     * @return whether string is valid or not
     */
    public static boolean validateDate(String data) {
    	String pattern = "\\d{1,2}/\\d{1,2}/\\d{4}";
    	
    	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    	format.setLenient(false);
    	
    	try {
    		if (data != null && data.matches(pattern)) {
	    		format.parse(data);
	    		return true;
    		}
    		else
    			return false;
    	}
    	catch (ParseException pe) {
    		return false;
    	}
    	catch (IndexOutOfBoundsException ioobe) {
    		return false;
    	}
    }
    
    /**
     * Adds a component to the center of the panel. Used as an interface that allows
     * inheritors to communicate with the centerPanel of this class.
     * 
     * @param guiComponent component to be added to centerPanel
     */
    public static void addToCenter(JPanel centerPanel, Component guiComponent) {
        centerPanel.add(guiComponent);
    }
    
    /**
     * Creates a row of elements that'll hold the information of a given field.
     * 
     * @param name      name of the information written in the row
     * @param textField contains the correlating information
     * @return a row of elements
     */
    public static JPanel createRow(String name, JTextField textField) {
        // panel that contains the row
        JPanel row = new JPanel();

        // elements of the row
        row.add(new JLabel(name));
        row.add(textField);

        return row;
    }

}
