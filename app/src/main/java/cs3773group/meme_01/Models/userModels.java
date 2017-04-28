package cs3773group.meme_01.Models;

import java.io.Serializable;

/**
 * Created by Tom on 4/25/2017.
 */

public class userModels implements Serializable{

    private String username;
    private String password;
    private int isOnline;
    private int isLocked;

    public userModels (){
        this.username = "";
        this.password = "";
        this.isOnline = 0;
        this.isLocked = 0;
    }

    public userModels(String username, String password){
        this.username = username;
        this.password = password;
        this.isOnline = 0;
        this.isLocked = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isOnline() {
        if(this.isOnline == 0){
            return true;
        }
        else{
            return false;
        }
    }

    public void setOnline(int online) {
        isOnline = online;
    }

    public boolean isLocked() {
        if(this.isLocked == 0){
            return false;
        }
        else{
            return true;
        }
    }

    public void setLocked(int locked) {
        isLocked = locked;
    }
}
