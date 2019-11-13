package com.example.liying.model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by      android studio
 *
 * @author :       ly
 * Date            :       2019-07-13
 * Time            :       08:38
 * Version         :       1.0
 * location        :       武汉研发中心
 * 功能描述         :
 **/
public class Book implements Parcelable {
    public int bookid;
    public String bookName;
    public Book(int bookid,String bookName){
        this.bookid = bookid;
        this.bookName = bookName;
    }

    protected Book(Parcel in) {
        bookid = in.readInt();
        bookName = in.readString();
    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookid);
        dest.writeString(bookName);
    }
}
