package de.upb.sede.edd.reports;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public abstract class Report {

    protected List<String> messages;

    public Report() {
        messages = new ArrayList<>();
    }

    public Report(List<String> message) {
        this.messages = messages;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    @JsonIgnore
    public void addMessage(String msg) {
        messages.add(msg);
    }
}
