// IBookManager.aidl
package com.wuxiaolong.aidlservice;
//通常引用方式传递自定义对象，必须要import语句声明
import com.wuxiaolong.aidlservice.Book;
// Declare any non-default types here with import statements

interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    List<Book> getBookList();

    //Error:aidl 'Book' can be an out type, so you must declare it as in, out or inout.
    void addBook(in Book book);

}
