package com.wuxiaolong.androidsamples.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by wxl on 2014/11/4.
 */
public class AppUtils {
    /**
     * Close outputStream
     *
     * @param is
     */
    public static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
                stream = null;
            } catch (IOException e) {
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        stream = null;
                    }
                    stream = null;
                }
            }
        }
    }
}
