package com.aleksandrov.web;

import com.aleksandrov.examples.jpa.StatelessBean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigInteger;
import java.sql.SQLDataException;

@Named
@RequestScoped
public class ManagedBean {
;
    private String id;
    private String name;

    @Inject
    private StatelessBean bean;

    public String getValue() {
        try {
            name = bean.getById(new BigInteger(id)).getName();
        } catch (SQLDataException e) {
            return "error";
        }
        return "show";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
