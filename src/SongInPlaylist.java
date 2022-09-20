import java.util.GregorianCalendar;
import baseForms.Relation;

/**
 * This class is for the relation between a Song and the PlayList it is in.
 * PK1 is for the Song.
 * PK2 is for the PlayList.
 */
public class SongInPlaylist extends Relation {
	
    private GregorianCalendar dateOfAddition;

    public SongInPlaylist(String[] strArr) {
    	super(strArr[0], strArr[1]);
    	dateOfAddition = MyUtility.textToDate(strArr[2]);
    }
    
    /**
     * Get the Song's PK as PK1 in relation.
     */
    public String getSongPK() {
    	return pk1;
    }
    
    /**
     * Get the PlayList's PK as PK2 in relation.
     */
    public String getPlaylistPK() {
    	return pk2;
    }
    
    /**
     * Get the date the Song was added to the PlayList in.
     */
    public GregorianCalendar getDateOfAddition() {
    	return dateOfAddition;
    }
    
    /**
     * Override the method relationToString form Relation class.
     * Creating a string out of the relation and the date of addition.
     */
    @Override
    public String relationToString() {
    	return super.relationToString() + " " + MyUtility.dateToText(dateOfAddition);
    }
    
    /**
     * Define and check the date.
     * Check that the first key is really a Song PK
     * and the second key is a PlayList PK.
     */
    public static boolean validateData(String data) {
    	String date_pattern = "^\\d{1,2}/\\d{1,2}/\\d{4}$";
    	String[] strArr = data.split(" ");
    	
    	try {
    		return strArr[2].matches(date_pattern) &&
    				MyUtility.validateDate((data.split(" "))[2]) &&
    				Song.validatePK(strArr[0]) && Playlist.validatePK(strArr[1]);
    	}
    	catch (IndexOutOfBoundsException ioobe) {
    		return false;
    	}
    }
    
    /**
     * Override the method validateData form Relation class.
     */
    @Override
    public boolean validateData() {
    	return validateData(relationToString());
    }
    
}