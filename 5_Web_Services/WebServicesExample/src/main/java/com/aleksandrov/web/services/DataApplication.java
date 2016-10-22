package com.aleksandrov.web.services;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("services")
public class DataApplication extends Application {

    private final Set<Class<?>> classes;

    public DataApplication() {

        HashSet<Class<?>> c = new HashSet<>();
        c.add(WebService.class);
        classes = Collections.unmodifiableSet(c);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }
}