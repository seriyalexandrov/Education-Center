package com.alexandrov.example.log;

public class POJO {

    String name;
    String surname;
    int age;

    @Override
    public String toString() {
        return "POJO{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                '}';
    }
}
