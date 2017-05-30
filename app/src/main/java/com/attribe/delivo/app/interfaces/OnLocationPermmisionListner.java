package com.attribe.delivo.app.interfaces;

/**
 * Author: Uzair Qureshi
 * Date:  5/29/17.
 * Description:
 */

public interface OnLocationPermmisionListner {
    void onPermissionAlreadyGranted();
    void onPermisionRequested();
    void onPermissionBeforeDenied();
}
