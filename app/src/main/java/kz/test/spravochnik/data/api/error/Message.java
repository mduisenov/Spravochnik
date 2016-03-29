package kz.test.spravochnik.data.api.error;

import java.util.List;

public class Message {
    private String field;
    private List<String> message;

    public String getField() {
        return this.field;
    }

    public List<String> getMessage() {
        return this.message;
    }
}
