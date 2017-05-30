package com.attribe.delivo.app.interfaces;

import com.attribe.delivo.app.models.response.ErrorBody;

/**
 * Author: Uzair Qureshi
 * Date:  4/21/17.
 * Description:
 */

public interface ResponseCallback<T>
{
    void onSuccess(T response);
    void OnResponseFailure(ErrorBody error_body);
    void onfailure(String message);
}

