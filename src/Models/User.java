package Models;

public class User {

    private int id;
    private String username;

    public User(int id, String username){
        setId(id);
        setUsername(username);
    }


    public void setId(int id){
        this.id = id;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public int getId(){
        return id;
    }

    public String getUsername(){
        return username;
    }

    @Override
    public String toString() {return username;}
}
