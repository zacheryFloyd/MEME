package cs3773group.meme_01.Models;

/**
 * Created by Tom on 4/26/2017.
 */

public class messageModels {
    private String text;
    private String encryptionMethods;
    private int lifeSpan; //seconds
    private String senderID;
    private String receiverID;
    private int msgID;

    private static final Boolean ON = true;
    private static final Boolean OFF = false;

    public messageModels(){
        this.text = "";
        this.encryptionMethods = "";
        this.lifeSpan = 15;
        senderID = "";
        receiverID = "";
        msgID = 0;

    }

    public messageModels(String text, String encryptionMethods, int lifeSpan, String senderID, String receiverID, int msgID){
        this.text = text;
        this.encryptionMethods = encryptionMethods;
        this.lifeSpan = lifeSpan;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.msgID = msgID;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getEncryptionMethods() {
        return encryptionMethods;
    }

    public void setEncryptionMethods(String encryptionMethods) {
        this.encryptionMethods = encryptionMethods;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public int getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(int lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

}
