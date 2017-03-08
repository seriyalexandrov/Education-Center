package com.aleksandrov.observer;

import com.aleksandrov.observer.common.Observer;
import com.aleksandrov.observer.observable.Observable;
import com.aleksandrov.observer.observers.Observer1;
import com.aleksandrov.observer.observers.Observer2;


/**
 * Created by ser on 08/03/2017.
 */
public class Main {

    public static void main(String[] args) {
        Observable observable = new Observable();

        Observer observer1 = new Observer1();
        Observer observer2 = new Observer2();

        observable.subscribe(observer1);
        observable.subscribe(observer2);

        observable.generateEvent("event 1");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        observable.generateEvent("event 2");

        observable.unsubscribe(observer1);

        observable.generateEvent("event 3");

        observable.unsubscribe(observer2);

        observable.generateEvent("event 4");

    }
}
