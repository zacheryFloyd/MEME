package cs3773group.meme_01.Models;

import java.io.Serializable;

/**
 * Created by Tom on 4/26/2017.
 */

public class messageModels implements Serializable {
    private String text;
    private String encryptionKey;
    private int lifeSpan; //seconds
    private String senderID;
    private String receiverID;
    private int msgID;

    private static final Boolean ON = true;
    private static final Boolean OFF = false;

    public messageModels(){
        this.text = "";
        this.encryptionKey = "";
        this.lifeSpan = 15;
        senderID = "";
        receiverID = "";
        msgID = 0;

    }

    public messageModels(String text, String encryptionKey, int lifeSpan, String senderID, String receiverID, int msgID){
        this.text = text;
        this.encryptionKey = encryptionKey;
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

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public void setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
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

    public void setMsgID(int id){
        this.msgID = id;
    }
    public int getMsgID(){
        return this.msgID;
    }

}
