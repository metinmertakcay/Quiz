package com.example.user.hw1;

import java.io.Serializable;

/**
 * Created by User on 28.03.2018.
 */

@SuppressWarnings("serial")
public class Person implements Serializable {

    private String name, surname, phone, email;

    public Person(String name, String surname, String phone, String email)
    {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email =email;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
