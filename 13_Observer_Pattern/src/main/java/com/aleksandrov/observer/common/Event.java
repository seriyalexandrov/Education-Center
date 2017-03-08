package com.aleksandrov.observer.common;

public class Event {

    private String message;

    public Event(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Event{");
        sb.append("message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}