package at.ac.uibk.estore.dto;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String street;
    private String city;
    private String zip;
    private String country;
    private Boolean notification;
}
