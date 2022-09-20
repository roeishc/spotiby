package baseForms;

/**
 * base abstract relation class
 */
public abstract class Relation {
	// RelationName:			pk1 - pk2
    // CreatedBy:				Customer - Playlist
    // SongInPlaylist:			Song - Playlist
    // SongPerformedByArist:	Song - Artist

    protected final String pk1;
    protected final String pk2;

    /**
     * constructor for relatoin
     */
    public Relation(String pk1, String pk2) {
        this.pk1 = pk1;
        this.pk2 = pk2;
    }
    
    /**
     * get first PK of the relation
     * @return
     */
    public String getPK1() {
    	return pk1;
    }
    
    /**
     * get second PK of the relation
     * @return
     */
    public String getPK2() {
    	return pk2;
    }
    
    /**
     * base toString for Relation
     */
    @Override
    public String toString() {
    	return pk1 + " " + pk2;
    }
    
    /**
     * method to change a relation to a single string
     */
    public String relationToString() {
    	return toString();
    }
    
    /**
     * abstract method to validate data of a relation
     */
    public abstract boolean validateData();
    
}
