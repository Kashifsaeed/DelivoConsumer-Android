package com.attribe.delivo.app.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Author: Uzair Qureshi
 * Date:  6/21/17.
 * Description:
 */

public class SnackBars {
    /**
     * @usage It use to show any message provided by the caller
     * @param view
     * @param message
     */
    public static void showMessage(View view, String message)
    {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

}
