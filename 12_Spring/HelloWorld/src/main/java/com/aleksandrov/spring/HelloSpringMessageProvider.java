package com.aleksandrov.spring;

public class HelloSpringMessageProvider implements MessageProvider {

    @Override
    public String getMessage() {
        return "Hello Spring Framework!";
    }
}
