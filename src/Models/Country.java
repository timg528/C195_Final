package Models;

public class Country {
    private int countryID;
    private String countryName;


    /**
     * Creates a country object
     * @param countryID
     * @param countryName
     */
    public Country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    /**
     * Returns the ID of a country
     * @return countryID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Returns the Name of a Country object
     * @return countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Sets the ID of a country Object
     * @param countryID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Sets the name of a country object
     * @param countryName
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Overrides the toString method of a country object to display the name in combo boxes
     * @return
     */
    @Override
    public String toString() {
        return countryName;
    }
}
