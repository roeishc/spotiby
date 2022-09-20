import baseForms.Relation;

/**
 * This class is for the relation between a Song and the Artist that peraformed.
 * PK1 is for the Song.
 * PK2 is for the Artist.
 */
public class SongPerformedByArtist extends Relation {

    // Artist's role in a song can be one of the folowing:
    // L = leading singer, S - singer, P - player, D - director.
    private char role;

    public SongPerformedByArtist(String[] strArr) {
    	super(strArr[0], strArr[1]);
    	role = strArr[2].charAt(0);
    }
    
    /**
     * Get the Artist's role in the song.
     */
    public char getRole() {
        return role;
    }
    
    /**
     * Get the Song's PK as PK1 in relation.
     */
    public String getSongPK() {
    	return pk1;
    }
    
    /**
     * Get the Artist's PK as PK2 in relation.
     */
    public String getArtistPK() {
    	return pk2;
    }
    
    /**
     * Override the method relationToString form Relation class.
     * Creating a string out of the relation and the Artist's role.
     */
    @Override
    public String relationToString() {
    	return super.relationToString() + " " + role;
    }
    
    /**
     * Define and check the role.
     * Check that the first key is really a Song PK
     * and the second key is an Artist PK.
     */
    public static boolean validateData(String str) {
//    	String pattern = "^\\d{7} \\d{7} [LSPD]$";
    	String[] strArr = str.split(" ");
    	String role_pattern = "[LSPD]";
    	return strArr[2].matches(role_pattern) &&
    			Song.validatePK(strArr[0]) &&
    			Artist.validatePK(strArr[1]);
    }
    
    /**
     * Override the method validateData form Relation class.
     */
    @Override
    public boolean validateData() {
    	return validateData(relationToString());
    }

}