package com.example.server.model;

import java.util.Set;
import java.util.UUID;

public interface IUser {

    UUID getId();
    String getFirstName();
    String getLastName();
    String getUserName();
    String getPassword();
    Set<Role> getRoles();

    void setFirstName(String firstName);
    void setLastName(String lastName);
    void setUserName(String userName);
    void setPassword(String password);
    void setRoles(Set<Role> roles);
}
