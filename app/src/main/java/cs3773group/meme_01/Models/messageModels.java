package cs3773group.meme_01.Models;

/**
 * Created by Tom on 4/26/2017.
 */

public class messageModels {
    private String text;
    private char[] encryptionMethods;
    private int lifeSpan; //seconds

    public messageModels(){
        this.text = "";
        this.encryptionMethods = new char[5];
        this.lifeSpan = 15;
    }

    public messageModels(String text, char[] encryptionMethods, int lifeSpan){
        this.text = text;
        this.encryptionMethods = encryptionMethods;
        this.lifeSpan = lifeSpan;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public char[] getEncryptionMethods() {
        return encryptionMethods;
    }

    public void setEncryptionMethods(char[] encryptionMethods) {
        this.encryptionMethods = encryptionMethods;
    }

    public int getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(int lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

}
