import baseForms.Relation;

/**
 * This class is for the relation between Custumer and the PlayList he creates.
 * PK1 is for the Customer.
 * PK2 is for the PlayList.
 */
public class CreatedBy extends Relation {
    
    public CreatedBy(String[] strArr) {
    	super(strArr[0], strArr[1]);
    }
    
    /**
     * Get the Customer's PK as PK1 in relation.
     */
    public String getCustomerPK() {
    	return pk1;
    }
    
    /**
     * Get the PlayList's PK as PK2 in relation.
     */
    public String getPlaylistPK() {
    	return pk2;
    }
    
    /**
     * Check that the first key is really a Customer
     * PK and the second key is a PlayList PK.
     */
    public static boolean validateData(String str) {
    	String[] strArr = str.split(" ");
    	return Customer.validatePK(strArr[0]) && Playlist.validatePK(strArr[1]);
    }
    
    /**
     * Override the method validateData form Relation class.
     */
    @Override
    public boolean validateData() {
    	return validateData(relationToString());
    }
    
}