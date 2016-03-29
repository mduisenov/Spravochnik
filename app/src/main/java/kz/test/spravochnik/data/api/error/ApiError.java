package kz.test.spravochnik.data.api.error;

import java.util.List;

public class ApiError {
    private int code;
    private List<Message> errors;
    private String message;
    private String name;
    private int status;

    public String getName() {
        return this.name;
    }

    public String getMessage() {
        return this.message;
    }

    public int getCode() {
        return this.code;
    }

    public int getStatus() {
        return this.status;
    }

    public List<Message> getErrors() {
        return this.errors;
    }
}
