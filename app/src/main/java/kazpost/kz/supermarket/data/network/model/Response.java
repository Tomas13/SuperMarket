package kazpost.kz.supermarket.data.network.model;

/**
 * Created by jean on 5/17/17.
 */

public class Response {
    String status, message;

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
