package com.attribe.delivo.app.Extras;

/**
 * Author: Uzair Qureshi
 * Date:  2/15/17.
 * Description:
 */

public interface CallBackResponse<T>
{
    void onSuccess(T data);
    void onFailure(String message);
}
