package com.example.server.model.user;

import com.fasterxml.jackson.annotation.JsonSubTypes;

import java.util.Set;
import java.util.UUID;

public interface User {

    String getId();
    String getFirstName();
    String getLastName();
    String getUserName();
    String getPassword();
    String getRole();

    void setFirstName(String firstName);
    void setLastName(String lastName);
    void setUserName(String userName);
    void setPassword(String password);
    void setRole(String role);
}
