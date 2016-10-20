package models.response;

/**
 * Created by Maaz on 17-Jun-16.
 */
public class ResponseConfirmOrder {

    private Meta meta;
    private Object data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public class Meta {
        private boolean success;
        private Object apiDeprecated;
        private String code;
        private String message;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public Object getApiDeprecated() {
            return apiDeprecated;
        }

        public void setApiDeprecated(Object apiDeprecated) {
            this.apiDeprecated = apiDeprecated;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }
}
