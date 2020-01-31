package com.mktowett.majiapp.utils;

import android.text.TextUtils;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class StringUtilities {
    public static boolean checkFilledEditText(EditText editText, String errorMessage) {
        if (TextUtils.isEmpty(editText.getText().toString())) {
            editText.setError(errorMessage);
            return false;
        } else {
            return true;
        }
    }

    public static String getEncodedString(String string) {
        try {
            return URLEncoder.encode(string, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getDecodedString(String string) {
        try {
            return URLDecoder.decode(string, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param input
     * @return
     */
    public static String creditCardNumber(String input) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (i % 4 == 0 && i != 0) {
                result.append(" ");
            }
            if (i > 5 && i < (input.length() - 4)) {
                result.append("*");
            } else {
                result.append(input.charAt(i));
            }
        }
        return result.toString();
    }

    public static String getProperString(String crapString) {
        crapString = crapString.replace("\\", "");
        crapString = crapString.replace("\"", "");
        crapString = crapString.replace("[", "");
        crapString = crapString.replace("]", "");

        return crapString;
    }

    public static String stripSlashes(String crapString) {
        crapString = crapString.replaceAll("\\\\", "");
        crapString = crapString.replace("\"[", "[");
        crapString = crapString.replace("]\"", "]");
        crapString = crapString.replace("\"{", "{");
        crapString = crapString.replace("}\"", "}");
        return crapString;
    }

    public static void disableEditTextAndPreventCopyPaste(EditText edittext, boolean isEnabled) {
        edittext.setEnabled(isEnabled);
        edittext.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public void onDestroyActionMode(ActionMode mode) {
            }

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });
    }
}
