package cs3773group.meme_01.Models;

/**
 * Created by Tom on 4/25/2017.
 */

public class adminModelz {
    private int username;
    private String password;

    public adminModelz(){
        this.username = 0;
        this.password = "";
        //AdminAreaActivity john = new AdminAreaActivity();
        //john.addUserToDatabase();
    }

    public adminModelz(int username, String password){
        this.username = username;
        this.password = password;
    }

    public int getUsername(){
        return this.username;
    }

    public void setUsername(int username){
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



 }
