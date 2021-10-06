package Models;


public class Customer {

    private int id;
    private String name;
    private String address;
    private String postal;
    private int division;
    private String phone;

    public Customer(int id, String name, String address, String postal, int division, String phone){
        setId(id);
        setName(name);
        setAddress(address);
        setPostal(postal);
        setDivision(division);
        setPhone(phone);
    }

    //Setters

    public void setId(int id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public void setPostal(String postal){
        this.postal = postal;
    }
    public void setDivision(int division){
        this.division = division;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }

    //Getters

    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getAddress(){
        return address;
    }
    public String getPostal(){
        return postal;
    }
    public int getDivision(){
        return division;
    }
    public String getPhone(){
        return phone;
    }


}
