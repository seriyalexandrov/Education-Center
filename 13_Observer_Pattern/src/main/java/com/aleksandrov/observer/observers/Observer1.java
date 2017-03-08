package com.aleksandrov.observer.observers;

import com.aleksandrov.observer.common.Event;
import com.aleksandrov.observer.common.Observer;

public class Observer1 implements Observer {

    @Override
    public void onEvent(Event event) {
        System.out.println("Observer 1 got event: " + event);
    }
}
