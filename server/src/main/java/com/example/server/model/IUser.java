package com.example.server.model;

import java.util.Set;
import java.util.UUID;

public interface IUser {

    String getId();
    String getFirstName();
    String getLastName();
    String getUserName();
    String getPassword();
    String getRoles();

    void setFirstName(String firstName);
    void setLastName(String lastName);
    void setUserName(String userName);
    void setPassword(String password);
    void setRoles(String role);
}
