package com.aleksandrov.observer.observable;

import com.aleksandrov.observer.common.Event;
import com.aleksandrov.observer.common.Observer;

import java.util.ArrayList;

/**
 * Created by ser on 08/03/2017.
 */
public class Observable {

    private ArrayList<Observer> listeners = new ArrayList<>();

    public void subscribe(Observer observer) {
        listeners.add(observer);
    }

    public void unsubscribe(Observer observer) {
        listeners.remove(observer);
    }

    public void generateEvent(String eventMessage) {
        Event event = new Event(eventMessage);

        for(Observer o : listeners) {
            o.onEvent(event);
        }
    }
}
