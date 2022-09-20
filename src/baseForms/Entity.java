package baseForms;

/**
 * base abstract class of entity
 */
public abstract class Entity {
	protected final String pk;

    /**
     * constructor of entity
     * @param pkString
     */
    public Entity(String pkString) {
        pk = pkString;
    }

    /**
     * get PK of entity
     * @return PK of entity
     */
    public String getPK() {
        return pk;
    }
    
    /**
     * basic toString for entity
     */
    @Override
    public String toString() {
    	return pk;
    }
    
    /**
     * turns entity into a string
     * @return
     */
    public String entityToString() {
    	return pk;
    }
    
    /**
     * abstract method to validate data
     */
    public abstract boolean validateData();
}