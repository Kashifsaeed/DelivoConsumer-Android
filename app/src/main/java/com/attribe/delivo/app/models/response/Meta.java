package com.attribe.delivo.app.models.response;

/**
 * Author: Uzair Qureshi
 * Date:  5/24/17.
 * Description:
 */

public class Meta {
    public boolean success;
    public Integer status;
    public String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }



}
