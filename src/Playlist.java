import java.util.GregorianCalendar;
import baseForms.Entity;

/**
 * represents Playlists, extends Entity
 */
public class Playlist extends Entity {

	//Primary Key: a 7-digits serial number, starting at 1000000
    private String name;
    private final GregorianCalendar creationDate;
    private int numOfSongs;
    private int totalDuration;

    /**
     * constructor for new Playlists that is created through the UI
     * @param name      name of Playlist
     * @param serialNum serial number of Playlist
     */
    public Playlist(String name, int serialNum) {
    	super(String.valueOf(serialNum));
        this.name = name;
        creationDate = new GregorianCalendar();
        numOfSongs = totalDuration = 0;
    }

    /**
     * regular constructor for Playlists that are read from file
     * @param strArr parsed string array that contains data of Playlist
     */
    public Playlist(String[] strArr) {
        super(strArr[0]);
        name = strArr[1];
        creationDate = MyUtility.textToDate(strArr[2]);
        numOfSongs = Integer.parseInt(strArr[3]);
        totalDuration = Integer.parseInt(strArr[4]);
    }
    
    /**
     * updates Playlist info with a song
     * @param s song that updates the Playlist
     */
    public void addSongInfo(Song s) {
    	numOfSongs++;
    	totalDuration += s.getDuration();
    }

    /**
     * get name of Playlist
     * @return name of Playlist
     */
    public String getName() {
        return name;
    }
    
    /**
     * @return number of songs in a Playlist
     */
    public int getNumOfSongs() {
    	return numOfSongs;
    }
    
    /**
     * @return the total duration of the Playlist
     */
    public int getTotalDuration() {
    	return totalDuration;
    }
    
    /**
     * turns Playlist data to a string
     * @return string contains data of Playlist
     */
    @Override
    public String entityToString() {
    	return super.entityToString() + " " + name +
    			" " + MyUtility.dateToText(creationDate) +
    			" " + numOfSongs + " " + totalDuration;
    }
    
    /**
     * makes sure PK of Playlist is validate
     * @param pk the PK to be validated
     * @return whether PK is valid or not
     */
    public static boolean validatePK(String pk) {
    	String pattern = "^1\\d{6}$";
    	return pk.matches(pattern);
    }
    
    /**
     * makes sure the entirety of Playlist data is in format
     * @param data the string to be validated
     * @return whether string is valid or not
     */
    public static boolean validateData(String data) {
    	String pattern = "^1\\d{6} \\S+ \\d{1,2}/\\d{1,2}/\\d{4} \\d+ \\d+$";
    	try {
    		return data.matches(pattern) && MyUtility.validateDate((data.split(" "))[2]);
    	}
    	catch (IndexOutOfBoundsException ioobe) {
    		return false;
    	}
    }
    
    /**
     * non static variant of validateData, calls it static variant
     * @return whether this instace of Playlist is valid.
     */
    @Override
    public boolean validateData() {
    	return validateData(entityToString());
    }

}
