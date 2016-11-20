package com.aleksandrov.examples.service;

import javax.ws.rs.ApplicationPath;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("services")
public class Application extends javax.ws.rs.core.Application {

    private final Set<Class<?>> classes;

    public Application() {

        HashSet<Class<?>> c = new HashSet<>();
        c.add(RestServices.class);
        classes = Collections.unmodifiableSet(c);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }
}
