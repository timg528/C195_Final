package Models;

public class Contact {
    private int contactID;
    private String contactName;
    private String contactEmail;

    /**
     * Creates a contact object
     * @param contactID
     * @param contactName
     * @param contactEmail
     */
    public Contact(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * Returns the contactID of the contact
     * @return contactID
     */
    public int getContactID() { return contactID;}

    /**
     * Returns contactName of the contact
     * @return contactName
     */
    public String getContactName() {return contactName;}

    /**
     * Returns the email of the contact
     * @return contactEmail
     */
    public String getContactEmail() {return contactEmail;}

    /**
     * Sets the ID of the contact
     * @param contactID
     */
    public void setContactID(int contactID) {this.contactID = contactID;}

    /**
     * Sets the name of the contact
     * @param contactName
     */
    public void setContactName(String contactName) {this.contactName = contactName;}

    /**
     * Sets the email of the contact
     * @param contactEmail
     */
    public void setContactEmail(String contactEmail) {this.contactEmail = contactEmail;}

    /**
     * Overrides the toString method of the contact object to allow comboboxes to display the name of the
     * contact object instead of it's memory location
     * @return contactName
     */
    @Override
    public String toString() {
        return contactName;
    }
}
