package com.hit.ticketbookingclient.models;

import java.io.Serializable;

/** Someone who can buy tickets: basic contact details and an id. */
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String email;
    private String phone;

    /** Empty constructor so JSON from the server can fill this object. */
    public Customer() {
        // No-arg constructor for JSON deserialization (GSON)
    }

    /** Creates a customer with all contact fields filled in. */
    public Customer(int id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    /** Returns the customer's unique id. */
    public int getId() {
        return id;
    }

    /** Returns the customer's display name. */
    public String getName() {
        return name;
    }

    /** Returns the email address on file. */
    public String getEmail() {
        return email;
    }

    /** Returns the phone number on file. */
    public String getPhone() {
        return phone;
    }

    /** Changes the stored id. */
    public void setId(int id) {
        this.id = id;
    }

    /** Updates the name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Updates the email. */
    public void setEmail(String email) {
        this.email = email;
    }

    /** Updates the phone. */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /** Builds a short text summary useful for debugging. */
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

