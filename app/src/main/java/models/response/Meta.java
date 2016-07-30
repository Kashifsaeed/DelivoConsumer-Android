package models.response;

import java.io.Serializable;

/**
 * Created by Sabih Ahmed on 14-Jun-16.
 */
public class Meta implements Serializable{

    private Boolean success;
    private Object apiDeprecated;
    private Object message;
    private Integer code;

    public Meta(Boolean success, Object apiDeprecated, Object message, Integer code) {
        this.success = success;
        this.apiDeprecated = apiDeprecated;
        this.message = message;
        this.code = code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Object getApiDeprecated() {
        return apiDeprecated;
    }

    public void setApiDeprecated(Object apiDeprecated) {
        this.apiDeprecated = apiDeprecated;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
