package com.aleksandrov.jsf.example;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class ManagedBean {

    private String name;
    private String surname;

    public String addPerson() {
        System.out.printf("Person with name %s and surname %s was added!\n", name, surname);

        return "newPerson";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
