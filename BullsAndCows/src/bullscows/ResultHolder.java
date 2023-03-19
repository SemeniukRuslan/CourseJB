package bullscows;

public class ResultHolder {
    private Boolean success;
    private String message;

    public ResultHolder(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }
    public String getMessage() {
        return message;
    }
}