package com.epam.testorm;

import android.text.TextUtils;
import android.widget.EditText;

/**
 * Created by Mike on 09.09.13.
 */
public class StringUtils {

    public static final String EMPTY = "";

    public static boolean isEmpty(String string) {
        if (string == null) return true;
        if (string.length() > 0 && string.charAt(0) == 160) return true;
        return TextUtils.isEmpty(string.trim());
    }


    public static String getTrimmedText(EditText editText) {
        if (editText == null || TextUtils.isEmpty(editText.getText().toString().trim())) {
            return null;
        }
        return editText.getText().toString().trim();
    }



}
