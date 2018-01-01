package me.Andrew.PrisonRadio.socketio;

/**
 * Created by Andrew on 01/01/2018.
 */
public class PlayingObject {

    private String message;

    public PlayingObject() {
    }

    public PlayingObject(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

}