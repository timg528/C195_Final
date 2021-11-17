package Models;

public class Division {
    private int divisionID;
    private String divisionName;
    private int countryID;

    /**
     * Creates a first level division object
     * @param divisionID
     * @param divisionName
     * @param countryID
     */
    public Division(int divisionID, String divisionName, int countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
    }

    /**
     * Sets the countryID of a division
     * @param countryID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Sets the division ID
     * @param divisionID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * Sets the name of the division
     * @param divisionName
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * Returns the divisionID
     * @return divisionID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Returns the countryID of a division
     * @return countryID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Returns the name of the division
     * @return divisionName
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * Overrides the toString() method to show division names in combo boxes
     * @return divisionName
     */
    public String toString() {
        return divisionName;
    }
}
