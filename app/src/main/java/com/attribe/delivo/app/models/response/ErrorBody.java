package com.attribe.delivo.app.models.response;

/**
 * Author: Uzair Qureshi
 * Date:  5/25/17.
 * Description:
 */

public class ErrorBody {
    private int error_code;
    private String error_msg;

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }


    public ErrorBody(int error_code, String error_msg) {

        this.error_code = error_code;
        this.error_msg = error_msg;
    }
}
