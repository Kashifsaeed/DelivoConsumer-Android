package models.response;

/**
 * Author: Uzair Qureshi
 * Date:  2/15/17.
 * Description:
 */

public class MetaInfo {
    private String message;
    private String status;
    private String success;


    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
