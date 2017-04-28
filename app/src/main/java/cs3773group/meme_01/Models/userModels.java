package cs3773group.meme_01.Models;

import java.io.Serializable;

/**
 * Created by Tom on 4/25/2017.
 */

public class userModels implements Serializable{

    private String username;
    private String password;
    private boolean isOnline;
    private boolean isLocked;

    public userModels (){
        this.username = "";
        this.password = "";
        this.isOnline = false;
        this.isLocked = false;
    }

    public userModels(String username, String password){
        this.username = username;
        this.password = password;
        this.isOnline = false;
        this.isLocked = false;
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
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }
}
