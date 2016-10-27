package com.aleksandrov.jsf.example;

import com.aleksandrov.jsf.example.ejb.PersonDAO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class PersonManagedBean {

    @Inject
    private PersonDAO personDAO;

    private String name;
    private String surname;
    private String nameToDelete;

    public String addPerson() {
        personDAO.addPerson(name, surname);

        return null;
    }

    public String removePerson() {

        personDAO.removePerson(nameToDelete);
        return null;
    }

    public List<Map.Entry<String, String>> getAll() {
        return personDAO.getAll().entrySet().stream().collect(Collectors.toList());
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

    public String getNameToDelete() {
        return nameToDelete;
    }

    public void setNameToDelete(String nameToDelete) {
        this.nameToDelete = nameToDelete;
    }
}
