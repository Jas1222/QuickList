package com.example.jaspalhayer.quicklist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaspalhayer on 07/11/2015.
 */
public class Books {

    protected String bookTitle;
    protected String bookAuthors;
    protected String bookDateListed;
    protected String bookPrice;
    protected String bookUserListed;

//    protected String jsonBookTitle[];
//    protected String jsonBookAuthor[];
//    protected String jsonBookYear[];
//    protected int jsonBookPrice[];
//    protected String jsonBookIsbn[];
//    protected String jsonUniYear[];
//    protected String jsonUniCourse[];
//
    List<String> jsonBookTitle = new ArrayList<String>();
    List<String> jsonBookAuthor = new ArrayList<String>();
    List<String> jsonBookYear = new ArrayList<String>();
    List<String> jsonBookPrice = new ArrayList<String>();
    List<String> jsonBookIsbn = new ArrayList<String>();
    List<String> jsonUniYear = new ArrayList<String>();
    List<String> jsonUniCourse = new ArrayList<String>();

    public Books(){

    }

}
