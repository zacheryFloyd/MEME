package cs3773group.meme_01.Models;
import android.util.Log;

import java.util.Random;
import java.lang.Object;
import java.security.SecureRandom;
 /**
 * Created by Tom on 4/25/2017.
 */

public class adminModelz {
    private int username;
    private String password;

    public adminModelz(){
        this.username = 0;
        this.password = "";
        generateNewUser();
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

    public void generateNewUser() {

        //password requirements
        String UpperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String LowerCase = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "!@#$";
        SecureRandom sr = new SecureRandom();

        //random 10 string username
        Random rand = new Random();
        char[] newUsername = new char[10];

        for(int i=0; i<10; i++){
            newUsername[i] = numbers.toCharArray()[sr.nextInt(numbers.toCharArray().length)];
        }

        //password size 16-20
        int passwordSize = 16 + rand.nextInt(5);
        char[] password = new char[passwordSize];

        for (int i=0; i < passwordSize; i++){
            password[i] = UpperCase.toCharArray()[sr.nextInt(UpperCase.toCharArray().length)];
            i++;
            if(i < passwordSize) {
                password[i] = LowerCase.toCharArray()[sr.nextInt(LowerCase.toCharArray().length)];
                i++;
            }
            if(i < passwordSize) {
                password[i] = numbers.toCharArray()[sr.nextInt(numbers.toCharArray().length)];
                i++;
            }
            if(i < passwordSize) {
                password[i] = symbols.toCharArray()[sr.nextInt(symbols.toCharArray().length)];
            }
        }
        String pw = new String(password);
        String us = new String(newUsername);
        Log.d("Password", pw);
        Log.d("Username", us);
    }
 }
