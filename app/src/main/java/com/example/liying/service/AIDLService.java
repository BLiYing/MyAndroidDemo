package com.example.liying.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.liying.model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by      android studio
 *
 * @author :       ly
 * Date            :       2019-07-15
 * Time            :       11:48
 * Version         :       1.0
 * location        :       武汉研发中心
 * 功能描述         :
 **/
public class AIDLService extends Service {

    private final String TAG = "Server";

    private List<Book> bookList;

    public AIDLService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        bookList = new ArrayList<>();
        initData();
    }

    private void initData() {
        Book book1 = new Book(1,"活着");
        Book book2 = new Book(2,"或者");
        Book book3 = new Book(3,"叶应是叶");
        Book book4 = new Book(4,"https://github.com/leavesC");
        Book book5 = new Book(5,"http://www.jianshu.com/u/9df45b87cfdf");
        Book book6 = new Book(6,"http://blog.csdn.net/new_one_object");
        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);
        bookList.add(book4);
        bookList.add(book5);
        bookList.add(book6);
    }

    /*public final IBookManager.Stub stub = new IBookManager.Stub() {

        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;
        }

        @Override
        public void addBookInOut(Book book) throws RemoteException {
            if (book != null) {
                book.setBookName("服务器改了新书的名字 InOut");
                bookList.add(book);
            } else {
                Log.e(TAG, "接收到了一个空对象 InOut");
            }
        }

    };*/

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
