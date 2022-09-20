import java.util.GregorianCalendar;
import baseForms.Entity;

/**
 * represents customers of the app, extends Entity
 */
public class Customer extends Entity {
	
	//Primary Key: Email
    private String name;
    private final GregorianCalendar birthDate;
    private char gender; // M for male, F for female, O for other
    private GregorianCalendar registrationDate;
    private GregorianCalendar endOfServiceDate;
    private char paymentFrequency; // M for monthly, Y for yearly
    private String phoneNumber;
    private String nickname;

    /**
     * constructor for customer
     * @param strArr pre-parsed string that contains all relevant data
     */
    public Customer(String[] strArr) {
    	/*
         * 0 - email 1 - name 2 - birth Date 3 - gender 4 - registration date 5 - end of
         * service date 6 - payment frequency 7 - phone number 8 - nickname
         */
        super(strArr[0]);
        name = strArr[1];
        birthDate = MyUtility.textToDate(strArr[2]);
        gender = strArr[3].charAt(0);
        registrationDate = MyUtility.textToDate(strArr[4]);
        endOfServiceDate = MyUtility.textToDate(strArr[5]);
        paymentFrequency = strArr[6].charAt(0);
        phoneNumber = strArr[7];
        nickname = strArr[8];
    }

    /**
     * @return phones number of customer
     */
    public String getPhone() {
        return phoneNumber;
    }
    
    /**
     * @return birth date of customer
     */
    public GregorianCalendar getBirthdate() {
    	return birthDate;
    }
    
    /**
     * turns the data of the customer to a string
     * @return string containing all relevant data of customer
     */
    @Override
    public String entityToString() {
    	return super.entityToString() + " " + name + " " + MyUtility.dateToText(birthDate) + 
    			" " + gender + " " + MyUtility.dateToText(registrationDate) +
    			" " + MyUtility.dateToText(endOfServiceDate) + " " + paymentFrequency + 
    			" " + phoneNumber + " " + nickname;
    }
    
    /**
     * makes sure a certain string is in the correct format of customer's PK
     * @param pk the string to validate
     * @return whether PK is valid or not
     */
    public static boolean validatePK(String pk) {
    	String pattern = "^\\w{2,}@\\w+(\\.\\w{2,3}){1,2}$";
    	return pk.matches(pattern);
    }
    
    /**
     * makes sure a certain string is in the correct format of customer's data
     * @param data string to validate
     * @return whether PK is valid or not
     */
    public static boolean validateData(String data) {
    	String pattern = "^\\w{2,}@\\w+(\\.\\w{2,3}){1,2} \\w{2,} \\d{1,2}/\\d{1,2}/\\d{4} [MFO] \\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}/\\d{1,2}/\\d{4} [YM] \\d{10} \\w{2,}$";
    	String strArr[] = data.split(" ");
    	GregorianCalendar start, end;
    	
    	try {
    		if (data.matches(pattern) && MyUtility.validateDate(strArr[2]) && MyUtility.validateDate(strArr[4]) && MyUtility.validateDate(strArr[5])) {
    	    	start = MyUtility.textToDate(data.split(" ")[4]);
    	    	end = MyUtility.textToDate(data.split(" ")[5]);
    			return start.compareTo(end) < 0;
    		}
    		return false;
    	}
    	catch (IndexOutOfBoundsException ioobe) {
    		return false;
    	}
    }
    
    /**
     * non static variant of validateData, calls its static variant.
     * @return whether this instance of Customer is in format or not.
     */
    @Override
    public boolean validateData() {
    	return validateData(entityToString());
    }

}