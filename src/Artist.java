import baseForms.Entity;

/**
 * Represents artists, inherits from Entity.
 */
public class Artist extends Entity {
	
	//Primary Key: a 7-digits serial number, starting at 2000000
    private String name;
    private String wikiLink;
    private String description;

    /**
     * Constructor for artist.
     * @param strArr contains a parsed string with all relevant data.
     */
    public Artist(String[] strArr) {
        super(strArr[0]);
        name = strArr[1];
        wikiLink = strArr[2];
        description = strArr[3];
    }

    /**
     * @return the name of the artist
     */
    public String getName() {
    	return name;
    }
    
    /**
     * @return the wikilink of the artist
     */
    public String getWikiLink() {
        return wikiLink;
    }

    /**
     * @return the description of the artist
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Takes all data of an artist and turns it into a single string
     * @return string containing relevant data of artist
     */
    @Override
    public String entityToString() {
    	return super.entityToString() + " " + name + " " + wikiLink +
    			" " + description;
    }
    
    /**
     * validates the primary key of an artist
     * @param pk primary key to validate
     * @return whether PK is valid or not
     */
    public static boolean validatePK(String pk) {
    	String pattern = "^2\\d{6}$";
    	return pk.matches(pattern);
    }
    
    /**
     * Validate the entirety of artist's data format, can be used from anywhere in
     * the package
     * @param data all data of an artist
     * @return whether data is in format or not
     */
    public static boolean validateData(String data) {
    	String pattern = "^2\\d{6} \\w+ \\Qhttps://en.wikipedia.org/wiki/\\E\\S+ \\w+$";
    	return data.matches(pattern);
    }
    
    /**
     * non static variant of validateData, calls its static variant
     * @return whether data is in format or not
     */
    @Override
    public boolean validateData() {
    	return validateData(entityToString());
    }

}
