package Models;

import java.sql.Timestamp;

public class Appointment {

    // Making this like the Product class from C482

    // These are the variables that are displayed in the table view
    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private int customer_id;
    private int user_id;
    private int contact_id;

    /**
     * This handles taking the various parameters and creating an Appointment object.
     * @param id
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customer_id
     * @param user_id
     * @param contact_id
     */
    public Appointment(int id, String title, String description, String location, String type,
                       Timestamp start, Timestamp end, int customer_id, int user_id, int contact_id) {
        setId(id);
        setTitle(title);
        setDescription(description);
        setLocation(location);
        setType(type);
        setStart(start);
        setEnd(end);
        setCustomer(customer_id);
        setUser(user_id);
        setContact(contact_id);
    }

    /**
     * Sets the appointment ID
     * @param id
     */
    public void setId(int id){this.id = id;}

    /**
     * Sets the appointment title
     * @param title
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * Sets the appointment description
     * @param description
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * Sets the appointment location
     * @param location
     */
    public void setLocation(String location) {this.location = location;}

    /**
     * Sets the appointment Type
     * @param type
     */
    public void setType(String type) {this.type = type;}

    /**
     * Sets the appointment start timestamp
     * @param start
     */
    public void setStart(Timestamp start) {this.start = start;}

    /**
     * Sets the appointment end timestamp
     * @param end
     */
    public void setEnd(Timestamp end) {this.end = end;}

    /**
     * Sets the appointment customer ID
     * @param customer_id
     */
    public void setCustomer(int customer_id) {this.customer_id = customer_id;}

    /**
     * Sets the appointment user id
     * @param user_id
     */
    public void setUser(int user_id) {this.user_id = user_id;}

    /**
     * Sets the appointment contact id
     * @param contact_id
     */
    public void setContact(int contact_id) {this.contact_id = contact_id;}

    /**
     * Returns appointment ID
     * @return id
     */
    public int getId() { return id;}

    /**
     * Returns appointment title
     * @return title
     */
    public String getTitle() {return title;}

    /**
     * Returns appointment description
     * @return description
     */
    public String getDescription() {return description;}

    /**
     * Returns the appointment location
     * @return location
     */
    public String getLocation() {return location;}

    /**
     * Returns the appointment type
     * @return type
     */
    public String getType() {return type;}

    /**
     * Returns the start timestamp
     * @return start
     */
    public Timestamp getStart() {return start;}

    /**
     * Returns the end timestamp
     * @return end
     */
    public Timestamp getEnd() {return end;}

    /**
     * Returns the customer id of the appointment
     * @return customer_id
     */
    public int getCustomer() {return customer_id;}

    /**
     * Returns the user_id of the appointment
     * @return user_id
     */
    public int getUser() {return user_id;}

    /**
     * Returns the contact_id of the appointment
     * @return
     */
    public int getContact() {return contact_id;}

}
