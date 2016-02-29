package com.example.jaspalhayer.quicklist;

/**
 * Created by jaspalhayer on 29/02/2016.
 */
public class ChatRowItem {
    protected String userId;
    protected String message;
    protected long timestamp;

    public ChatRowItem(String userId, String message, long timestamp){
        this.userId = userId;
        this.message = message;
        this.timestamp = timestamp;
    }
}
