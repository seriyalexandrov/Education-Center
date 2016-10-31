package com.aleksandrov.jsf.example.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Ser on 27/10/2016.
 */

@Singleton
public class PersonDAO {

    private final Map<String, String> personStorage = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {

        personStorage.put("Sergei", "Aleksandrov");
    }

    public void addPerson(String name, String surname) {
        personStorage.put(name, surname);
    }

    public void removePerson(String name) {
        personStorage.remove(name);
    }

    public Map<String, String> getAll() {

        return Collections.unmodifiableMap(personStorage);
    }

}
