package org.ride_on.domain;

import java.util.ArrayList;
import java.util.List;

public class Result<T> {

    private final ArrayList<String> messages = new ArrayList<>();
    private ActionStatus type = ActionStatus.SUCCESS;
    private T payload;

    public ActionStatus getType() {
        return type;
    }

    public boolean isSuccess() {
        return type == ActionStatus.SUCCESS;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }

    public void addMessage( ActionStatus type, String message ) {
        messages.add(message);
        this.type = type;
    }

}
