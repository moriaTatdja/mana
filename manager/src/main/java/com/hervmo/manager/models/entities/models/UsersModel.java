package com.hervmo.manager.models.entities.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersModel {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String matchingPassword;
}
