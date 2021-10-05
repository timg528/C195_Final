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

    public Appointment(int id, String title, String description, String location, String type,
                       Timestamp start, Timestamp end, int customer_id, int user_id) {
        setId(id);
        setTitle(title);
        setDescription(description);
        setLocation(location);
        setType(type);
        setStart(start);
        setEnd(end);
        setCustomer(customer_id);
        setUser(user_id);
    }

    public void setId(int id){
        this.id = id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setDescription(String description){
        this.description = description;
    }

}
