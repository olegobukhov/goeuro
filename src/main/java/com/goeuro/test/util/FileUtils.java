package com.goeuro.test.util;

import java.io.File;
import java.io.IOException;

/**
 * Created by Olegdelone on 05.10.2016.
 */
public final class FileUtils {
    public static void mkdirs(File file) throws IOException {
        if(!file.mkdirs()){
            throw new IOException("Unable to create dirs: " + file);
        }
    }
}
