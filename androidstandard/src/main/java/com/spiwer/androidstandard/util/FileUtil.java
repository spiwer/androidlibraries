package com.spiwer.androidstandard.util;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class FileUtil {

    public static void validateDatabase(Context context, String name, InputStream input, boolean force) throws IOException {
        String directory = context.getFilesDir().getParent() + "/databases/";
        File folder = new File(directory);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String databasePath = directory + name;
        File file = new File(databasePath);
        if (file.exists() && !force) {
            return;
        }
        copyFile(input, new FileOutputStream(databasePath));
    }

    public static void copyFile(InputStream input, OutputStream output)
            throws IOException {
        try {
            byte[] buf = new byte[input.available()];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        } finally {
            input.close();
            output.close();
        }
    }


}
