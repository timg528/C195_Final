package Models;


public class Customer {

    private int id;
    private String name;
    private String address;
    private String postal;
    private int division;
    private String phone;

    /**
     * Creates a customer object
     * @param id
     * @param name
     * @param address
     * @param postal
     * @param division
     * @param phone
     */
    public Customer(int id, String name, String address, String postal, int division, String phone){
        setId(id);
        setName(name);
        setAddress(address);
        setPostal(postal);
        setDivision(division);
        setPhone(phone);
    }

    //Setters

    /**
     * Sets the ID of a customer
     * @param id
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Sets the name of a customer
     * @param name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Sets the address of a customer
     * @param address
     */
    public void setAddress(String address){
        this.address = address;
    }

    /**
     * Sets the postal code of a customer
     * @param postal
     */
    public void setPostal(String postal){
        this.postal = postal;
    }

    /**
     * Sets the first level division of a customer
     * @param division
     */
    public void setDivision(int division){
        this.division = division;
    }

    /**
     * Sets the phone number of a customer
     * @param phone
     */
    public void setPhone(String phone){
        this.phone = phone;
    }

    //Getters

    /**
     * Returns the ID of a customer
     * @return id
     */
    public int getId(){
        return id;
    }

    /**
     * Returns the name of a customer
     * @return name
     */
    public String getName(){
        return name;
    }

    /**
     * Returns the address of a customer
     * @return address
     */
    public String getAddress(){
        return address;
    }

    /**
     * Returns the postal code of a customer
     * @return postal
     */
    public String getPostal(){
        return postal;
    }

    /**
     * Returns the first level division of the customer
     * @return division
     */
    public int getDivision(){
        return division;
    }

    /**
     * Returns the phone number of a customer
     * @return phone
     */
    public String getPhone(){
        return phone;
    }

    /**
     * Overrides the toString() method to allow customer names to show in combo boxes
     * @return name
     */
    @Override
    public String toString() {return name;}


}
