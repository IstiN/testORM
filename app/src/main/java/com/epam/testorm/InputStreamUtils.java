package com.epam.testorm;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Mikhail_Ivanou on 6/20/2014.
 */
public class InputStreamUtils {

    private static final int BUFFER_SIZE = 1024;

    public static String readString(InputStream is) throws IOException {

        if (is == null) {
            return null;
        }

        String text = null;
        final InputStreamReader in = new InputStreamReader(is);
        try {
            text = readString(in);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                Log.d("READ_STRING", "IOException : " + e.getMessage());
            }
        }
        return text;
    }

    public static String readString(InputStreamReader isr) throws IOException {
        String text;
        BufferedReader reader = new BufferedReader(isr,
                BUFFER_SIZE);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append('\n');
        }
        text = sb.toString();
        return text;
    }
}
