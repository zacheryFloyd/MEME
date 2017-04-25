package cs3773group.meme_01.Models;

/**
 * Created by Tom on 4/25/2017.
 */

public class userModels {

    private int username;
    private String password;
    private boolean isOnline;

    public userModels (){
        this.username = 0;
        this.password = "";
        this.isOnline = false;
    }

    public userModels(int username, String password){
        this.username = username;
        this.password = password;
        this.isOnline = false;
    }

    public int getUsername() {
        return username;
    }

    public void setUsername(int username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }
}
