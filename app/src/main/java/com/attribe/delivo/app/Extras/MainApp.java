package com.attribe.delivo.app.Extras;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Author: Uzair Qureshi
 * Date:  4/26/17.
 * Description:
 */

public class MainApp extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
