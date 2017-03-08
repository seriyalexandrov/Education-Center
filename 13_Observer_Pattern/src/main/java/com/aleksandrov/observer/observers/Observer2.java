package com.aleksandrov.observer.observers;

import com.aleksandrov.observer.common.Event;
import com.aleksandrov.observer.common.Observer;

public class Observer2 implements Observer{

    @Override
    public void onEvent(Event event) {
        System.out.println("Observer 2 got event: " + event);
    }
}
