package cn.wuxiaolong.aidlservice;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wuxiaolong on 2017/6/30.
 * 个人博客：http：//wuxiaolong.me
 */

public class Book implements Parcelable {
    public int bookId;
    public String bookName;

    public Book(int bookId, String bookName) {
        this.bookId = bookId;
        this.bookName = bookName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 将对象写入到Parcel（序列化）
     *
     * @param parcel：就是对象即将写入的目的对象
     * @param i:                   有关对象序列号的方式的标识
     *                             这里要注意，写入的顺序要和在createFromParcel方法中读出的顺序完全相同。例如这里先写入的为name，
     *                             那么在createFromParcel就要先读name
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(bookId);
        parcel.writeString(bookName);
    }

    /**
     * 在想要进行序列号传递的实体类内部一定要声明该常量。常量名只能是CREATOR,类型也必须是
     * Parcelable.Creator<T>  T:就是当前对象类型
     */
    public static final Creator<Book> CREATOR = new Creator<Book>() {
        /***
         * 根据序列化的Parcel对象，反序列化为原本的实体对象
         * 读出顺序要和writeToParcel的写入顺序相同
         */
        @Override
        public Book createFromParcel(Parcel parcel) {
            return new Book(parcel);
        }

        /**
         * 创建一个要序列化的实体类的数组，数组中存储的都设置为null
         */
        @Override
        public Book[] newArray(int i) {
            return new Book[i];
        }
    };

    private Book(Parcel parcel) {
        bookId = parcel.readInt();
        bookName = parcel.readString();
    }
}
