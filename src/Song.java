import java.util.GregorianCalendar;

import baseForms.Entity;

/**
 * represents songs inside the app, extends Entity
 */
public class Song extends Entity {

	//Primary Key: a 7-digits serial number, starting at 3000000
	private String name;
    private final GregorianCalendar releaseDate;
    private int duration;
    private String musicClip; // path to clip
    private String audioFile; // path to file

    /**
     * constructor for Song
     */
    public Song(String[] strArr) {
        super(strArr[0]);
        name = strArr[1];
        releaseDate = MyUtility.textToDate(strArr[2]);
        duration = Integer.parseInt(strArr[3]);
        musicClip = strArr[4];
        audioFile = strArr[5];
    }

    /**
     * get song's duration in seconds
     */
    public int getDuration() {
        return duration;
    }

    /**
     * get song's name
     */
    public String getName() {
    	return name;
    }
    
    /**
     * turns the data of the Song into a single string
     * @return string containing data of string
     */
    @Override
    public String entityToString() {
    	return super.entityToString() + " " + name +
    			" " + MyUtility.dateToText(releaseDate) +
    			" " + duration + " " + musicClip + " " + audioFile;
    }
    
    /**
     * checks of a PK is in the format of song
     * @param pk PK to validate
     * @return whether PK is valid or not
     */
    public static boolean validatePK(String pk) {
    	String pattern = "^3\\d{6}$";
    	return pk.matches(pattern);
    }
    
    /**
     * checks if a string is in format of song's data
     * @param data the string to validate
     * @return whether string is valid of not
     */
    public static boolean validateData(String data) {
        /* please notice that for the purpose of our app integrity all song links should
         * link to Rick Astley's Never Gonna Give You Up - official youtube video and
         * all play files should link to "play.mp4" file.
         */
    	String pattern = "^3\\d{6} \\w+ \\d{1,2}/\\d{1,2}/\\d{4} \\d+ \\Qhttp://y2u.be/dQw4w9WgXcQ play.mp4\\E$";
    	try {
    		return data.matches(pattern) && MyUtility.validateDate((data.split(" "))[2]);
    	}
    	catch (IndexOutOfBoundsException ioobe) {
    		return false;
    	}
    }
    
    //validateData but without youtube link and file
    /**
     * same as validateDate but without the youtube and file links
     * @param data string to validate
     * @return whether string is validate or not
     */
    public static boolean validatePartialData(String data) {
    	String pattern = "^\\w+ \\d{1,2}/\\d{1,2}/\\d{4} \\d+$";
    	try {
    		return data.matches(pattern) && MyUtility.validateDate((data.split(" "))[1]);
    	}
    	catch (IndexOutOfBoundsException ioobe) {
    		return false;
    	}
    }
    
    /**
     * non static variant of validateData calls its static variant
     */
    @Override
    public boolean validateData() {
    	return validateData(entityToString());
    }
    
}
