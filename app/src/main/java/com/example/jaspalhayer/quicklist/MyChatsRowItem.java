package com.example.jaspalhayer.quicklist;

/**
 * Created by jaspalhayer on 29/02/2016.
 */
public class MyChatsRowItem {
    protected String userId;
    protected String userFullName;
    protected String bookTitle;
    protected String date;

    public MyChatsRowItem(String userId, String bookTitle, String userFullName){
        this.userId = userId;
        this.bookTitle = bookTitle;
        this.userFullName = userFullName;
    }
}
