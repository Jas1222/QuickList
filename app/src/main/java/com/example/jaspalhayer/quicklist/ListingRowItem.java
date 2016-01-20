package com.example.jaspalhayer.quicklist;

/**
 * Created by jaspalhayer on 07/11/2015.
 */
public class ListingRowItem {
    protected String bookTitle;
    protected String bookAuthor;
    protected String bookPrice;
    protected String bookListId;
    protected String dateListed;

    public ListingRowItem(String bookTitle, String bookAuthor, String bookPrice, String dateListed, String bookListId){
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookPrice = bookPrice;
        this.dateListed = dateListed;
        this.bookListId = bookListId;
    }
}
