package Models;

public class User {

    private int id;
    private String username;

    /**
     * Creates a User object
     * @param id
     * @param username
     */
    public User(int id, String username){
        setId(id);
        setUsername(username);
    }


    /**
     * Sets the ID of the user
     * @param id
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Sets the username of the user
     * @param username
     */
    public void setUsername(String username){
        this.username = username;
    }

    /**
     * Gets the ID of the user
     * @return id
     */
    public int getId(){
        return id;
    }

    /**
     * Returns the username of the user
     * @return username
     */
    public String getUsername(){
        return username;
    }

    /**
     * Overrides the toString() method to show usernames in combo boxes
     * @return username
     */
    @Override
    public String toString() {return username;}
}
