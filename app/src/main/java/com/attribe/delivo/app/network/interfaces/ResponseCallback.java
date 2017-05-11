package com.attribe.delivo.app.network.interfaces;

/**
 * Author: Uzair Qureshi
 * Date:  4/21/17.
 * Description:
 */

public interface ResponseCallback<T>
{
    void onSuccess(T response);
    void OnResponseFailure(String error_body);
    void onfailure(String message);
}

