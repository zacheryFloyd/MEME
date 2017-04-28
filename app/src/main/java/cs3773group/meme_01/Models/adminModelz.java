package cs3773group.meme_01.Models;

/**
 * Created by Tom on 4/25/2017.
 */

public class adminModelz {
    private String username;
    private String password;

    public adminModelz(){
        this.username = "";
        this.password = "";
        //AdminAreaActivity john = new AdminAreaActivity();
        //john.addUserToDatabase();
    }

    public adminModelz(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



 }
