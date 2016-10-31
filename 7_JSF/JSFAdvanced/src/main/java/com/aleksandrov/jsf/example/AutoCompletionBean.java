package com.aleksandrov.jsf.example;

import com.aleksandrov.jsf.example.ejb.PersonDAO;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ser on 27/10/2016.
 */

@ManagedBean
public class AutoCompletionBean {

    @Inject
    private PersonDAO personDAO;

    private String name;

    public List<String> completeName(String query) {

        return personDAO.getAll().keySet().stream().collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
